package com.nethit.ui.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;

import com.nethit.ui.theme.AppTheme;

/**
 * Kelas dasar semua halaman: header (judul + subjudul) di atas dan area konten
 * yang bisa di-scroll di bawahnya. Subclass mengisi konten lewat {@link #addCard()}.
 */
public abstract class PagePanel extends JPanel {

    /** Lebar teks paragraf agar baris otomatis terbungkus. */
    private static final int TEXT_WIDTH = 520;

    private final ScrollableContent content = new ScrollableContent();

    protected PagePanel(String title, String subtitle) {
        super(new BorderLayout());
        setBackground(AppTheme.BACKGROUND);

        add(buildHeader(title, subtitle), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(AppTheme.BACKGROUND);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel buildHeader(String title, String subtitle) {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(AppTheme.BACKGROUND);
        header.setBorder(BorderFactory.createEmptyBorder(
                AppTheme.SPACE_XXL, AppTheme.SPACE_XXL, AppTheme.SPACE_LG, AppTheme.SPACE_XXL));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppTheme.FONT_TITLE);
        titleLabel.setForeground(AppTheme.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(AppTheme.FONT_BODY);
        subtitleLabel.setForeground(AppTheme.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(titleLabel);
        header.add(Box.createVerticalStrut(AppTheme.SPACE_XS));
        header.add(subtitleLabel);
        return header;
    }

    /** Membuat kartu putih kosong, menambahkannya ke halaman, lalu mengembalikannya untuk diisi. */
    protected final Card addCard() {
        Card card = new Card();
        content.add(card);
        content.add(Box.createVerticalStrut(AppTheme.SPACE_LG));
        return card;
    }

    /** Kartu sementara untuk halaman yang logikanya belum dibuat. */
    protected final void addComingSoonCard(String message) {
        Card card = addCard();
        card.add(createHeading("Segera hadir"));
        card.add(createParagraph(message));
    }

    protected static JLabel createHeading(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppTheme.FONT_HEADING);
        label.setForeground(AppTheme.TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, AppTheme.SPACE_SM, 0));
        return label;
    }

    /** Paragraf yang membungkus baris otomatis. Boleh berisi tag HTML sederhana seperti b dan br. */
    protected static JLabel createParagraph(String htmlText) {
        JLabel label = new JLabel("<html><body style='width:" + TEXT_WIDTH + "px'>"
                + htmlText + "</body></html>");
        label.setFont(AppTheme.FONT_BODY);
        label.setForeground(AppTheme.TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    /** Kartu putih dengan border tipis dan padding. */
    protected static class Card extends JPanel {

        Card() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(AppTheme.SURFACE);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AppTheme.DIVIDER),
                    BorderFactory.createEmptyBorder(AppTheme.SPACE_XL, AppTheme.SPACE_XL,
                            AppTheme.SPACE_XL, AppTheme.SPACE_XL)));
        }

        /** Melebar penuh, tetapi tingginya tidak ikut diregangkan oleh BoxLayout. */
        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
        }
    }

    /** Panel konten yang lebarnya mengikuti jendela dan tingginya mengikuti isi (scroll vertikal). */
    private static class ScrollableContent extends JPanel implements Scrollable {

        ScrollableContent() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(AppTheme.BACKGROUND);
            setBorder(BorderFactory.createEmptyBorder(
                    0, AppTheme.SPACE_XXL, AppTheme.SPACE_XXL, AppTheme.SPACE_XXL));
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return orientation == javax.swing.SwingConstants.VERTICAL
                    ? visibleRect.height : visibleRect.width;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}