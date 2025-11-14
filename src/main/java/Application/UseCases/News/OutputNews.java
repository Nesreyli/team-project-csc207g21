package Application.UseCases.News;
import java.util.List;

public class OutputNews {
    private List<NewsArticle> articles;

    public OutputNews(List<NewsArticle> articles){
        this.articles = articles;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsArticle> articles) {
        this.articles = articles;
    }
}
