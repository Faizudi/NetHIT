package com.nethit.ui.theme;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.UIManager;

public final class AppTheme {

    public static final Color PRIMARY         = new Color(0x2563EB);
    public static final Color PRIMARY_HOVER   = new Color(0x1D4ED8);
    public static final Color PRIMARY_PRESSED = new Color(0x1E40AF);
    public static final Color PRIMARY_RING    = new Color(0x93C5FD);

    public static final Color BACKGROUND      = new Color(0xF8FAFC);
    public static final Color SURFACE         = Color.WHITE;
    public static final Color SURFACE_ALT     = new Color(0xF1F5F9);

    public static final Color SIDEBAR         = new Color(0x0F172A);
    public static final Color SIDEBAR_HOVER   = new Color(0x1E293B);
    public static final Color SIDEBAR_TEXT    = new Color(0xCBD5E1);

    public static final Color TEXT_PRIMARY    = new Color(0x0F172A);
    public static final Color TEXT_SECONDARY  = new Color(0x64748B);
    public static final Color TEXT_MUTED      = new Color(0x94A3B8);
    public static final Color TEXT_ON_PRIMARY = Color.WHITE;

    public static final Color BORDER          = new Color(0xCBD5E1);
    public static final Color DIVIDER         = new Color(0xE2E8F0);
    public static final Color DISABLED        = new Color(0xCBD5E1);

    public static final Color ERROR           = new Color(0xDC2626);
    public static final Color SUCCESS         = new Color(0x16A34A);

    public static final int SPACE_XS = 4;
    public static final int SPACE_SM = 8;
    public static final int SPACE_MD = 12;
    public static final int SPACE_LG = 16;
    public static final int SPACE_XL = 24;
    public static final int SPACE_XXL = 32;

    public static final int RADIUS = 10;

    private static final String UI_FAMILY = pickFamily(Font.SANS_SERIF,
            "Segoe UI", "SF Pro Text", "Helvetica Neue", "Roboto");
    private static final String MONO_FAMILY = pickFamily(Font.MONOSPACED,
            "Consolas", "Menlo", "JetBrains Mono", "DejaVu Sans Mono");

    public static final Font FONT_TITLE     = new Font(UI_FAMILY, Font.BOLD, 24);
    public static final Font FONT_HEADING   = new Font(UI_FAMILY, Font.BOLD, 16);
    public static final Font FONT_BODY      = new Font(UI_FAMILY, Font.PLAIN, 14);
    public static final Font FONT_BODY_BOLD = new Font(UI_FAMILY, Font.BOLD, 14);
    public static final Font FONT_LABEL     = new Font(UI_FAMILY, Font.BOLD, 13);
    public static final Font FONT_SMALL     = new Font(UI_FAMILY, Font.PLAIN, 12);
    public static final Font FONT_MONO      = new Font(MONO_FAMILY, Font.PLAIN, 14);

    private AppTheme() {
        // utility class
    }
    public static void install() {
        UIManager.put("ToolTip.font", FONT_SMALL);
        UIManager.put("OptionPane.messageFont", FONT_BODY);
        UIManager.put("OptionPane.buttonFont", FONT_BODY);
    }

    private static String pickFamily(String fallback, String... candidates) {
        Set<String> available = new HashSet<>(Arrays.asList(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        for (String candidate : candidates) {
            if (available.contains(candidate)) {
                return candidate;
            }
        }
        return fallback;
    }
}