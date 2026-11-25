package com.nethit.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nethit.ui.panels.AboutPanel;
import com.nethit.ui.panels.CidrCalculatorPanel;
import com.nethit.ui.panels.IpCalculatorPanel;
import com.nethit.ui.panels.SubnetCalculatorPanel;
import com.nethit.ui.theme.AppTheme;

/**
 * Jendela utama netHIT: sidebar menu di kiri dan area halaman (CardLayout) di kanan.
 */
public class MainFrame extends JFrame {

    private static final String APP_TITLE = "netHIT - Network Hitung";
    private static final int SIDEBAR_WIDTH = 220;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 650;
    private static final int MIN_WIDTH = 900;
    private static final int MIN_HEIGHT = 600;

    // Kunci halaman untuk CardLayout
    private static final String PAGE_IP = "ip";
    private static final String PAGE_SUBNET = "subnet";
    private static final String PAGE_CIDR = "cidr";
    private static final String PAGE_ABOUT = "about";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel pageContainer = new JPanel(cardLayout);
    private final Map<String, SidebarButton> menuButtons = new LinkedHashMap<>();

    public MainFrame() {
        super(APP_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        buildPages();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buildSidebar(), BorderLayout.WEST);
        getContentPane().add(pageContainer, BorderLayout.CENTER);

        setLocationRelativeTo(null); // posisikan di tengah layar
        showPage(PAGE_IP);
    }

    private void buildPages() {
        pageContainer.setBackground(AppTheme.BACKGROUND);
        pageContainer.add(new IpCalculatorPanel(), PAGE_IP);
        pageContainer.add(new SubnetCalculatorPanel(), PAGE_SUBNET);
        pageContainer.add(new CidrCalculatorPanel(), PAGE_CIDR);
        pageContainer.add(new AboutPanel(), PAGE_ABOUT);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(AppTheme.SIDEBAR);
        sidebar.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(
                AppTheme.SPACE_XL, AppTheme.SPACE_LG, AppTheme.SPACE_XL, AppTheme.SPACE_LG));

        JLabel brand = new JLabel("netHIT");
        brand.setFont(AppTheme.FONT_TITLE);
        brand.setForeground(AppTheme.TEXT_ON_PRIMARY);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        brand.setBorder(BorderFactory.createEmptyBorder(0, AppTheme.SPACE_MD, 0, 0));

        JLabel tagline = new JLabel("Network Hitung");
        tagline.setFont(AppTheme.FONT_SMALL);
        tagline.setForeground(AppTheme.TEXT_MUTED);
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);
        tagline.setBorder(BorderFactory.createEmptyBorder(0, AppTheme.SPACE_MD, 0, 0));

        JLabel caption = new JLabel("KALKULATOR");
        caption.setFont(AppTheme.FONT_SMALL);
        caption.setForeground(AppTheme.TEXT_MUTED);
        caption.setAlignmentX(Component.LEFT_ALIGNMENT);
        caption.setBorder(BorderFactory.createEmptyBorder(0, AppTheme.SPACE_MD, 0, 0));

        sidebar.add(brand);
        sidebar.add(tagline);
        sidebar.add(Box.createVerticalStrut(AppTheme.SPACE_XXL));
        sidebar.add(caption);
        sidebar.add(Box.createVerticalStrut(AppTheme.SPACE_SM));

        addMenuButton(sidebar, PAGE_IP, "IP Address");
        addMenuButton(sidebar, PAGE_SUBNET, "Subnet");
        addMenuButton(sidebar, PAGE_CIDR, "CIDR");

        sidebar.add(Box.createVerticalGlue()); // dorong "Tentang" ke bawah
        addMenuButton(sidebar, PAGE_ABOUT, "Tentang");

        return sidebar;
    }

    private void addMenuButton(JPanel sidebar, String pageKey, String text) {
        SidebarButton button = new SidebarButton(text);
        button.addActionListener(e -> showPage(pageKey));
        menuButtons.put(pageKey, button);

        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(AppTheme.SPACE_XS));
    }

    /** Menampilkan satu halaman dan menandai menu yang sesuai sebagai aktif. */
    private void showPage(String pageKey) {
        cardLayout.show(pageContainer, pageKey);
        menuButtons.forEach((key, button) -> button.setSelected(key.equals(pageKey)));
    }

    /** Tombol menu sidebar: biru saat aktif, abu gelap saat di-hover. */
    private static class SidebarButton extends JButton {

        private boolean selected;
        private boolean hovered;

        SidebarButton(String text) {
            super(text);
            setFont(AppTheme.FONT_BODY);
            setForeground(AppTheme.SIDEBAR_TEXT);
            setHorizontalAlignment(LEFT);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(
                    AppTheme.SPACE_MD, AppTheme.SPACE_MD, AppTheme.SPACE_MD, AppTheme.SPACE_MD));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    repaint();
                }
            });
        }

        @Override
        public void setSelected(boolean selected) {
            this.selected = selected;
            setForeground(selected ? AppTheme.TEXT_ON_PRIMARY : AppTheme.SIDEBAR_TEXT);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            if (selected) {
                g2.setColor(AppTheme.PRIMARY);
                g2.fillRoundRect(0, 0, w, h, AppTheme.RADIUS, AppTheme.RADIUS);
            } else if (hovered) {
                g2.setColor(AppTheme.SIDEBAR_HOVER);
                g2.fillRoundRect(0, 0, w, h, AppTheme.RADIUS, AppTheme.RADIUS);
            }

            // Cincin fokus untuk pengguna keyboard (tidak ditampilkan pada menu yang aktif)
            if (isFocusOwner() && !selected) {
                g2.setColor(AppTheme.PRIMARY_RING);
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, w - 3, h - 3, AppTheme.RADIUS, AppTheme.RADIUS);
            }

            g2.dispose();
            super.paintComponent(g); // menggambar teks
        }

        /** Melebar penuh mengikuti sidebar, tinggi tetap. */
        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
        }
    }
}