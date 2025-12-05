package application.use_case.Price;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import application.entities.Price;

import java.util.ArrayList;

// implement where it checks if symbol is valid
@Singleton
public class getPriceInteractor {
    @Inject
    StockDatabaseInterface stockDb;

    public OutputDataPrice executePrice(PricesInput symbols){
        ArrayList<Price> prices;
        ArrayList<Price> openPrices;
        try{
            prices = stockDb.checkPrice(symbols);
            openPrices = stockDb.checkOpen(symbols);
            assert(prices.size() == openPrices.size());
        } catch(Price.IllegalPrice e){
            return new OutputDataPrice("500");
        }
        catch (RuntimeException error) {
            return new OutputDataPrice("400");
        }
        return new OutputDataPrice("200", prices, openPrices);
    }

}
