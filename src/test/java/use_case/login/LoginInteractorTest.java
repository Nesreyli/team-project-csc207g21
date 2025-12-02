package use_case.login;

import entity.Response;
import entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginInteractorTest {

    private TestLoginDb testLoginDb;
    private LogInInteractor testInteractor;
    private TestLoginBoundary testOutputBoundary;

    @BeforeEach
    void setUp() {
        testLoginDb = new TestLoginDb();
        testOutputBoundary = new TestLoginBoundary();
        testInteractor = new LogInInteractor(testLoginDb, testOutputBoundary);
    }

    private class TestLoginDb implements LogInAccessInterface {
        private String username;
        private String password;
        private boolean error;

        @Override
        public Response logIn(String username, String password) {
            if(error){
                return new Response(500, null);
            }
            else if (this.username.equals(username) && this.password.equals(password)) {
                return new Response(200, UserFactory.create(username,password));
            }
            else {
                return new Response(400, null);
            }
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }
    }
    private class TestLoginBoundary implements LoginOutputBoundary {
        private String exceptionMessage;
        private boolean prepareSuccessViewCalled;
        private boolean prepareFailViewCalled;
        private boolean prepareSignupViewCalled;
        private LoginOutputData loginOutputData;


        public String getExceptionMessage() {
            return exceptionMessage;
        }

        public void setExceptionMessage(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
        }

        @Override
        public void prepareSuccessView(LoginOutputData outputData) {
            prepareSuccessViewCalled = true;
            this.loginOutputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            prepareFailViewCalled =  true;
            this.exceptionMessage = errorMessage;
        }

        @Override
        public void prepareSignupView() {
            prepareSignupViewCalled = true;
        }
    }

    @Test
    void testLogin() {
        LoginInputData loginInputData = new LoginInputData("Hello",
                "world");
        testLoginDb.setUsername("Hello");
        testLoginDb.setPassword("world");
        testInteractor.execute(loginInputData);
        assertTrue(testOutputBoundary.prepareSuccessViewCalled);
        assertFalse(testOutputBoundary.prepareFailViewCalled);
        assertFalse(testOutputBoundary.prepareSignupViewCalled);

        assertTrue(testOutputBoundary.loginOutputData.getUsername().equals("Hello"));
        assertTrue(testOutputBoundary.loginOutputData.getPassword().equals("world"));

    }

    @Test
    void testIncorrectUsername() {
        LoginInputData loginInputData = new LoginInputData("Hello",
                "world");

        testLoginDb.setUsername("Hello");
        testLoginDb.setPassword("world2");
        testInteractor.execute(loginInputData);
        assertFalse(testOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testOutputBoundary.prepareFailViewCalled);
        assertTrue(testOutputBoundary.getExceptionMessage().equals("Incorrect username or password."));
        assertFalse(testOutputBoundary.prepareSignupViewCalled);
    }

    @Test
    void testIncorrectPassword() {
        LoginInputData loginInputData = new LoginInputData("Hello",
                "world");

        testLoginDb.setUsername("Hello2");
        testLoginDb.setPassword("world");
        testInteractor.execute(loginInputData);
        assertFalse(testOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testOutputBoundary.prepareFailViewCalled);
        assertTrue(testOutputBoundary.getExceptionMessage().equals("Incorrect username or password."));
        assertFalse(testOutputBoundary.prepareSignupViewCalled);
    }

    @Test
    void testServerError() {
        LoginInputData loginInputData = new LoginInputData("WHatup",
                "deeff");
        testLoginDb.setUsername("Hello");
        testLoginDb.setError(true);
        testInteractor.execute(loginInputData);
        assertFalse(testOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testOutputBoundary.prepareFailViewCalled);
        assertTrue(testOutputBoundary.getExceptionMessage().equals("Error"));
        assertFalse(testOutputBoundary.prepareSignupViewCalled);
    }

    @Test
    void testLoginView() {
        LoginInputData loginInputData = new LoginInputData("",
                "deeff");
        testLoginDb.setUsername("Hello");
        testInteractor.toSignup();
        assertTrue(testOutputBoundary.prepareSignupViewCalled);
    }
}

