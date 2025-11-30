package application.database;

import java.util.List;

// should i have headers of http request as param too. Perhaps make it easier to change things later
// if i want something like txt instead of json ...
// could have this as actual class...
public interface UrlBuild {
    String buildUrl(String url, List<String> stocks, String feed);
}
