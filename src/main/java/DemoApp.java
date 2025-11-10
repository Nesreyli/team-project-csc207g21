import javax.naming.InitialContext;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class DemoApp {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = "http://100.67.8.245:4848/rest/stocks/price/?symbols=";
        Request request = new Request.Builder().
                url(url + "AAPL").
                get().
                addHeader("accept", "application/json").
                build();
        Response response;

            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        System.out.println(new JSONObject(response.body().string()));
    }
}
