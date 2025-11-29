package Entity;

import org.json.JSONObject;

import java.util.List;

public class Watchlist {
    private String username;
    private List<String> symbols;
    private JSONObject prices;

    public Watchlist() {}

    public Watchlist(String username, List<String> symbols,
                     JSONObject prices) {
        this.username = username;
        this.symbols = symbols;
        this.prices = prices;
    }

    public String getUsername() { return username; }
    public List<String> getSymbols() { return symbols; }
    public JSONObject getPrices() { return prices; }

    public static class Builder {
        private final Watchlist watchlist;

        public Builder() {
            watchlist = new Watchlist();
        }

        public Builder username(String username) {
            watchlist.username = username;
            return this;
        }

        public Builder symbols(List<String> symbols) {
            watchlist.symbols = symbols;
            return this;
        }

        public Builder prices(JSONObject prices) {
            watchlist.prices = prices;
            return this;
        }

        public Watchlist build() {
            return watchlist;
        }
    }
}
