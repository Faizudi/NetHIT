package com.nethit.ui.panels;

import com.nethit.ui.components.ResultRow;

public class AboutPanel extends PagePanel {

    private static final String APP_VERSION = "1.0.0 (dalam pengembangan)";

    public AboutPanel() {
        super("Tentang netHIT", "Informasi singkat tentang aplikasi ini.");

        Card intro = addCard();
        intro.add(createHeading("Apa itu netHIT?"));
        intro.add(createParagraph(
                "netHIT (Network Hitung) adalah kalkulator jaringan yang membantu menghitung "
                + "IP Address dan subnetting dengan mudah. Aplikasi ini dibuat untuk mahasiswa, "
                + "pelajar, dan siapa saja yang sedang mempelajari dasar-dasar jaringan komputer."));

        Card features = addCard();
        features.add(createHeading("Fitur"));
        features.add(createParagraph(
                "&bull; <b>IP Address Calculator</b>: informasi biner, kelas, dan tipe sebuah alamat IPv4.<br><br>"
                + "&bull; <b>Subnet Calculator</b>: subnet mask, network, broadcast, rentang IP, dan jumlah host.<br><br>"
                + "&bull; <b>CIDR Calculator</b>: memahami notasi prefix dan membagi blok menjadi beberapa subnet.<br><br>"
                + "Semua perhitungan memakai operasi bitwise, bukan hasil statis."));

        Card info = addCard();
        info.add(createHeading("Informasi"));
        info.add(new ResultRow("Versi", APP_VERSION));
        info.add(new ResultRow("Bahasa", "Java " + System.getProperty("java.version")));
        info.add(new ResultRow("Antarmuka", "Java Swing"));
        info.add(new ResultRow("Library eksternal", "Tidak ada"));
    }
}