package Application.UseCases.Price;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import Application.Entities.Price;

import java.util.ArrayList;

//implement where it checks if symbol is valid
@Singleton
public class getPriceInteractor {
    @Inject
    StockDatabaseInterface stockDB;

    public OutputDataPrice executePrice(PricesInput symbols) throws RuntimeException{
        ArrayList<Price> prices;
        ArrayList<Price> openPrices;
        try{
            prices = stockDB.checkPrice(symbols);
            openPrices = stockDB.checkOpen(symbols);
            assert(prices.size() == openPrices.size());
        } catch(Price.IllegalPrice e){
            return new OutputDataPrice("500");
        }
        catch (RuntimeException e) {
            return new OutputDataPrice("400");
        }
        return new OutputDataPrice("200", prices, openPrices);
    }

}
