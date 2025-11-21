package Application.UseCases.news;
import Application.Database.NewsApi;
import Application.Entities.NewsArticle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class NewsFeedInteractor {
    @Inject
    private NewsDBInterface newsApi;

    public OutputNews execute() {
        List<NewsArticle> articles;
        try{
            articles = newsApi.fetchNews();
        } catch (Exception e) {
            return new OutputNews(400);
        }
        return new OutputNews(200, articles);
    }
}
