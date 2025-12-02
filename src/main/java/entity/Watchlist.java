package entity;

import java.util.List;

import org.json.JSONObject;

/**
 * A simple Entity representing the Watchlist.
 */

public class Watchlist {
    private String username;
    private List<String> symbols;
    private JSONObject prices;

    public Watchlist() {

    }

    public Watchlist(String username, List<String> symbols,
                     JSONObject prices) {
        this.username = username;
        this.symbols = symbols;
        this.prices = prices;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public JSONObject getPrices() {
        return prices;
    }

    public static class Builder {
        private final Watchlist watchlist;

        public Builder() {
            watchlist = new Watchlist();
        }

        /**
         * Builds username.
         * @param username the username associated
         * @return the current builder instance
         */
        public Builder username(String username) {
            watchlist.username = username;
            return this;
        }

        /**
         * Builds the list of stock symbols.
         * @param symbols the symbols associated
         * @return the current builder instance
         */
        public Builder symbols(List<String> symbols) {
            watchlist.symbols = symbols;
            return this;
        }
        /**
         * Builds prices.
         * @param prices the price associated
         * @return the current builder instance
         */

        public Builder prices(JSONObject prices) {
            watchlist.prices = prices;
            return this;
        }

        /**
         * Builds the watchlist.
         * @return the built watchlist
         */
        public Watchlist build() {
            return watchlist;
        }
    }
}
