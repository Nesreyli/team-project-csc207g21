package Presenter;

import Application.UseCases.Sell.OutputDataSell;

public class SellPresenter {
    public String retrieveSellInfo (OutputDataSell odb) {
        return ("You have purchased " + odb.getAmount() + " shares of " + odb.getAmount() + ".\n" +
                "The total price is " + odb.getTotalPrice() + ".\n" +
                odb.getMessage());
    }
}