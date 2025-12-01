package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;

import javax.swing.*;
import javax.swing.border.*;

import org.jetbrains.annotations.NotNull;

import entity.News;
import interface_adapter.news.NewsState;
import interface_adapter.news.NewsViewModel;

public class NewsPanel extends JPanel implements PropertyChangeListener {
    private final JPanel newsPanel;
    private NewsViewModel newsViewModel;
    private JTextArea newsTextArea;
    private Color bright = new Color(214, 216, 220);
    private Color dark = new Color(43, 45, 48);

    public NewsPanel(NewsViewModel newsViewModel) {
        this.newsViewModel = newsViewModel;
        newsViewModel.addPropertyChangeListener(this);

        newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        final var title = BorderFactory.createTitledBorder("Latest News");
        title.setTitleColor(bright);
        setBorder(title);
        final JScrollPane newsScroll = new JScrollPane(newsPanel);
        this.add(newsScroll);
        this.setBackground(dark);
        this.setForeground(bright);
        newsScroll.setBackground(dark);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final NewsState newsState = (NewsState) evt.getNewValue();
        newsPanel.removeAll();
        for (News news: newsState.getNews()) {
            final JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            final JTextArea title = new JTextArea(news.getTitle());
            title.setFont(new Font("Arial", Font.BOLD, 13));
            title.setLocation(title.getLocation().x, title.getLocation().y - 20);
            title.setForeground(bright);
            final JTextArea content = new JTextArea(news.getContent());
            content.setLineWrap(true);
            content.setForeground(bright);

            final JButton url = addOpenButton(news);
            url.setPreferredSize(new Dimension(20, 14));
            final JTextArea date = new JTextArea(news.getDate());
            date.setFont(new Font("Arial", Font.ITALIC, 9));
            final JPanel datePanel = new JPanel();
            datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            url.setOpaque(true);
            url.setBackground(bright);
            url.setForeground(dark);
            datePanel.add(url);
            date.setForeground(bright);
            date.setBackground(dark);
            datePanel.add(date);

            title.setBackground(dark);
            content.setBackground(dark);
            datePanel.setBackground(dark);

            panel.add(title);
            panel.add(content);
            panel.add(datePanel);
            panel.setBorder(new BevelBorder(0));

            panel.setBackground(dark);
            newsPanel.add(panel);
        }
    }

    @NotNull
    private static JButton addOpenButton(News news) {
        final JButton url = new JButton("...");
        url.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(news.getLink()));
                }
                catch (Exception ex) {
                }
            }
        });
        return url;
    }
}
