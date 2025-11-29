package InterfaceAdapter.news;

import UseCase.news.NewsInputBoundary;

public class NewsController {
    private NewsInputBoundary newsInputBoundary;

    public NewsController(NewsInputBoundary newsInputBoundary) {
        this.newsInputBoundary = newsInputBoundary;
    }

    public void execute(){
        newsInputBoundary.execute();
    }
}
