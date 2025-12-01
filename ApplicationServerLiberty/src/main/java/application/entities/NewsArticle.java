package application.entities;

public class NewsArticle {
    private String title;
    private String content;
    private String news_url;
    private String author;
    private String date;
    private String image;

    public NewsArticle() {

    }

    public NewsArticle(String title, String content, String news_url,
                       String author, String date, String image) {
        this.title = title;
        this.content = content;
        this.news_url = news_url;
        this.author = author;
        this.date = date;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNews_url() {
        return news_url;
    }

    public String getAuthor() {

        return author;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }
}
