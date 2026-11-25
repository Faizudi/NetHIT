package com.nethit.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.nethit.ui.components.InputField;
import com.nethit.ui.components.PrimaryButton;
import com.nethit.ui.components.ResultRow;
import com.nethit.ui.components.PrimaryButton.Style;
import com.nethit.ui.theme.AppTheme;

public class ComponentDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ComponentDemo::showWindow);
    }

    private static void showWindow() {
        AppTheme.install();

        JFrame frame = new JFrame("netHIT - Demo Komponen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(
                AppTheme.SPACE_XXL, AppTheme.SPACE_XXL, AppTheme.SPACE_XXL, AppTheme.SPACE_XXL));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(AppTheme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.DIVIDER),
                BorderFactory.createEmptyBorder(AppTheme.SPACE_XL, AppTheme.SPACE_XL,
                        AppTheme.SPACE_XL, AppTheme.SPACE_XL)));

        JLabel title = new JLabel("Demo Komponen netHIT");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Nilai hasil di bawah hanya contoh statis, bukan hasil perhitungan.");
        subtitle.setFont(AppTheme.FONT_BODY);
        subtitle.setForeground(AppTheme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        InputField ipField = new InputField("Alamat IPv4", "contoh: 192.168.1.10");
        ipField.setHint("Masukkan 4 angka (0-255) dipisah titik.");
        InputField prefixField = new InputField("Prefix CIDR", "contoh: 24");

        PrimaryButton calcButton = new PrimaryButton("Hitung");
        PrimaryButton resetButton = new PrimaryButton("Reset", PrimaryButton.Style.SECONDARY);
        PrimaryButton errorButton = new PrimaryButton("Simulasi Error", PrimaryButton.Style.SECONDARY);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttons.setOpaque(false);
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.add(calcButton);
        buttons.add(Box.createHorizontalStrut(AppTheme.SPACE_SM));
        buttons.add(resetButton);
        buttons.add(Box.createHorizontalStrut(AppTheme.SPACE_SM));
        buttons.add(errorButton);
        buttons.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttons.getPreferredSize().height));

        JLabel resultHeading = new JLabel("HASIL");
        resultHeading.setFont(AppTheme.FONT_LABEL);
        resultHeading.setForeground(AppTheme.TEXT_SECONDARY);
        resultHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        ResultRow networkRow = new ResultRow("Network Address");
        networkRow.setDescription("Alamat pertama sebuah jaringan, mengidentifikasi jaringan itu sendiri.");
        ResultRow broadcastRow = new ResultRow("Broadcast Address");
        broadcastRow.setDescription("Alamat terakhir sebuah jaringan, dipakai untuk mengirim ke semua perangkat.");
        ResultRow hostRow = new ResultRow("Jumlah Host");

        calcButton.addActionListener(e -> {
            ipField.clearError();
            if (ipField.getText().isEmpty()) {
                ipField.showError("Alamat IP tidak boleh kosong.");
                return;
            }
            networkRow.setValue("192.168.1.0");
            broadcastRow.setValue("192.168.1.255");
            hostRow.setValue("254");
        });
        ipField.addActionListener(e -> calcButton.doClick()); // Enter = Hitung

        resetButton.addActionListener(e -> {
            ipField.clear();
            prefixField.clear();
            networkRow.clear();
            broadcastRow.clear();
            hostRow.clear();
            ipField.focusField();
        });

        errorButton.addActionListener(e ->
                prefixField.showError("Prefix harus berupa angka 0 sampai 32."));

        card.add(title);
        card.add(Box.createVerticalStrut(AppTheme.SPACE_XS));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(AppTheme.SPACE_XL));
        card.add(ipField);
        card.add(Box.createVerticalStrut(AppTheme.SPACE_SM));
        card.add(prefixField);
        card.add(Box.createVerticalStrut(AppTheme.SPACE_MD));
        card.add(buttons);
        card.add(Box.createVerticalStrut(AppTheme.SPACE_XL));
        card.add(resultHeading);
        card.add(Box.createVerticalStrut(AppTheme.SPACE_SM));
        card.add(networkRow);
        card.add(broadcastRow);
        card.add(hostRow);

        root.add(card, BorderLayout.NORTH);

        frame.setContentPane(root);
        frame.setSize(820, 780);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}   