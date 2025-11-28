package InterfaceAdapter.news;

import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import UseCase.news.NewsInputBoundary;
import UseCase.news.NewsOutputBoundary;
import UseCase.news.NewsOutputData;
import kotlin.NotImplementedError;

public class NewsPresenter implements NewsOutputBoundary {
    private ViewManagerModel viewManagerModel;
    private NewsViewModel newsViewModel;

    public NewsPresenter(ViewManagerModel viewManagerModel,
                         NewsViewModel newsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.newsViewModel = newsViewModel;
    }

    @Override
    public void prepareSuccessView(NewsOutputData newsOutputData) {
        NewsState newsState = newsViewModel.getState();
        newsState.setNews(newsOutputData.getNewsList());
        newsViewModel.firePropertyChange();

        viewManagerModel.setState("news");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String message) {
        throw new NotImplementedError();
    }
}
