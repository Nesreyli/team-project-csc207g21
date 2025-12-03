package use_case.add_watchlist;

import data_access.WatchlistAccessObject;
import entity.WatchlistEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AddToWatchlistInteractor with full line coverage.
 * Uses manual test doubles (no Mockito).
 */
class AddToWatchlistInteractorTest {

    private TestWatchlistAccess testAccess;
    private TestAddOutput testOutput;
    private AddToWatchlistInteractor interactor;

    @BeforeEach
    void setUp() {
        testAccess = new TestWatchlistAccess();
        testOutput = new TestAddOutput();
        interactor = new AddToWatchlistInteractor(testAccess, testOutput);
    }

    private static class TestWatchlistAccess extends WatchlistAccessObject {

        boolean shouldThrow = false;
        String errorMsg;

        List<WatchlistEntry> entriesToReturn;

        String lastAddUser;
        String lastAddPass;
        String lastAddSymbol;
        boolean addCalled = false;
        boolean getCalled = false;

        @Override
        public void addToWatchlist(String username, String password, String symbol) {
            addCalled = true;
            lastAddUser = username;
            lastAddPass = password;
            lastAddSymbol = symbol;

            if (shouldThrow) throw new RuntimeException(errorMsg);
        }

        @Override
        public List<WatchlistEntry> getWatchlist(String username, String password) {
            getCalled = true;
            if (shouldThrow) throw new RuntimeException(errorMsg);
            return entriesToReturn;
        }

        public void setEntries(List<WatchlistEntry> list) {
            this.entriesToReturn = list;
        }

        public void throwError(String msg) {
            this.shouldThrow = true;
            this.errorMsg = msg;
        }
    }

    private static class TestAddOutput implements AddToWatchlistOutputBoundary {

        AddToWatchlistOutputData successData;
        String failMessage;

        boolean successCalled = false;
        boolean failCalled = false;

        @Override
        public void prepareAddToWatchlistSuccessView(AddToWatchlistOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }

        @Override
        public void prepareAddToWatchlistFailView(String errorMessage) {
            failCalled = true;
            failMessage = errorMessage;
        }
    }

    private WatchlistEntry entry(String symbol) {
        return new WatchlistEntry(symbol, new BigDecimal("10.00"), new BigDecimal("2.00"));
    }

    @Test
    void testExecute_success() {
        testAccess.setEntries(Arrays.asList(entry("AAPL"), entry("GOOGL")));

        AddToWatchlistInputData input = new AddToWatchlistInputData("john", "pw", "AAPL");

        interactor.execute(input);

        assertTrue(testAccess.addCalled);
        assertTrue(testAccess.getCalled);
        assertTrue(testOutput.successCalled);
        assertFalse(testOutput.failCalled);

        AddToWatchlistOutputData out = testOutput.successData;
        assertEquals("AAPL", out.getSymbol());
        assertEquals(Arrays.asList("AAPL", "GOOGL"), out.getUpdatedSymbols());
        assertEquals("200", out.getMessage());
    }

    @Test
    void testExecute_successWithNullEntries() {
        testAccess.setEntries(null);

        AddToWatchlistInputData input = new AddToWatchlistInputData("u", "p", "TSLA");

        interactor.execute(input);

        assertTrue(testOutput.successCalled);
        assertNotNull(testOutput.successData.getUpdatedSymbols());
        assertEquals(0, testOutput.successData.getUpdatedSymbols().size());
    }

    @Test
    void testExecute_failure_addThrows() {
        testAccess.throwError("DB error");

        AddToWatchlistInputData input = new AddToWatchlistInputData("u", "p", "FAIL");

        interactor.execute(input);

        assertTrue(testOutput.failCalled);
        assertTrue(testOutput.failMessage.contains("Failed to add symbol: DB error"));
        assertFalse(testOutput.successCalled);
    }

    @Test
    void testExecute_failure_getWatchlistThrows() {
        testAccess.setEntries(Arrays.asList(entry("NFLX")));
        testAccess.throwError("Cannot fetch");

        AddToWatchlistInputData input = new AddToWatchlistInputData("u", "p", "NFLX");

        interactor.execute(input);

        assertTrue(testOutput.failCalled);
        assertTrue(testOutput.failMessage.contains("Cannot fetch"));
    }

    @Test
    void testExecute_recordsCorrectArguments() {
        testAccess.setEntries(Collections.singletonList(entry("IBM")));

        AddToWatchlistInputData input = new AddToWatchlistInputData("alice", "123", "IBM");

        interactor.execute(input);

        assertEquals("alice", testAccess.lastAddUser);
        assertEquals("123", testAccess.lastAddPass);
        assertEquals("IBM", testAccess.lastAddSymbol);
    }
}
