package application.use_case.Stock_Search;

import application.entities.Stock;
import java.util.List;

public class OutputDataSearch {
    private final String message;
    private final List<Stock> stocks;

    public OutputDataSearch(String message, List<Stock> stocks) {
        this.message = message;
        this.stocks = stocks;
    }

    public String getMessage(){
        return message;
    }

    public List<Stock> getStocks(){
        return stocks;
    }

    public boolean isSuccess(){
        return "200".equals(message);
    }

}
