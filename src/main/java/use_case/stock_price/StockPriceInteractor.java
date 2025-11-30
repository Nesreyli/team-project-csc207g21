package use_case.stock_price;

import entity.Price;
import entity.Response;

public class StockPriceInteractor implements PriceInputBoundary{
    PriceAccessInterface priceAccessInterface;
    PriceOutputBoundary priceOutputBoundary;

    public StockPriceInteractor(PriceAccessInterface priceAccessInterface,
                                PriceOutputBoundary priceOutputBoundary){
        this.priceAccessInterface = priceAccessInterface;
        this.priceOutputBoundary = priceOutputBoundary;
    }

    public void execute(String symbol){
        Response response = priceAccessInterface.getPrice(symbol);

        switch(response.getStatus_code()){
            case 200:
                priceOutputBoundary.prepareSuccessView((Price)response.getEntity());
                break;
            case 400:
                priceOutputBoundary.prepareFailView("Server Error");
        }
    }
}
