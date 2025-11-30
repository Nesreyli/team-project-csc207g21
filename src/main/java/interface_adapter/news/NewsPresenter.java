package interface_adapter.news;

import interface_adapter.ViewManagerModel;
import use_case.news.NewsOutputBoundary;
import use_case.news.NewsOutputData;
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
