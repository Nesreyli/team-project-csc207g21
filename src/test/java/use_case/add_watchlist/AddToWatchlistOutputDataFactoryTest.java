package use_case.add_watchlist;

import entity.WatchlistEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AddToWatchlistOutputDataFactoryTest {

    private WatchlistEntry e(String s) {
        return new WatchlistEntry(s, new BigDecimal("2.0"), new BigDecimal("3.0"));
    }

    @Test
    void testCreate() {
        AddToWatchlistOutputData d =
                AddToWatchlistOutputDataFactory.create("AAPL",
                        Arrays.asList(e("AAPL"), e("GOOGL")));

        assertEquals("AAPL", d.getSymbol());
        assertEquals(Arrays.asList("AAPL", "GOOGL"), d.getUpdatedSymbols());
        assertEquals("200", d.getMessage());
    }

    @Test
    void testCreateNull() {
        AddToWatchlistOutputData d =
                AddToWatchlistOutputDataFactory.create("TSLA", null);

        assertEquals(0, d.getUpdatedSymbols().size());
    }
}
