package View;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class NewsPanel extends JPanel {

    private JTextArea newsTextArea;

    public NewsPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 350));
        setBorder(BorderFactory.createTitledBorder("Latest News"));

        newsTextArea = new JTextArea();
        newsTextArea.setEditable(false);
        newsTextArea.setLineWrap(true);
        newsTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(newsTextArea);
        add(scrollPane, BorderLayout.CENTER);

        loadNews();
    }

    private void loadNews() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {

                String url = "http://localhost:4848/news/get";

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();

                try (Response response = client.newCall(request).execute()) {

                    String body = response.body().string();
                    JSONArray arr = new JSONArray(body);

                    StringBuilder text = new StringBuilder();

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);

                        String title = obj.getString("title");
                        String desc = obj.optString("description", "");
                        String link = obj.getString("url");
                        text.append("- ").append(title).append("\n");
                        text.append(link).append("\n");
                        if (!desc.isEmpty()) {
                            text.append("   ").append(desc).append("\n");
                        }
                        text.append("\n");
                    }

                    newsTextArea.setText(text.toString());

                } catch (IOException e) {
                    newsTextArea.setText("Error loading news: " + e.getMessage());
                }

                return null;
            }
        };

        worker.execute();
    }
}