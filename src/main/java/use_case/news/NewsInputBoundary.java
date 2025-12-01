package use_case.news;

public interface NewsInputBoundary {
    /**
     * Executes the News use case.
     * Implementations fetch news data from the data access interface
     * and pass the results to the output boundary for presentation.
     */
    void execute();
}
