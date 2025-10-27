package jakarta.Controller;

import jakarta.UseCases.OutputDataSignup;
import jakarta.UseCases.SignUpInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/user")
public class SignUpResource {

    @Inject
    SignUpInteractor signUp;

    //So i have to user GET why not POST
    @Path("/signup")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public OutputDataSignup userSignUp(@DefaultValue("") @QueryParam("username") String username,
                                       @DefaultValue("") @QueryParam("password") String password,
                                       @DefaultValue("") @QueryParam("password2") String password2){
        if(username == null){
            username = "";
        }
        if(password == null){
            password = "";
        }
        if(password2 == null){
            password2 = "";
        }
        System.out.println(username + password + password2);
        return signUp.executeSignup(username, password, password2);

    }
}
