package use_case.remove_watchlist;

import entity.WatchlistEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.watchlist.WatchlistAccessInterface;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for RemoveFromWatchlistInteractor and related output data classes.
 * Uses manual test doubles and achieves full line and method coverage.
 */
class RemoveFromWatchlistInteractorTest {

    private TestAccess testAccess;
    private TestRemoveOutput testOutput;
    private RemoveFromWatchlistInteractor interactor;

    /**
     * Initializes fresh test doubles and interactor for each test.
     */
    @BeforeEach
    void setUp() {
        testAccess = new TestAccess();
        testOutput = new TestRemoveOutput();
        interactor = new RemoveFromWatchlistInteractor(testAccess, testOutput);
    }

    /**
     * Test double implementing WatchlistAccessInterface to simulate
     * database behavior, failures, and record interaction parameters.
     */
    private static class TestAccess implements WatchlistAccessInterface {

        boolean shouldThrow = false;
        String errorMsg;

        boolean removeCalled = false;
        boolean getCalled = false;

        String lastUser;
        String lastPass;
        String lastSymbol;

        List<WatchlistEntry> returnEntries;

        @Override
        public List<WatchlistEntry> getWatchlist(String username, String password) {
            getCalled = true;
            if (shouldThrow) throw new RuntimeException(errorMsg);
            return returnEntries;
        }

        @Override
        public void addToWatchlist(String username, String password, String symbol) {}

        @Override
        public void removeFromWatchlist(String username, String password, String symbol) {
            removeCalled = true;
            lastUser = username;
            lastPass = password;
            lastSymbol = symbol;
            if (shouldThrow) throw new RuntimeException(errorMsg);
        }

        public void setEntries(List<WatchlistEntry> entries) {
            returnEntries = entries;
        }

        public void throwError(String msg) {
            shouldThrow = true;
            errorMsg = msg;
        }
    }

    /**
     * Test double for RemoveFromWatchlistOutputBoundary to capture
     * success and error output for verification.
     */
    private static class TestRemoveOutput implements RemoveFromWatchlistOutputBoundary {

        RemoveFromWatchlistOutputData successData;
        String failMessage;

        boolean successCalled = false;
        boolean failCalled = false;

        @Override
        public void prepareRemoveFromWatchlistSuccessView(RemoveFromWatchlistOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }

        @Override
        public void prepareRemoveFromWatchlistFailView(String errorMessage) {
            failCalled = true;
            failMessage = errorMessage;
        }
    }

    /**
     * Utility constructor for creating sample WatchlistEntry objects.
     */
    private WatchlistEntry entry(String symbol) {
        return new WatchlistEntry(symbol, new BigDecimal("12.00"), new BigDecimal("3.00"));
    }

    /**
     * Verifies successful symbol removal, correct access calls,
     * and correct success output structure.
     */
    @Test
    void testExecute_success() {
        testAccess.setEntries(Arrays.asList(entry("AAPL"), entry("GOOGL")));
        RemoveFromWatchlistInputData input =
                new RemoveFromWatchlistInputData("john", "pw", "AAPL");

        interactor.execute(input);

        assertTrue(testAccess.removeCalled);
        assertTrue(testAccess.getCalled);
        assertTrue(testOutput.successCalled);

        RemoveFromWatchlistOutputData out = testOutput.successData;
        assertEquals("AAPL", out.getSymbol());
        assertEquals(Arrays.asList("AAPL", "GOOGL"), out.getUpdatedSymbols());
    }

    /**
     * Ensures null watchlist entries are treated as an empty list.
     */
    @Test
    void testExecute_nullEntries() {
        testAccess.setEntries(null);

        RemoveFromWatchlistInputData input =
                new RemoveFromWatchlistInputData("u", "p", "TSLA");

        interactor.execute(input);

        assertTrue(testOutput.successCalled);
        assertNotNull(testOutput.successData.getUpdatedSymbols());
        assertEquals(0, testOutput.successData.getUpdatedSymbols().size());
    }

    /**
     * Verifies failure output when removeFromWatchlist throws an exception.
     */
    @Test
    void testExecute_failure_removeThrows() {
        testAccess.throwError("DB failure");

        RemoveFromWatchlistInputData input =
                new RemoveFromWatchlistInputData("u", "p", "FAIL");

        interactor.execute(input);

        assertTrue(testOutput.failCalled);
        assertTrue(testOutput.failMessage.contains("Failed to remove symbol: DB failure"));
    }

    /**
     * Verifies failure output when getWatchlist throws after removal.
     */
    @Test
    void testExecute_failure_getThrows() {
        testAccess.setEntries(Arrays.asList(entry("IBM")));
        testAccess.throwError("Cannot fetch");

        RemoveFromWatchlistInputData input =
                new RemoveFromWatchlistInputData("u", "p", "IBM");

        interactor.execute(input);

        assertTrue(testOutput.failCalled);
        assertTrue(testOutput.failMessage.contains("Cannot fetch"));
    }

    /**
     * Confirms the interactor passes correct arguments to the data access layer.
     */
    @Test
    void testExecute_argumentRecording() {
        testAccess.setEntries(Collections.emptyList());

        RemoveFromWatchlistInputData input =
                new RemoveFromWatchlistInputData("alice", "123", "MSFT");

        interactor.execute(input);

        assertEquals("alice", testAccess.lastUser);
        assertEquals("123", testAccess.lastPass);
        assertEquals("MSFT", testAccess.lastSymbol);
    }

    /**
     * Covers constructor and all getters in RemoveFromWatchlistOutputData.
     * Ensures correct field assignment and retrieval.
     */
    @Test
    void outputDataCoverage() {
        RemoveFromWatchlistOutputData data =
                new RemoveFromWatchlistOutputData("ok", "AAPL", List.of("TSLA", "GOOG"));

        assertEquals("AAPL", data.getSymbol());
        assertEquals("ok", data.getMessage());
        assertEquals(List.of("TSLA", "GOOG"), data.getUpdatedSymbols());
    }
}
