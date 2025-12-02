package use_case.watchlist;

import entity.WatchlistEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the WatchlistInteractor covering success and failure paths.
 */
class WatchlistInteractorTest {

    /**
     * Fake data access object for testing.
     */
    static class FakeAccess implements WatchlistAccessInterface {
        boolean fail;

        @Override
        public List<WatchlistEntry> getWatchlist(String username, String password) {
            if (fail) throw new RuntimeException("get fail");
            return Arrays.asList(
                    new WatchlistEntry("AAPL", BigDecimal.TEN, BigDecimal.ONE),
                    new WatchlistEntry("TSLA", BigDecimal.ONE, BigDecimal.ZERO)
            );
        }

        @Override
        public void addToWatchlist(String username, String password, String symbol) {

        }

        @Override
        public void removeFromWatchlist(String username, String password, String symbol) {
            if (fail) throw new RuntimeException("remove fail");
        }
    }

    /**
     * Fake presenter for recording output boundary calls.
     */
    static class FakePresenter implements WatchlistOutputBoundary {
        WatchlistOutputData success;
        String fail;

        @Override
        public void prepareWatchlistSuccessView(WatchlistOutputData data) {
            this.success = data;
        }

        @Override
        public void prepareWatchlistFailView(String error) {
            this.fail = error;
        }

        @Override
        public void switchToWatchlistView() {}
    }

    /**
     * Tests the success flow of the interactor.
     */
    @Test
    void testSuccess() {
        FakeAccess access = new FakeAccess();
        FakePresenter presenter = new FakePresenter();
        WatchlistInteractor interactor = new WatchlistInteractor(access, presenter);

        WatchlistInputData input = new WatchlistInputData("u", "p");

        interactor.execute(input);

        assertNotNull(presenter.success);
        assertEquals("u", presenter.success.getUsername());
        assertEquals("p", presenter.success.getPassword());
        assertEquals(Arrays.asList("AAPL", "TSLA"), presenter.success.getSymbols());
    }

    /**
     * Tests the failure flow of the interactor.
     */
    @Test
    void testFail() {
        FakeAccess access = new FakeAccess();
        access.fail = true;

        FakePresenter presenter = new FakePresenter();
        WatchlistInteractor interactor = new WatchlistInteractor(access, presenter);

        WatchlistInputData input = new WatchlistInputData("u", "p");

        interactor.execute(input);

        assertTrue(presenter.fail.contains("fail"));
    }
}
