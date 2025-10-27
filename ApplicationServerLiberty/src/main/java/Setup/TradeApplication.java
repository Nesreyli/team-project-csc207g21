package Setup;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class TradeApplication extends Application {
    // Needed to enable Jakarta REST and specify path.
}
