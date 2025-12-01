package use_case.news;

import java.util.List;

import entity.News;

public class NewsOutputData {
    private List<News> newsList;

    public NewsOutputData(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
