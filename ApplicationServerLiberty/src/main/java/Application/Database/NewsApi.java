package Application.Database;
import Application.Entities.NewsArticle;
import Application.UseCases.news.NewsDBInterface;
import jakarta.enterprise.context.ApplicationScoped;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class NewsApi implements NewsDBInterface {
    private static final String API_KEY = "f8df4a9810d546f689a4a9f9b76cf470";

    public List<NewsArticle> fetchNews() {
        List<NewsArticle> articles = new ArrayList<>();
        String url = "https://newsapi.org/v2/top-headlines?sources=bloomberg&apiKey=" + API_KEY;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject root = new JSONObject(response.body());
            JSONArray newsArray = root.getJSONArray("articles");
            int limit = Math.min(5, newsArray.length());

            if (response.statusCode() == 200) {
                for (int i = 0; i < limit; i++) {
                    JSONObject obj = newsArray.getJSONObject(i);
                    String title = obj.getString("title");
                    String author = obj.getString("author");
                    String content = obj.getString("description");
                    String date = obj.getString("publishedAt");
                    String news_url = obj.getString("url");
                    String urlImage = obj.getString("urlToImage");
                    articles.add(new NewsArticle(title, content, news_url, author, date, urlImage));
                }
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return articles;
    }
}
