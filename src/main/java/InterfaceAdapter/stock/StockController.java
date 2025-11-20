package InterfaceAdapter.stock;

import Application.Controller.BuyResource;
import Application.Controller.PortfolioResource;
import Application.Controller.SellResource;
import Application.Controller.StockSearchResource;
import Application.UseCases.Buy.OutputDataBuy;
import Application.UseCases.Portfolio.OutputPortfolio;
import Application.UseCases.Sell.OutputDataSell;
import Application.UseCases.Stock_Search.OutputDataSearch;
import InterfaceAdapter.portfolio.PortfolioPresenter;

public class StockController {
    String username;
    String password;
    StockPresenter presenter;

    public StockController() {
        // TODO: idk how to get username and password
        username = "Bob";
        password = "000";
        presenter = new StockPresenter();
    }

    public void fetchStockInfo(String symbol) {
        StockSearchResource stockSearchResource = new StockSearchResource();
        OutputDataSearch query = stockSearchResource.searchStocks(symbol);

        PortfolioResource portfolioResource = new PortfolioResource();
        OutputPortfolio portfolio = portfolioResource.getPortfolio(username, password);

        presenter.updateStockViewModel(query, portfolio);
    }

    public void buyStock(String symbol, String amount) {
        BuyResource buyResource = new BuyResource();
        OutputDataBuy outputBuy = buyResource.buyMarketPrice(symbol, amount, username, password);
        presenter.processBuyData(outputBuy);
    }

    public void sellStock(String symbol, String amount) {
        SellResource sellResource = new SellResource();
        OutputDataSell outputSell = sellResource.buyMarketPrice(symbol, amount, username, password);
        presenter.processSellData(outputSell);
    }

    public void fetchBalance() {
        PortfolioResource portfolioResource = new PortfolioResource();
        OutputPortfolio portfolio = portfolioResource.getPortfolio(username, password);

        PortfolioPresenter portfolioPresenter = new PortfolioPresenter();
    }
}
