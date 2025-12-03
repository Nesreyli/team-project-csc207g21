package interface_adapter.news;

import java.util.List;

import entity.News;

public class NewsState {
    private List<News> news;
    private String error;

    public NewsState() {

    }

    public List<News> getNews() {
        return news;
    }

    /**
     * Updates the list of news items stored in this state.
     * @param news the list of News objects to set
     * @return this NewsState instance for method chaining
     */
    public NewsState setNews(List<News> news) {
        this.news = news;
        return this;
    }

    public String getError() {
        return error;
    }

    /**
     * Sets an error message for this state, typically used when
     * retrieving news fails.
     * @param error the error message to set
     * @return this NewsState instance for method chaining
     */
    public NewsState setError(String error) {
        this.error = error;
        return this;
    }
}
