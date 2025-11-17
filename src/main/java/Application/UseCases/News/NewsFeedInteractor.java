package Application.UseCases.News;
import Application.External.NewsApi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Singleton
public class NewsFeedInteractor {
    private final NewsApi newsApi = new NewsApi();
    public OutputNews execute() {
        List<NewsArticle> articles = newsApi.fetchNews();
        return (OutputNews) articles;
    }
}
