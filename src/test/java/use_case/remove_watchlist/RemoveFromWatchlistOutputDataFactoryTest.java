package use_case.remove_watchlist;

import entity.WatchlistEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RemoveFromWatchlistOutputDataFactoryTest {

    private WatchlistEntry e(String s) {
        return new WatchlistEntry(s, new BigDecimal("2.0"), new BigDecimal("3.0"));
    }

    @Test
    void testCreate() {
        RemoveFromWatchlistOutputData d =
                RemoveFromWatchlistOutputDataFactory.create("AAPL",
                        Arrays.asList(e("AAPL"), e("MSFT")));

        assertEquals("AAPL", d.getSymbol());
        assertEquals(Arrays.asList("AAPL", "MSFT"), d.getUpdatedSymbols());
        assertEquals("200", d.getMessage());
    }

    @Test
    void testCreateNull() {
        RemoveFromWatchlistOutputData d =
                RemoveFromWatchlistOutputDataFactory.create("TSLA", null);

        assertEquals(0, d.getUpdatedSymbols().size());
    }
}
