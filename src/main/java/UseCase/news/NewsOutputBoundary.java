package UseCase.news;

public interface NewsOutputBoundary {
    void prepareSuccessView(NewsOutputData newsOutputData);

    void prepareFailView(String message);
}
