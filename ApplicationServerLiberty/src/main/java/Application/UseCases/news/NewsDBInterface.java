package Application.UseCases.news;

import Application.Entities.NewsArticle;

import java.util.List;

public interface NewsDBInterface {
    public List<NewsArticle> fetchNews();
}
