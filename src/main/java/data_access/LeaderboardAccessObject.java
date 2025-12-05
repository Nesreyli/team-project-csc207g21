package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Response;
import entity.ResponseFactory;
import use_case.leaderboard.LeaderboardAccessInterface;

public class LeaderboardAccessObject implements LeaderboardAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String MESSAGE = "message";
    private final String url = "http://localhost:4848/rest";


    public Response getLeaderboard() {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(url + "/leaderboard/get")
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final okhttp3.Response httpResponse = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(httpResponse.body().string());
            if (responseBody.getInt(MESSAGE) == SUCCESS_CODE) {
                final JSONObject leaderboardJson = responseBody.getJSONObject("leaderboard");
                final Map<Integer, List<Object>> leaderboard = new HashMap<>();
                
                // Parse the leaderboard JSON object
                for (String key : leaderboardJson.keySet()) {
                    try {
                        final Integer rank = Integer.parseInt(key);
                        final JSONArray userData = leaderboardJson.getJSONArray(key);
                        if (userData != null && userData.length() >= 2) {
                            final List<Object> userInfo = new ArrayList<>();
                            for (int i = 0; i < userData.length(); i++) {
                                final Object value = userData.get(i);
                                // Handle null values
                                if (value == null || value instanceof String && ((String) value).isEmpty()) {
                                    userInfo.add("");
                                }
                                else {
                                    userInfo.add(value);
                                }
                            }
                            // Only add if we have valid data
                            if (!userInfo.isEmpty() && userInfo.get(0) != null && !userInfo.get(0)
                                    .toString().isEmpty()) {
                                leaderboard.put(rank, userInfo);
                            }
                        }
                    }
                    catch (NumberFormatException | JSONException error) {
                        // Skip invalid entries
                        System.err.println("Error parsing leaderboard entry for key " + key + ": "
                                + error.getMessage());
                    }
                }
                
                return ResponseFactory.create(SUCCESS_CODE, leaderboard);
            }
            else {
                return ResponseFactory.create(responseBody.getInt(MESSAGE), null);
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}

