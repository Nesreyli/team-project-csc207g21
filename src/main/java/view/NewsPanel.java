package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;

import javax.swing.*;

import entity.News;
import interface_adapter.news.NewsState;
import interface_adapter.news.NewsViewModel;
import view.theme.StyledButton;
import view.theme.StyledCardPanel;
import view.theme.UiTheme;
import view.theme.UiTheme.*;

/**
 * NewsPanel displays a scrollable list of the latest market news articles.
 * Each article is shown as a styled card with title, content preview,
 * publication date, and a button to open the full article in the browser.
 * Listens to updates from the NewsViewModel.
 */
public class NewsPanel extends StyledCardPanel implements PropertyChangeListener {

    private final JPanel contentPanel;
    private final NewsViewModel newsViewModel;

    /**
     * Constructs a NewsPanel that listens to the provided NewsViewModel.
     *
     * @param newsViewModel the NewsViewModel providing news updates
     */
    public NewsPanel(NewsViewModel newsViewModel) {
        this.newsViewModel = newsViewModel;
        newsViewModel.addPropertyChangeListener(this);

        final JLabel header = new JLabel("Latest Market News");
        header.setFont(new Font(UiTheme.FONT_NAME, Font.BOLD, 18));
        header.setForeground(UiTheme.TEXT_DARK);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(UiTheme.CARD_BG);

        final JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);

        this.setBackground(UiTheme.BG);
        this.add(header);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(scroll);
    }

    /**
     * Responds to changes in the NewsViewModel by rebuilding the list
     * of news article cards.
     *
     * @param evt the PropertyChangeEvent containing the new NewsState
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final NewsState state = (NewsState) evt.getNewValue();
        contentPanel.removeAll();

        for (News news : state.getNews()) {
            final JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(UiTheme.CARD_BG);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UiTheme.BG, 1),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)
            ));

            final JLabel title = new JLabel("<html><b>" + news.getTitle() + "</b></html>");
            title.setFont(new Font(UiTheme.FONT_NAME, Font.BOLD, 14));
            title.setForeground(UiTheme.TEXT_DARK);
            title.setAlignmentX(Component.LEFT_ALIGNMENT);

            final JTextArea body = new JTextArea(news.getContent());
            body.setLineWrap(true);
            body.setWrapStyleWord(true);
            body.setEditable(false);
            body.setFont(new Font(UiTheme.FONT_NAME, Font.PLAIN, 13));
            body.setBackground(UiTheme.CARD_BG);
            body.setForeground(UiTheme.TEXT_DARK);
            body.setAlignmentX(Component.LEFT_ALIGNMENT);

            final JButton readButton = StyledButton.createOutlineButton("Read Article");
            readButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            readButton.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(news.getLink()));
                }
                catch (Exception ignored) {
                }
            });

            final JLabel dateLabel = new JLabel(news.getDate());
            dateLabel.setFont(new Font(UiTheme.FONT_NAME, Font.ITALIC, 11));
            dateLabel.setForeground(UiTheme.TEXT_DARK.darker());
            dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            card.add(title);
            card.add(Box.createRigidArea(new Dimension(0, 5)));
            card.add(body);
            card.add(Box.createRigidArea(new Dimension(0, 10)));
            card.add(readButton);
            card.add(Box.createRigidArea(new Dimension(0, 5)));
            card.add(dateLabel);

            contentPanel.add(card);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
