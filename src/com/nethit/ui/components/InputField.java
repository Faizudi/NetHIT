package com.nethit.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nethit.ui.theme.AppTheme;

public class InputField extends JPanel {

    private final JLabel titleLabel;
    private final PlaceholderTextField field;
    private final JLabel messageLabel;

    private String hint = "";
    private boolean hasError = false;
    private boolean focused = false;

    public InputField(String label, String placeholder) {
        super(new BorderLayout(0, AppTheme.SPACE_XS));
        setOpaque(false);
        setAlignmentX(LEFT_ALIGNMENT);

        titleLabel = new JLabel(label);
        titleLabel.setFont(AppTheme.FONT_LABEL);
        titleLabel.setForeground(AppTheme.TEXT_PRIMARY);

        field = new PlaceholderTextField(placeholder);
        field.setFont(AppTheme.FONT_BODY);
        field.setForeground(AppTheme.TEXT_PRIMARY);
        field.setCaretColor(AppTheme.PRIMARY);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                focused = true;
                refreshAppearance();
            }

            @Override
            public void focusLost(FocusEvent e) {
                focused = false;
                refreshAppearance();
            }
        });

        // Teks spasi dipakai agar tinggi baris pesan tetap, sehingga layout tidak "melompat"
        messageLabel = new JLabel(" ");
        messageLabel.setFont(AppTheme.FONT_SMALL);

        add(titleLabel, BorderLayout.NORTH);
        add(field, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        refreshAppearance();
    }

    public String getText() {
        return field.getText().trim();
    }

    public void setText(String text) {
        field.setText(text);
    }

    public void clear() {
        field.setText("");
        clearError();
    }

    public void setHint(String hint) {
        this.hint = hint == null ? "" : hint;
        refreshAppearance();
    }

    public void showError(String message) {
        hasError = true;
        messageLabel.setText(message);
        refreshAppearance();
    }

    public void clearError() {
        hasError = false;
        refreshAppearance();
    }

    public boolean hasError() {
        return hasError;
    }

    public void addActionListener(ActionListener listener) {
        field.addActionListener(listener);
    }

    public void addTextChangeListener(Runnable listener) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                listener.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                listener.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                listener.run();
            }
        });
    }

    public void focusField() {
        field.requestFocusInWindow();
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
    }

    private void refreshAppearance() {
        Color line = hasError ? AppTheme.ERROR : (focused ? AppTheme.PRIMARY : AppTheme.BORDER);
        int thickness = (hasError || focused) ? 2 : 1;
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(line, thickness),
                BorderFactory.createEmptyBorder(10 - thickness, 14 - thickness, 10 - thickness, 14 - thickness)));

        if (hasError) {
            messageLabel.setForeground(AppTheme.ERROR);
        } else {
            messageLabel.setForeground(AppTheme.TEXT_SECONDARY);
            messageLabel.setText(hint.isEmpty() ? " " : hint);
        }
    }

    private static class PlaceholderTextField extends JTextField {

        private final String placeholder;

        PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && placeholder != null && !placeholder.isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(AppTheme.TEXT_MUTED);
                g2.setFont(getFont());
                Insets in = getInsets();
                FontMetrics fm = g2.getFontMetrics();
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(placeholder, in.left, y);
                g2.dispose();
            }
        }
    }
}