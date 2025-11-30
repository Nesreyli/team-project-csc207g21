package interface_adapter.news;

import interface_adapter.ViewModel;

public class NewsViewModel extends ViewModel<NewsState> {
    public NewsViewModel() {
        super("news");
        setState(new NewsState());
    }
}
