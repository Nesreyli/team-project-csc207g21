package application.use_case.news;

import application.entities.NewsArticle;

import java.util.List;

public interface NewsDBInterface {
    public List<NewsArticle> fetchNews();
}
