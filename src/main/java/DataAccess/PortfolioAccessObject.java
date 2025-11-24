package DataAccess;

import Entity.Portfolio;
import Entity.UserFactory;
import UseCase.portfolio.PortfolioAccessInterface;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
//import use_case.change_password.ChangePasswordUserDataAccessInterface;
//import use_case.login.LoginUserDataAccessInterface;
//import use_case.logout.LogoutUserDataAccessInterface;
//import use_case.signup.SignupUserDataAccessInterface;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * The DAO for user data.
 */
/*implements SignupUserDataAccessInterface,
LoginUserDataAccessInterface,
ChangePasswordUserDataAccessInterface,
LogoutUserDataAccessInterface*/

public class PortfolioAccessObject implements PortfolioAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private final String url = "http://alex-mh-mbp:4848/rest";
    private String currentUsername;


    public Entity.Response getPort(String username, String password){
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(url + "/portfolio/get/?username=%s&password=%s", username, password))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());
            if (responseBody.getInt(MESSAGE) == SUCCESS_CODE) {
                final String name = responseBody.getString("username");
                final String pass = password;
                BigDecimal cash = responseBody.getBigDecimal("cash");
                BigDecimal value = responseBody.getBigDecimal("value");
                JSONObject holdings = responseBody.getJSONObject("holdings");
                BigDecimal performance = responseBody.getBigDecimal("performance");

                Portfolio port = new Portfolio.Builder().user(UserFactory.create(name, pass)).
                        cash(cash).holdings(holdings.toMap()).value(value).performance(performance).
                        build();

                return Entity.ResponseFactory.create(SUCCESS_CODE, port);
            }
            else {
                return Entity.ResponseFactory.create(responseBody.getInt(MESSAGE), null);
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}