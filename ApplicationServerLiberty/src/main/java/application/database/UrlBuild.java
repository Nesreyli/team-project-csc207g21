package application.database;

import java.util.List;

// should i have headers of http request as param too. Perhaps make it easier to change things later
// if i want something like txt instead of json ...
// could have this as actual class...
public interface UrlBuild {
    /**
     * URL builder for building url for ALPACA API.
     * @param url url of ALPACA
     * @param stocks stocks to search
     * @param feed which market feed to use
     * @return string of complete url
     */
    String buildUrl(String url, List<String> stocks, String feed);
}
