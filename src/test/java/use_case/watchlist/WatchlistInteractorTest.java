package use_case.watchlist;

import entity.WatchlistEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for WatchlistInteractor achieving 100% code coverage.
 */
class WatchlistInteractorTest {

    /**
     * Fake access interface for simulating success, failure, and null responses.
     */
    static class FakeAccess implements WatchlistAccessInterface {
        boolean fail;
        boolean returnNull;

        @Override
        public List<WatchlistEntry> getWatchlist(String u, String p) {
            if (fail) throw new RuntimeException("fail");
            if (returnNull) return null;
            return Arrays.asList(
                    new WatchlistEntry("AAPL", BigDecimal.TEN, BigDecimal.ONE),
                    new WatchlistEntry("TSLA", BigDecimal.ONE, BigDecimal.ZERO)
            );
        }

        @Override public void addToWatchlist(String u, String p, String s) {}
        @Override public void removeFromWatchlist(String u, String p, String s) {}
    }

    /**
     * Fake presenter used to capture output boundary interactions.
     */
    static class FakePresenter implements WatchlistOutputBoundary {
        WatchlistOutputData success;
        String fail;
        boolean navigated;

        @Override public void prepareWatchlistSuccessView(WatchlistOutputData data) { success = data; }
        @Override public void prepareWatchlistFailView(String errorMessage) { fail = errorMessage; }
        @Override public void switchToWatchlistView() { navigated = true; }
    }

    /**
     * Tests successful execution of the interactor and correct population
     * of WatchlistOutputData.
     */
    @Test
    void success() {
        FakeAccess access = new FakeAccess();
        FakePresenter presenter = new FakePresenter();
        WatchlistInteractor interactor = new WatchlistInteractor(access, presenter);

        interactor.execute(new WatchlistInputData("u", "p"));

        assertNotNull(presenter.success);
        assertEquals("u", presenter.success.getUsername());
        assertEquals("p", presenter.success.getPassword());
        assertEquals(Arrays.asList("AAPL", "TSLA"), presenter.success.getSymbols());
    }

    /**
     * Tests error-handling logic when the data access layer throws an exception.
     */
    @Test
    void failure() {
        FakeAccess access = new FakeAccess();
        access.fail = true;

        FakePresenter presenter = new FakePresenter();
        WatchlistInteractor interactor = new WatchlistInteractor(access, presenter);

        interactor.execute(new WatchlistInputData("u", "p"));

        assertTrue(presenter.fail.contains("fail"));
    }

    /**
     * Tests handling of a null watchlist returned by the data access layer.
     */
    @Test
    void nullList() {
        FakeAccess access = new FakeAccess();
        access.returnNull = true;

        FakePresenter presenter = new FakePresenter();
        WatchlistInteractor interactor = new WatchlistInteractor(access, presenter);

        interactor.execute(new WatchlistInputData("u", "p"));

        assertNotNull(presenter.success);
        assertTrue(presenter.success.getSymbols().isEmpty());
    }

    /**
     * Tests that the interactor both executes the watchlist retrieval and
     * triggers navigation to the watchlist view.
     */
    @Test
    void executeAndNavigate() {
        FakeAccess access = new FakeAccess();
        FakePresenter presenter = new FakePresenter();
        WatchlistInteractor interactor = new WatchlistInteractor(access, presenter);

        interactor.executeAndNavigate(new WatchlistInputData("u", "p"));

        assertNotNull(presenter.success);
        assertTrue(presenter.navigated);
    }

    /**
     * Tests the WatchlistOutputDataFactory to ensure correct mapping of
     * symbols, prices, and performance values.
     */
    @Test
    void factoryCoverage() {
        List<WatchlistEntry> entries = Arrays.asList(
                new WatchlistEntry("MSFT", BigDecimal.TEN, BigDecimal.ONE),
                new WatchlistEntry("GOOG", null, null)
        );

        WatchlistOutputData data = WatchlistOutputDataFactory.create("u", "p", entries);

        assertEquals(Arrays.asList("MSFT", "GOOG"), data.getSymbols());
        assertEquals("u", data.getUsername());
        assertEquals("p", data.getPassword());
        assertEquals("200", data.getMessage());
    }

    /**
     * Tests simple getter coverage for WatchlistInputData.
     */
    @Test
    void inputDataCoverage() {
        WatchlistInputData d = new WatchlistInputData("u", "p");
        assertEquals("u", d.getUsername());
        assertEquals("p", d.getPassword());
    }

    /**
     * Covers all getters in WatchlistOutputData, including prices and performance.
     */
    @Test
    void outputDataGettersCoverage() {
        Map<String, BigDecimal> prices = Map.of("AAPL", BigDecimal.TEN);
        Map<String, BigDecimal> perf = Map.of("AAPL", BigDecimal.ONE);

        WatchlistOutputData data = new WatchlistOutputData(
                "u", "p",
                List.of("AAPL"),
                prices,
                perf,
                "200"
        );

        assertEquals("u", data.getUsername());
        assertEquals("p", data.getPassword());
        assertEquals(List.of("AAPL"), data.getSymbols());
        assertEquals(prices, data.getPrices());
        assertEquals(perf, data.getPerformance());
        assertEquals("200", data.getMessage());
    }

    /**
     * Covers all branches in WatchlistOutputDataFactory including null entries list,
     * null price, and null performance branches.
     */
    @Test
    void factoryBranchCoverage() {
        WatchlistOutputData data = WatchlistOutputDataFactory.create("u", "p", null);

        assertEquals("u", data.getUsername());
        assertEquals("p", data.getPassword());
        assertTrue(data.getSymbols().isEmpty());

        List<WatchlistEntry> entries = Arrays.asList(
                new WatchlistEntry("X", null, BigDecimal.ONE),
                new WatchlistEntry("Y", BigDecimal.TEN, null)
        );

        WatchlistOutputData data2 = WatchlistOutputDataFactory.create("u2", "p2", entries);

        assertEquals(Arrays.asList("X", "Y"), data2.getSymbols());
        assertEquals(BigDecimal.ONE, data2.getPerformance().get("X"));
        assertEquals(BigDecimal.TEN, data2.getPrices().get("Y"));
    }
}
