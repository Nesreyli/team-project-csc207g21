package View;
import Entity.News;
import InterfaceAdapter.news.NewsState;
import InterfaceAdapter.news.NewsViewModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class NewsPanel extends JPanel implements PropertyChangeListener {
    private final JPanel newsPanel;
    private NewsViewModel newsViewModel;
    private JTextArea newsTextArea;

    public NewsPanel(NewsViewModel newsViewModel) {
        this.newsViewModel = newsViewModel;
        newsViewModel.addPropertyChangeListener(this);

        newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Latest News"));
        JScrollPane newsScroll = new JScrollPane(newsPanel);

        this.add(newsScroll);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        NewsState newsState = (NewsState) evt.getNewValue();
        newsPanel.removeAll();
        for(News news: newsState.getNews()){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JTextArea title = new JTextArea(news.getTitle());
            title.setAlignmentX(LEFT_ALIGNMENT);
            JTextArea content = new JTextArea(news.getContent());
            content.setAlignmentX(LEFT_ALIGNMENT);
            JTextArea date = new JTextArea(news.getDate());
            date.setAlignmentX(LEFT_ALIGNMENT);

            panel.add(title);
            panel.add(content);
            panel.add(date);

            newsPanel.add(panel);
        }
    }
}