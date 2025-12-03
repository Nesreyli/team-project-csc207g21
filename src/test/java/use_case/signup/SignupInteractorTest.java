package use_case.signup;

import entity.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupInteractorTest {
    private TestSignupDb testSignupDb;
    private SignupInteractor testInteractor;
    private TestSignupOutputBoundary testOutputBoundary;

    @BeforeEach
    void setUp() {
        testSignupDb = new TestSignupDb();
        testOutputBoundary = new TestSignupOutputBoundary();
        testInteractor = new SignupInteractor(testSignupDb, testOutputBoundary);
    }

    private class TestSignupDb implements SignupDataAccessInterface{
        private String username;
        private boolean error;

        @Override
        public Response signUp(String username, String password) {
            if (error) {
                return new Response(500, null);
            }
            if (this.username.equals(username)) {
                return new Response(400, null);
            }
            else{
                return new Response(200, null);
            }
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }
    }
    private class TestSignupOutputBoundary implements SignupOutputBoundary{
        private String exceptionMessage;
        private boolean prepareSuccessViewCalled;
        private boolean prepareFailViewCalled;
        private boolean prepareLoginViewCalled;

        @Override
        public void prepareSuccessView() {
            prepareSuccessViewCalled = true;
            prepareLoginViewCalled = true;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            prepareFailViewCalled = true;
            exceptionMessage = errorMessage;
        }

        @Override
        public void switchToLoginView() {
            prepareLoginViewCalled = true;
        }

        public String getExceptionMessage() {
            return exceptionMessage;
        }

        public void setExceptionMessage(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
        }
    }

    @Test
    void testSignUp() {
        SignupInputData signupInputData = new SignupInputData("Hello",
                "world",
                "world");
        testSignupDb.setUsername("Name");
        testInteractor.execute(signupInputData);
        assertTrue(testOutputBoundary.prepareSuccessViewCalled);
        assertFalse(testOutputBoundary.prepareFailViewCalled);
        assertTrue(testOutputBoundary.prepareLoginViewCalled);
    }

    @Test
    void testRepeat() {
        SignupInputData signupInputData = new SignupInputData("Hello",
                "world",
                "world");
        testSignupDb.setUsername("Hello");
        testInteractor.execute(signupInputData);
        assertFalse(testOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testOutputBoundary.prepareFailViewCalled);
        assertFalse(testOutputBoundary.prepareLoginViewCalled);
    }

    @Test
    void testPasswordRepeat() {
        SignupInputData signupInputData = new SignupInputData("Hello",
                "world",
                "world2");
        testSignupDb.setUsername("Hello");
        testInteractor.execute(signupInputData);
        assertFalse(testOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testOutputBoundary.prepareFailViewCalled);
        assertTrue(testOutputBoundary.getExceptionMessage().equals("Passwords don't match."));
        assertFalse(testOutputBoundary.prepareLoginViewCalled);
    }

    @Test
    void testPasswordWeak() {
        SignupInputData signupInputData = new SignupInputData("Hello",
                "3",
                "3");
        testSignupDb.setUsername("Hello");
        testInteractor.execute(signupInputData);
        assertFalse(testOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testOutputBoundary.prepareFailViewCalled);
        assertTrue(testOutputBoundary.getExceptionMessage().equals("Password too weak"));
        assertFalse(testOutputBoundary.prepareLoginViewCalled);
    }

    @Test
    void testPasswordEmpty() {
        SignupInputData signupInputData = new SignupInputData("Hello",
                "",
                "");
        testSignupDb.setUsername("Hello");
        testInteractor.execute(signupInputData);
        assertFalse(testOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testOutputBoundary.prepareFailViewCalled);
        assertTrue(testOutputBoundary.getExceptionMessage().equals("New password cannot be empty"));
        assertFalse(testOutputBoundary.prepareLoginViewCalled);
    }

    @Test
    void testUsernameEmpty() {
        SignupInputData signupInputData = new SignupInputData("",
                "deeff",
                "deeff");
        testSignupDb.setUsername("Hello");
        testInteractor.execute(signupInputData);
        assertFalse(testOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testOutputBoundary.prepareFailViewCalled);
        assertTrue(testOutputBoundary.getExceptionMessage().equals("Username cannot be empty"));
        assertFalse(testOutputBoundary.prepareLoginViewCalled);
    }

    @Test
    void testServerError() {
        SignupInputData signupInputData = new SignupInputData("WHatup",
                "deeff",
                "deeff");
        testSignupDb.setUsername("Hello");
        testSignupDb.setError(true);
        testInteractor.execute(signupInputData);
        assertFalse(testOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testOutputBoundary.prepareFailViewCalled);
        assertTrue(testOutputBoundary.getExceptionMessage().equals("Server Error"));
        assertFalse(testOutputBoundary.prepareLoginViewCalled);
    }

    @Test
    void testLoginView() {
        SignupInputData signupInputData = new SignupInputData("",
                "deeff",
                "deeff");
        testSignupDb.setUsername("Hello");
        testSignupDb.setError(true);
        testInteractor.switchToLoginView();
        assertTrue(testOutputBoundary.prepareLoginViewCalled);
    }
}
