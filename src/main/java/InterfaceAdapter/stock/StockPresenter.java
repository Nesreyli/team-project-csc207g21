package InterfaceAdapter.stock;

import Application.Controller.PortfolioResource;
import Application.Entities.Stock;
import Application.UseCases.Buy.OutputDataBuy;
import Application.UseCases.Portfolio.OutputPortfolio;
import Application.UseCases.Sell.OutputDataSell;
import Application.UseCases.Stock_Search.OutputDataSearch;

import java.util.Map;

public class StockPresenter {
    public StockViewModel stockViewModel;

    public StockPresenter() {
        this.stockViewModel = new StockViewModel();
    }

    public void updateStockViewModel(OutputDataSearch search, OutputPortfolio portfolio) {
        // Get stock data
        Stock stockObject = search.getStocks().get(0);
        Double stockPrice = stockObject.getPrice().doubleValue();
        String stockName = stockObject.getSymbol();
        String stockCompany = stockObject.getCompany();
        String stockCountry = stockObject.getCountry();
        String stockDetails = "Company: " + stockCompany + "\nCountry: " + stockCountry;

        // Get portfolio data
        Map<String, Integer> holdings = portfolio.getHoldings();
        Integer quantity = holdings.get(stockName);

        // Set new state
        stockViewModel.setStockState(stockName, stockDetails, stockPrice, quantity);
    }

    public void processBuyData(OutputDataBuy odb) {
        // TODO: Change Portfolio state here and update GUI
    }

    public void processSellData(OutputDataSell ods) {
        // TODO; Change Portfolio state here and update GUI
    }
}
