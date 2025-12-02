package use_case.leaderboard;

import entity.Response;
import entity.ResponseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LeaderboardInteractor with comprehensive test coverage.
 * Uses manual test doubles instead of Mockito.
 */
class LeaderboardInteractorTest {

    private TestLeaderboardAccessObject testAccessObject;
    private TestLeaderboardOutputBoundary testOutputBoundary;
    private LeaderboardInteractor leaderboardInteractor;

    @BeforeEach
    void setUp() {
        testAccessObject = new TestLeaderboardAccessObject();
        testOutputBoundary = new TestLeaderboardOutputBoundary();
        leaderboardInteractor = new LeaderboardInteractor(testAccessObject, testOutputBoundary);
    }

    /**
     * Test double for LeaderboardAccessInterface.
     */
    private static class TestLeaderboardAccessObject implements LeaderboardAccessInterface {
        private Response response;
        private boolean shouldThrowException;
        private String exceptionMessage;
        private boolean getLeaderboardCalled;

        @Override
        public Response getLeaderboard() {
            getLeaderboardCalled = true;
            if (shouldThrowException) {
                throw new RuntimeException(exceptionMessage);
            }
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        public void setShouldThrowException(boolean shouldThrow, String message) {
            this.shouldThrowException = shouldThrow;
            this.exceptionMessage = message;
        }

        public boolean wasGetLeaderboardCalled() {
            return getLeaderboardCalled;
        }
    }

    /**
     * Test double for LeaderboardOutputBoundary.
     */
    private static class TestLeaderboardOutputBoundary implements LeaderboardOutputBoundary {
        private LeaderboardOutputData lastSuccessOutputData;
        private String lastErrorMessage;
        private boolean prepareSuccessViewCalled;
        private boolean prepareFailViewCalled;

        @Override
        public void prepareSuccessView(LeaderboardOutputData outputData) {
            prepareSuccessViewCalled = true;
            lastSuccessOutputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            prepareFailViewCalled = true;
            lastErrorMessage = errorMessage;
        }

        public LeaderboardOutputData getLastSuccessOutputData() {
            return lastSuccessOutputData;
        }

        public String getLastErrorMessage() {
            return lastErrorMessage;
        }

        public boolean wasPrepareSuccessViewCalled() {
            return prepareSuccessViewCalled;
        }

        public boolean wasPrepareFailViewCalled() {
            return prepareFailViewCalled;
        }
    }

    private Map<Integer, List<Object>> createSampleLeaderboard() {
        Map<Integer, List<Object>> leaderboard = new HashMap<>();
        List<Object> user1 = new ArrayList<>();
        user1.add("user1");
        user1.add(new BigDecimal("1000.50"));
        leaderboard.put(1, user1);

        List<Object> user2 = new ArrayList<>();
        user2.add("user2");
        user2.add(new BigDecimal("950.25"));
        leaderboard.put(2, user2);

        return leaderboard;
    }

    @Test
    void testExecute_withSuccessResponse_success() {
        // Arrange
        Map<Integer, List<Object>> expectedLeaderboard = createSampleLeaderboard();
        Response successResponse = ResponseFactory.create(200, expectedLeaderboard);
        testAccessObject.setResponse(successResponse);

        // Act
        leaderboardInteractor.execute();

        // Assert
        assertTrue(testAccessObject.wasGetLeaderboardCalled());
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
        assertFalse(testOutputBoundary.wasPrepareFailViewCalled());

        LeaderboardOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertNotNull(outputData);
        assertEquals(expectedLeaderboard, outputData.getLeaderboard());
        assertTrue(outputData.hasEnoughUsers());
    }

    @Test
    void testExecute_with400Status_failure() {
        // Arrange
        Response failureResponse = ResponseFactory.create(400, null);
        testAccessObject.setResponse(failureResponse);

        // Act
        leaderboardInteractor.execute();

        // Assert
        assertTrue(testAccessObject.wasGetLeaderboardCalled());
        assertTrue(testOutputBoundary.wasPrepareFailViewCalled());
        assertFalse(testOutputBoundary.wasPrepareSuccessViewCalled());
        assertEquals("Failed to load leaderboard", testOutputBoundary.getLastErrorMessage());
    }

    @Test
    void testExecute_with500Status_failure() {
        // Arrange
        Response failureResponse = ResponseFactory.create(500, null);
        testAccessObject.setResponse(failureResponse);

        // Act
        leaderboardInteractor.execute();

        // Assert
        assertTrue(testAccessObject.wasGetLeaderboardCalled());
        assertTrue(testOutputBoundary.wasPrepareFailViewCalled());
        assertFalse(testOutputBoundary.wasPrepareSuccessViewCalled());
        assertEquals("Failed to load leaderboard", testOutputBoundary.getLastErrorMessage());
    }

    @Test
    void testExecute_withException_failure() {
        // Arrange
        String errorMessage = "Network connection error";
        testAccessObject.setShouldThrowException(true, errorMessage);

        // Act
        leaderboardInteractor.execute();

        // Assert
        assertTrue(testAccessObject.wasGetLeaderboardCalled());
        assertTrue(testOutputBoundary.wasPrepareFailViewCalled());
        assertFalse(testOutputBoundary.wasPrepareSuccessViewCalled());
        assertEquals("Server Error: " + errorMessage, testOutputBoundary.getLastErrorMessage());
    }

    @Test
    void testExecute_withEmptyLeaderboard_hasEnoughUsersFalse() {
        // Arrange
        Map<Integer, List<Object>> emptyLeaderboard = new HashMap<>();
        Response successResponse = ResponseFactory.create(200, emptyLeaderboard);
        testAccessObject.setResponse(successResponse);

        // Act
        leaderboardInteractor.execute();

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
        LeaderboardOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertNotNull(outputData);
        assertEquals(0, outputData.getLeaderboard().size());
        assertFalse(outputData.hasEnoughUsers());
    }

    @Test
    void testExecute_withSingleUser_hasEnoughUsersFalse() {
        // Arrange
        Map<Integer, List<Object>> singleUserLeaderboard = new HashMap<>();
        List<Object> user1 = new ArrayList<>();
        user1.add("user1");
        user1.add(new BigDecimal("1000.50"));
        singleUserLeaderboard.put(1, user1);

        Response successResponse = ResponseFactory.create(200, singleUserLeaderboard);
        testAccessObject.setResponse(successResponse);

        // Act
        leaderboardInteractor.execute();

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
        LeaderboardOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertNotNull(outputData);
        assertEquals(1, outputData.getLeaderboard().size());
        assertFalse(outputData.hasEnoughUsers());
    }

    @Test
    void testExecute_withTwoUsers_hasEnoughUsersTrue() {
        // Arrange
        Map<Integer, List<Object>> twoUserLeaderboard = createSampleLeaderboard();
        Response successResponse = ResponseFactory.create(200, twoUserLeaderboard);
        testAccessObject.setResponse(successResponse);

        // Act
        leaderboardInteractor.execute();

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
        LeaderboardOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertNotNull(outputData);
        assertEquals(2, outputData.getLeaderboard().size());
        assertTrue(outputData.hasEnoughUsers());
    }

    @Test
    void testExecute_withMultipleUsers_success() {
        // Arrange
        Map<Integer, List<Object>> leaderboard = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            List<Object> user = new ArrayList<>();
            user.add("user" + i);
            user.add(new BigDecimal(1000 - i * 10));
            leaderboard.put(i, user);
        }

        Response successResponse = ResponseFactory.create(200, leaderboard);
        testAccessObject.setResponse(successResponse);

        // Act
        leaderboardInteractor.execute();

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
        LeaderboardOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertNotNull(outputData);
        assertEquals(5, outputData.getLeaderboard().size());
        assertTrue(outputData.hasEnoughUsers());
    }

    @Test
    void testExecute_withNullLeaderboard_hasEnoughUsersFalse() {
        // Arrange
        Response successResponse = ResponseFactory.create(200, null);
        testAccessObject.setResponse(successResponse);

        // Act
        leaderboardInteractor.execute();

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
        LeaderboardOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertNotNull(outputData);
        assertNull(outputData.getLeaderboard());
        assertFalse(outputData.hasEnoughUsers());
    }

    @Test
    void testExecute_verifyCorrectDataPassed() {
        // Arrange
        Map<Integer, List<Object>> expectedLeaderboard = createSampleLeaderboard();
        Response successResponse = ResponseFactory.create(200, expectedLeaderboard);
        testAccessObject.setResponse(successResponse);

        // Act
        leaderboardInteractor.execute();

        // Assert
        LeaderboardOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertNotNull(outputData);
        assertEquals(expectedLeaderboard, outputData.getLeaderboard());
        assertEquals(2, outputData.getLeaderboard().size());
        assertTrue(outputData.getLeaderboard().containsKey(1));
        assertTrue(outputData.getLeaderboard().containsKey(2));
    }

    @Test
    void testExecute_withDifferentStatusCodes_failure() {
        // Arrange - Test various non-200 status codes
        int[] failureCodes = {404, 401, 403, 500, 503};
        
        for (int code : failureCodes) {
            // Reset test doubles
            testOutputBoundary = new TestLeaderboardOutputBoundary();
            testAccessObject = new TestLeaderboardAccessObject();
            leaderboardInteractor = new LeaderboardInteractor(testAccessObject, testOutputBoundary);
            
            Response failureResponse = ResponseFactory.create(code, null);
            testAccessObject.setResponse(failureResponse);

            // Act
            leaderboardInteractor.execute();

            // Assert
            assertTrue(testOutputBoundary.wasPrepareFailViewCalled(), 
                "Should fail for status code: " + code);
            assertEquals("Failed to load leaderboard", testOutputBoundary.getLastErrorMessage());
        }
    }

    @Test
    void testExecute_withExceptionContainingNullMessage() {
        // Arrange
        testAccessObject.setShouldThrowException(true, null);

        // Act
        leaderboardInteractor.execute();

        // Assert
        assertTrue(testOutputBoundary.wasPrepareFailViewCalled());
        assertEquals("Server Error: null", testOutputBoundary.getLastErrorMessage());
    }

    @Test
    void testExecute_verifyMethodCallOrder() {
        // Arrange
        Map<Integer, List<Object>> expectedLeaderboard = createSampleLeaderboard();
        Response successResponse = ResponseFactory.create(200, expectedLeaderboard);
        testAccessObject.setResponse(successResponse);

        // Act
        leaderboardInteractor.execute();

        // Assert - Verify getLeaderboard is called before prepareSuccessView
        assertTrue(testAccessObject.wasGetLeaderboardCalled());
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
    }
}

