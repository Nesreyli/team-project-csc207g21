package use_case.homebutton;

public interface HomeOutputBoundary {
    /**
     * Prepares the view for returning to the previous screen.
     *
     * @param output the data required to display the previous view correctly
     */
    void preparePreviousView(HomeOutputData output);
}
