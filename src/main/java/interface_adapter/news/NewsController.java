package interface_adapter.news;

import use_case.news.NewsInputBoundary;

public class NewsController {
    private NewsInputBoundary newsInputBoundary;

    public NewsController(NewsInputBoundary newsInputBoundary) {
        this.newsInputBoundary = newsInputBoundary;
    }

    public void execute(){
        newsInputBoundary.execute();
    }
}
