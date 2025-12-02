package use_case.add_watchlist;

import interface_adapter.add_watchlist.AddToWatchlistPresenter;
import interface_adapter.add_watchlist.AddToWatchlistViewModel;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AddToWatchlistPresenterTest {

    @Test
    void testPrepareSuccess() {
        AddToWatchlistViewModel addVM = new AddToWatchlistViewModel();
        WatchlistViewModel watchVM = new WatchlistViewModel();
        AddToWatchlistPresenter presenter = new AddToWatchlistPresenter(addVM, watchVM);

        AddToWatchlistOutputData out =
                new AddToWatchlistOutputData("200", "AAPL", Arrays.asList("AAPL", "GOOGL"));

        presenter.prepareAddToWatchlistSuccessView(out);

        assertEquals("AAPL", addVM.getState().getLastAddedSymbol());
        assertEquals("200", addVM.getState().getLastMessage());

        WatchlistState s = watchVM.getState();
        assertEquals(Arrays.asList("AAPL", "GOOGL"), s.getSymbols());
    }

    @Test
    void testPrepareFail() {
        AddToWatchlistViewModel addVM = new AddToWatchlistViewModel();
        WatchlistViewModel watchVM = new WatchlistViewModel();
        AddToWatchlistPresenter presenter = new AddToWatchlistPresenter(addVM, watchVM);

        presenter.prepareAddToWatchlistFailView("err");

        assertNull(addVM.getState().getLastAddedSymbol());
        assertEquals("err", addVM.getState().getLastMessage());
    }
}
