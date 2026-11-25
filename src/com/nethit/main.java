package com.nethit;

import javax.swing.SwingUtilities;

import com.nethit.ui.MainFrame;
import com.nethit.ui.theme.AppTheme;

/**
 * Titik masuk aplikasi netHIT (Network Hitung).
 */
public class Main {

    public static void main(String[] args) {
        // Semua pembuatan komponen Swing harus berjalan di Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            AppTheme.install();
            new MainFrame().setVisible(true);
        });
    }
}