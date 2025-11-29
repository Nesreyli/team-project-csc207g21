package UseCase.news;

import Entity.News;

import java.util.List;

public class NewsInteractor implements NewsInputBoundary{
    NewsAccessInterface newsAccessInterface;
    NewsOutputBoundary newsOutputBoundary;

    public NewsInteractor(NewsAccessInterface newsAccessInterface,
                          NewsOutputBoundary newsOutputBoundary){
        this.newsAccessInterface = newsAccessInterface;
        this.newsOutputBoundary = newsOutputBoundary;
    }
    public void execute(){
        try {
            switch (newsAccessInterface.getNews().getStatus_code()) {
                case 200:
                    NewsOutputData newsOutputData = new NewsOutputData(
                            (List<News>) newsAccessInterface.getNews().getEntity());
                    newsOutputBoundary.prepareSuccessView(newsOutputData);
                    break;
                case 400:
                    newsOutputBoundary.prepareFailView("News error");
                    break;
            }
        } catch (RuntimeException e) {
            newsOutputBoundary.prepareFailView("Server Error");
        }
    }
}
