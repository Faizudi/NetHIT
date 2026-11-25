package com.nethit.ui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.nethit.ui.theme.AppTheme;

/**
 * Tombol bersudut membulat dengan efek hover dan pressed.
 * Style PRIMARY (biru) untuk aksi utama, SECONDARY (putih) untuk aksi pendukung seperti Reset.
 */
public class PrimaryButton extends JButton {

    public enum Style { PRIMARY, SECONDARY }

    private final Style style;
    private boolean hovered;
    private boolean pressed;

    public PrimaryButton(String text) {
        this(text, Style.PRIMARY);
    }

    public PrimaryButton(String text, Style style) {
        super(text);
        this.style = style;

        setFont(AppTheme.FONT_BODY_BOLD);
        setForeground(style == Style.PRIMARY ? AppTheme.TEXT_ON_PRIMARY : AppTheme.TEXT_PRIMARY);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                pressed = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                repaint();
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = AppTheme.RADIUS;

        g2.setColor(resolveFill());
        g2.fillRoundRect(0, 0, w, h, arc, arc);

        if (style == Style.SECONDARY) {
            g2.setColor(AppTheme.BORDER);
            g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
        }

        // Cincin fokus untuk pengguna keyboard
        if (isFocusOwner()) {
            g2.setColor(AppTheme.PRIMARY_RING);
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(1, 1, w - 3, h - 3, arc, arc);
        }

        g2.dispose();
        super.paintComponent(g); // menggambar teks
    }

    private Color resolveFill() {
        if (!isEnabled()) {
            return style == Style.PRIMARY ? AppTheme.DISABLED : AppTheme.SURFACE_ALT;
        }
        if (style == Style.PRIMARY) {
            if (pressed) return AppTheme.PRIMARY_PRESSED;
            if (hovered) return AppTheme.PRIMARY_HOVER;
            return AppTheme.PRIMARY;
        }
        if (pressed) return AppTheme.DIVIDER;
        if (hovered) return AppTheme.SURFACE_ALT;
        return AppTheme.SURFACE;
    }
}