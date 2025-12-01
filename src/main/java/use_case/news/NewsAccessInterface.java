package use_case.news;

public interface NewsAccessInterface {
    /**
     * Retrieves news data from the data source.
     *
     * @return a Response object containing the news data or
     *         information about why the retrieval failed
     */
    entity.Response getNews();
}
