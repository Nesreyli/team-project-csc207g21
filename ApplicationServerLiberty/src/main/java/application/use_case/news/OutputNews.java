package application.use_case.news;
import application.entities.NewsArticle;

import java.util.List;
public class OutputNews {
    private List<NewsArticle> articles;
    private int message;

    public OutputNews(){}

    public OutputNews(int code){
        message = code;
    }

    public OutputNews(int code, List<NewsArticle> articles){
        message = code;
        this.articles = articles;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }

    public int getMessage() {
        return message;
    }

    public void setArticles(List<NewsArticle> objects) {
        articles = objects;
    }
}
