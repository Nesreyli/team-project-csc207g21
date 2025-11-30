package interface_adapter.news;

import entity.News;

import java.util.List;

public class NewsState {
    private List<News> news;
    private String error;

    public NewsState(){}

    public List<News> getNews() {
        return news;
    }

    public NewsState setNews(List<News> news) {
        this.news = news;
        return this;
    }

    public String getError() {
        return error;
    }

    public NewsState setError(String error) {
        this.error = error;
        return this;
    }
}
