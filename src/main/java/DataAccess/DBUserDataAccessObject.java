package DataAccess;

import Entity.User;
import Entity.UserFactory;
import UseCase.UserAccessInterface;
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

public class DBUserDataAccessObject implements UserAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private final UserFactory userFactory;
    private final String url = "http://100.71.12.182:4848/rest";

    private String currentUsername;

    public DBUserDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    public Entity.Response logIn(String username, String password) {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(url + "/user/login/?username=%s&password=%s", username, password))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());
            if (responseBody.getInt(MESSAGE) == SUCCESS_CODE) {
                final String name = responseBody.getString("username");
                final String pass = password;
                return Entity.ResponseFactory.create(SUCCESS_CODE, userFactory.create(name, pass));
            }
            else {
                return Entity.ResponseFactory.create(responseBody.getInt(MESSAGE), null);
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Entity.Response signUp(String username, String password) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // POST METHOD
        final Request request = new Request.Builder()
                .url(String.format(url + "/user/signup/?username=%s&password=%s&password2=%s",
                        username, password, password))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());
            if (responseBody.getInt(MESSAGE) == SUCCESS_CODE) {
                final JSONObject userJSONObject = responseBody.getJSONObject("user");
                final String name = userJSONObject.getString(USERNAME);
                final String pass = password;
                return Entity.ResponseFactory.create(SUCCESS_CODE, userFactory.create(name, pass));
            } else {
                return Entity.ResponseFactory.create(responseBody.getInt(MESSAGE), null);
            }
        } catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}