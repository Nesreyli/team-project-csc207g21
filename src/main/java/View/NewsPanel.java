package View;
import Entity.News;
import InterfaceAdapter.news.NewsState;
import InterfaceAdapter.news.NewsViewModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
            title.setFont(new Font("Arial", Font.BOLD, 13));
            title.setLocation(title.getLocation().x, title.getLocation().y - 20);
            JTextArea content = new JTextArea(news.getContent());
            content.setLineWrap(true);

            JButton url = addOpenButton(news);
            url.setPreferredSize(new Dimension(25,15));
            JTextArea date = new JTextArea(news.getDate());
            date.setFont(new Font("Arial", Font.ITALIC, 9));
            JPanel datePanel = new JPanel();
            datePanel.setBackground(Color.WHITE);
            datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            datePanel.add(url);
            date.setBackground(new Color(244, 244, 244));
            datePanel.add(date);

            title.setBackground(new Color(244, 244, 244));
            content.setBackground(new Color(244, 244, 244));
            datePanel.setBackground(new Color(244, 244, 244));

            panel.add(title);
            panel.add(content);
            panel.add(datePanel);
            panel.setBorder(new BevelBorder(0));

            panel.setBackground(new Color(244, 244, 244));
            newsPanel.add(panel);
        }
    }

    @NotNull
    private static JButton addOpenButton(News news) {
        JButton url = new JButton("...");
        url.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Desktop.getDesktop().browse(new URI(news.getLink()));
                } catch (Exception ex) {
                }
            }
        });
        return url;
    }
}