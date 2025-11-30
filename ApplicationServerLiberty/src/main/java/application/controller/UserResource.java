package application.controller;

import application.use_case.User.OutputDataSignup;
import application.use_case.User.SignUpInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/user")
public class UserResource {

    @Inject
    private SignUpInteractor signUp;

    /**
     * Endpoint for user signup.
     * @param username username
     * @param password password
     * @param password2 password repeat
     * @return JSON
     */
    @Path("/signup")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OutputDataSignup userSignUp(@DefaultValue("") @QueryParam("username") String username,
                                       @DefaultValue("") @QueryParam("password") String password,
                                       @DefaultValue("") @QueryParam("password2") String password2) {
        return signUp.executeSignup(username, password, password2);
    }

    /**
     * Endpoint for login.
     * @param username username
     * @param password password
     * @return JSON
     */
    @Path("/login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OutputDataSignup userLogin(@DefaultValue("") @QueryParam("username") String username,
                                       @DefaultValue("") @QueryParam("password") String password) {
        return signUp.executeLogin(username, password);
    }
}
