package Application.Database;
import Application.Entities.NewsArticle;
import Application.UseCases.news.NewsDBInterface;
import jakarta.enterprise.context.ApplicationScoped;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class NewsApi implements NewsDBInterface {
    private static final String API_KEY;

    static{
        try {
            API_KEY = InitialContext.doLookup("BloomAPIKey");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<NewsArticle> fetchNews() {
        List<NewsArticle> articles = new ArrayList<>();
        String url = "https://newsapi.org/v2/top-headlines?sources=bloomberg&apiKey=" + API_KEY;

        try {

            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = new String(response.body().string().getBytes(StandardCharsets.UTF_8));
            JSONObject root = new JSONObject(responseBody);
            JSONArray newsArray = root.getJSONArray("articles");
            response.close();

            int limit = Math.min(5, newsArray.length());

            if (root.getString("status").equals("ok")) {
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
