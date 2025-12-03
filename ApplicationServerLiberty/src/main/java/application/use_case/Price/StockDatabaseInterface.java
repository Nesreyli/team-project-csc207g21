package application.use_case.Price;

import application.entities.Price;

import java.util.ArrayList;

public interface StockDatabaseInterface {
    Price checkOrder(PricesInput symbols);

    ArrayList<Price> checkPrice(PricesInput symbols);

    ArrayList<Price> checkOpen(PricesInput symbols);
}
