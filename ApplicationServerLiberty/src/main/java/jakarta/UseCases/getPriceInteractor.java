package jakarta.UseCases;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.Entities.Price;

//implement where it checks if symbol is valid
@Singleton
public class getPriceInteractor {
    @Inject
    StockDatabaseInterface stockDB;
    public OutputDataPrice executePrice(String symbol) throws RuntimeException{
        Price price;
        try{
            price =stockDB.checkPrice(symbol);
        } catch (RuntimeException e) {
            return new OutputDataPrice("400", null, null);
        }
        return new OutputDataPrice("200", price.getSymbol(), price.getPrice());
    }

}
