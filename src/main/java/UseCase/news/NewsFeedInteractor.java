package UseCase.news;
import DataAccess.NewsApi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class NewsFeedInteractor {
    @Inject
    private NewsApi newsApi;

    public OutputNews execute() {
        List<NewsArticle> articles = newsApi.fetchNews();
        return new OutputNews(articles);
    }
}
