package application.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONArray;
import org.json.JSONObject;

import application.entities.NewsArticle;
import application.use_case.news.NewsDBInterface;
import jakarta.enterprise.context.ApplicationScoped;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@ApplicationScoped
public class NewsApi implements NewsDBInterface {
    private static final String API_KEY;
    private static final int NEWS_AMOUNT = 5;

    static {
        try {
            API_KEY = InitialContext.doLookup("BloomAPIKey");
        }
        catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Http reqeust to web API for news.
     * @return List of news
     * @throws RuntimeException exception
     */
    public List<NewsArticle> fetchNews() {
        final List<NewsArticle> articles = new ArrayList<>();
        final String url = "https://newsapi.org/v2/top-headlines?sources=bloomberg&apiKey=" + API_KEY;

        try {
            final OkHttpClient client = new OkHttpClient.Builder().build();
            final Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            final Response response = client.newCall(request).execute();
            final String responseBody = new String(response.body().string());
            final JSONObject root = new JSONObject(responseBody);
            final JSONArray newsArray = root.getJSONArray("articles");
            response.close();

            final int limit = Math.min(NEWS_AMOUNT, newsArray.length());

            if (root.getString("status").equals("ok")) {
                for (int i = 0; i < limit; i++) {
                    final JSONObject obj = newsArray.getJSONObject(i);
                    final String title = obj.getString("title");
                    final String author;
                    if (obj.get("author").equals(null)) {
                        author = "";
                    }
                    else {
                        author = obj.getString("author");
                    }
                    final String content = obj.getString("description");
                    final String date = obj.getString("publishedAt");
                    final String newsUrl = obj.getString("url");
                    final String urlImage = obj.getString("urlToImage");
                    articles.add(new NewsArticle(title, content, newsUrl, author, date, urlImage));
                }
            }
            else {
                throw new RuntimeException();
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return articles;
    }
}
