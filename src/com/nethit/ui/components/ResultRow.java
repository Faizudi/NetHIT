package com.nethit.ui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nethit.ui.theme.AppTheme;

public class ResultRow extends JPanel {

    private static final String EMPTY_VALUE = "-";
    private static final int NAME_WIDTH = 190;

    private final JLabel nameLabel;
    private final JLabel valueLabel;

    public ResultRow(String name) {
        this(name, EMPTY_VALUE);
    }

    public ResultRow(String name, String value) {
        super(new BorderLayout(AppTheme.SPACE_MD, 0));
        setOpaque(false);
        setAlignmentX(LEFT_ALIGNMENT);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.DIVIDER),
                BorderFactory.createEmptyBorder(AppTheme.SPACE_MD, AppTheme.SPACE_XS,
                        AppTheme.SPACE_MD, AppTheme.SPACE_XS)));

        nameLabel = new JLabel(name);
        nameLabel.setFont(AppTheme.FONT_BODY);
        nameLabel.setForeground(AppTheme.TEXT_SECONDARY);
        nameLabel.setPreferredSize(new Dimension(NAME_WIDTH, nameLabel.getPreferredSize().height));

        valueLabel = new JLabel(value);
        valueLabel.setFont(AppTheme.FONT_MONO);
        valueLabel.setForeground(AppTheme.TEXT_PRIMARY);

        add(nameLabel, BorderLayout.WEST);
        add(valueLabel, BorderLayout.CENTER);
    }

    public void setValue(String value) {
        valueLabel.setText(value);
    }

    public String getValue() {
        return valueLabel.getText();
    }

    public void clear() {
        valueLabel.setText(EMPTY_VALUE);
    }

    public void setDescription(String description) {
        nameLabel.setToolTipText(description);
        valueLabel.setToolTipText(description);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
    }
}