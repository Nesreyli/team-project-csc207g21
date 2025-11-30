package use_case.news;

import entity.News;

import java.util.List;

public class NewsOutputData {
    List<News> newsList;

    public NewsOutputData(List<News> newsList){
        this.newsList = newsList;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
