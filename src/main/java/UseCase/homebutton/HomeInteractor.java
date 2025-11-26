package UseCase.homebutton;

public class HomeInteractor implements HomeInputBoundary{
    private HomeOutputBoundary homeOutputBoundary;

    public HomeInteractor(HomeOutputBoundary homeOutputBoundary){
        this.homeOutputBoundary = homeOutputBoundary;
    }
    @Override
    public void executePrevious(String username, String password) {
        HomeOutputData homeOutputData = new HomeOutputData(username, password);
        homeOutputBoundary.preparePreviousView(homeOutputData);
    }
}
