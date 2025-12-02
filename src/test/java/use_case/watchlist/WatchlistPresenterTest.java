package use_case.watchlist;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.watchlist.WatchlistPresenter;
import interface_adapter.watchlist.WatchlistViewModel;
import org.junit.jupiter.api.Test;
import use_case.watchlist.WatchlistOutputData;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class WatchlistPresenterTest {

    @Test
    void testPrepareSuccess() {
        WatchlistViewModel watchlistViewModel = new WatchlistViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        WatchlistPresenter presenter = new WatchlistPresenter(viewManagerModel, watchlistViewModel, loggedInViewModel);

        WatchlistOutputData out =
                new WatchlistOutputData(
                        "Hello123",
                        "12345",
                        Arrays.asList("AAPL", "TSLA"),
                        Collections.emptyMap(),
                        Collections.emptyMap(),
                        "200"
                );

        presenter.prepareWatchlistSuccessView(out);

        assertEquals(Arrays.asList("AAPL", "TSLA"), watchlistViewModel.getState().getSymbols());
        assertEquals("200", watchlistViewModel.getState().getMessage());
    }

    @Test
    void testPrepareFail() {
        WatchlistViewModel watchlistViewModel = new WatchlistViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        WatchlistPresenter presenter = new WatchlistPresenter(viewManagerModel, watchlistViewModel, loggedInViewModel);

        presenter.prepareWatchlistFailView("no");

        assertEquals("no", watchlistViewModel.getState().getMessage());
    }
}
