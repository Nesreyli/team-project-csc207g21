package Application.UseCases.News;

public class NewsArticle {
    private String title;
    private String content;
    private String news_url;

    public NewsArticle(String title, String content, String news_url) {
        this.title = title;
        this.content = content;
        this.news_url = news_url;
    }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getNews_url() { return news_url; }
}
