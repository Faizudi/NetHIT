package com.nethit;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Main {

    private static final String APP_TITLE = "NetHIT - Network Hitung";
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 650;
    private static final int MIN_WIDTH = 900;
    private static final int MIN_HEIGHT = 600;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowWindow);
    }

    private static void createAndShowWindow() {
        JFrame frame = new JFrame(APP_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);

        JLabel placeholder = new JLabel("NetHIT siap digunakan", SwingConstants.CENTER);
        placeholder.setFont(placeholder.getFont().deriveFont(24f));
        frame.add(placeholder);

        frame.setVisible(true);
    }
}