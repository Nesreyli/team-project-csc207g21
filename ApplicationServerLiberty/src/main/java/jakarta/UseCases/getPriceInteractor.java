package jakarta.UseCases;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.Entities.Price;

import java.util.ArrayList;

//implement where it checks if symbol is valid
@Singleton
public class getPriceInteractor {
    @Inject
    StockDatabaseInterface stockDB;
    public OutputDataPrice executePrice(PricesInput symbols) throws RuntimeException{
        ArrayList<Price> prices;

        try{
            prices = stockDB.checkPrice(symbols);
        } catch (RuntimeException e) {
            return new OutputDataPrice("400");
        }
        return new OutputDataPrice("200", prices);
    }

}
