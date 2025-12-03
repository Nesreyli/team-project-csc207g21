package data_access;

import entity.Price;
import use_case.stock_price.PriceAccessInterface;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
//import use_case.change_password.ChangePasswordUserDataAccessInterface;
//import use_case.login.LoginUserDataAccessInterface;
//import use_case.logout.LogoutUserDataAccessInterface;
//import use_case.signup.SignupUserDataAccessInterface;

import java.io.IOException;

/**
 * The DAO for user data.
 */
/*implements SignupUserDataAccessInterface,
LoginUserDataAccessInterface,
ChangePasswordUserDataAccessInterface,
LogoutUserDataAccessInterface*/

public class PriceAccessObject implements PriceAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
<<<<<<< HEAD
    private static final String URL = "http://localhost:8080/rest";
=======
    private static final String URL = "http://100.67.4.80:4848/rest";
>>>>>>> origin/main

    public PriceAccessObject(){
    }

    public entity.Response getPrice(String symbol) {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(URL + "/stocks/price/?symbols=%s", symbol))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());
            if (responseBody.getInt(MESSAGE) == SUCCESS_CODE) {
                JSONObject price = responseBody.getJSONObject("prices");
                price = price.getJSONObject(symbol);

                Price priceEntity = new Price.Builder()
                        .addPrice(price.getBigDecimal("p"))
                        .addSymbol(symbol)
                        .addYtdPrice(price.getBigDecimal("ytd"))
                        .addYtdPerformance(price.getBigDecimal("perf"))
                        .build();

                return entity.ResponseFactory.create(SUCCESS_CODE, priceEntity);
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