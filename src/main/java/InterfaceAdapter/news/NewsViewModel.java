package InterfaceAdapter.news;

import InterfaceAdapter.ViewModel;

public class NewsViewModel extends ViewModel<NewsState> {
    public NewsViewModel() {
        super("news");
        setState(new NewsState());
    }
}
