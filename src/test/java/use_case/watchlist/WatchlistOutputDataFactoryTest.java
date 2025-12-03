package use_case.watchlist;

import entity.WatchlistEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class WatchlistOutputDataFactoryTest {

    private WatchlistEntry e(String s) {
        return new WatchlistEntry(s, new BigDecimal("1.0"), new BigDecimal("1.0"));
    }

    @Test
    void testCreate() {
        WatchlistOutputData d = WatchlistOutputDataFactory.create(
                "Hello123",
                "12345",
                Arrays.asList(e("AAPL"), e("TSLA"))
        );

        assertEquals(Arrays.asList("AAPL", "TSLA"), d.getSymbols());
        assertEquals("200", d.getMessage());
    }

    @Test
    void testCreateNullList() {
        WatchlistOutputData d = WatchlistOutputDataFactory.create(null, null, null);
        assertEquals(0, d.getSymbols().size());
    }
}
