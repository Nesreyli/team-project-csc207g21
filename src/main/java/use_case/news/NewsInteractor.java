package use_case.news;

import java.util.List;

import entity.News;

public class NewsInteractor implements NewsInputBoundary {
    private NewsAccessInterface newsAccessInterface;
    private NewsOutputBoundary newsOutputBoundary;

    public NewsInteractor(NewsAccessInterface newsAccessInterface,
                          NewsOutputBoundary newsOutputBoundary) {
        this.newsAccessInterface = newsAccessInterface;
        this.newsOutputBoundary = newsOutputBoundary;
    }

    /**
     * Executes the News use case.
     * Fetches news data through the data access interface and passes
     * the results to the output boundary. Handles both success and failure scenarios.
     */
    public void execute() {
        try {
            switch (newsAccessInterface.getNews().getStatus_code()) {
                case 200:
                    final NewsOutputData newsOutputData = new NewsOutputData(
                            (List<News>) newsAccessInterface.getNews().getEntity());
                    newsOutputBoundary.prepareSuccessView(newsOutputData);
                    break;
                case 400:
                    newsOutputBoundary.prepareFailView("News error");
                    break;
            }
        }
        catch (RuntimeException error) {
            newsOutputBoundary.prepareFailView("Server Error");
        }
    }
}
