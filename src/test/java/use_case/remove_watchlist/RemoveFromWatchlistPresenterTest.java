package use_case.remove_watchlist;

import interface_adapter.remove_watchlist.RemoveFromWatchlistPresenter;
import interface_adapter.remove_watchlist.RemoveFromWatchlistViewModel;
import interface_adapter.watchlist.WatchlistViewModel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RemoveFromWatchlistPresenterTest {

    @Test
    void testPrepareSuccess() {
        RemoveFromWatchlistViewModel removeVM = new RemoveFromWatchlistViewModel();
        WatchlistViewModel watchVM = new WatchlistViewModel();
        RemoveFromWatchlistPresenter presenter =
                new RemoveFromWatchlistPresenter(removeVM, watchVM);

        RemoveFromWatchlistOutputData out =
                new RemoveFromWatchlistOutputData("200", "TSLA", Arrays.asList("AAPL"));

        presenter.prepareRemoveFromWatchlistSuccessView(out);

        assertEquals("TSLA", removeVM.getState().getLastRemovedSymbol());
        assertEquals("200", removeVM.getState().getLastMessage());
        assertEquals(Arrays.asList("AAPL"), watchVM.getState().getSymbols());
    }

    @Test
    void testPrepareFail() {
        RemoveFromWatchlistViewModel removeVM = new RemoveFromWatchlistViewModel();
        WatchlistViewModel watchVM = new WatchlistViewModel();
        RemoveFromWatchlistPresenter presenter =
                new RemoveFromWatchlistPresenter(removeVM, watchVM);

        presenter.prepareRemoveFromWatchlistFailView("fail");

        assertNull(removeVM.getState().getLastRemovedSymbol());
        assertEquals("fail", removeVM.getState().getLastMessage());
    }
}
