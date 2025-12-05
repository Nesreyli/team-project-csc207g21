package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.News;
import use_case.news.NewsAccessInterface;

/**
 * The DAO for user data.
 */

public class NewsAccessObject implements NewsAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private final String url = "http://localhost:4848/rest";
    private String currentUsername;

    public entity.Response getNews() {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(url + "/news/get/")
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            if (responseBody.getInt(MESSAGE) == SUCCESS_CODE) {
                final JSONArray jsonArray = responseBody.getJSONArray("articles");
                final List<News> newsList = new ArrayList<>();
                for (Object object: jsonArray) {
                    final JSONObject jsonObject = (JSONObject) object;
                    final News news = new News().setAuthor(jsonObject.getString("author"))
                            .setContent(jsonObject.getString("content"))
                            .setImage(jsonObject.getString("image"))
                            .setTitle(jsonObject.getString("title"))
                            .setLink(jsonObject.getString("news_url"))
                            .setDate(jsonObject.getString("date"));

                    newsList.add(news);
                }
                return entity.ResponseFactory.create(SUCCESS_CODE, newsList);
            }
            else {
                return entity.ResponseFactory.create(responseBody.getInt(MESSAGE), null);
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}
