/*
 * Created on 10.Nis.2007
 */

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * @author Fatih Keles
 * @param <App>
 */

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener {
    JButton yenile, yazdir, kopyala, cik, ayarlar;
    JTextArea aciklamalar;
    JCheckBox _sum, _mult, _sub;
    JComboBox boyut, copylist;
    private String[] boyutlar = { "4", "5", "6", "7", "8" };
    App labirent;
    JPanel buttonPanel;
    private int n, f;
    boolean busy = false;
    private Thread thread = null;
    private String[] kopyalamaSecenekleri = { "Yan Yana", "Alt Alta", "Soru", "Cevap" };
    private boolean[] operator = { false, false, false };

    public ControlPanel(App labirent) {

        aciklamalar.setVisible(true);
        aciklamalar.setEditable(true);
        aciklamalar.setBackground(java.awt.Color.WHITE);
        aciklamalar.setLineWrap(true);
        aciklamalar.setWrapStyleWord(true);
        aciklamalar.setFont(new Font("Verdana", Font.PLAIN, 12));
        aciklamalar.setPreferredSize(new java.awt.Dimension(200, 60));
        add(aciklamalar, BorderLayout.CENTER);
        buttonPanel = new JPanel();

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == boyut) {
            int i = boyut.getSelectedIndex();
            int count = i + 4;

        } else if (source == yenile) {
            if (busy) {
                return;
            }
            thread = new Thread() {
                public void run() {

                }
            };

            thread.start();
        } else if (source == cik) {
            System.exit(0);
        } else if (source == ayarlar) {
            new Settings(labirent);
        } else if (source == yazdir) {
            PrintJob pj = Toolkit.getDefaultToolkit().getPrintJob(labirent, "", new java.util.Properties());
            if (pj == null)
                return;
            Graphics g = pj.getGraphics();
            g.setFont(new Font("Times New Roman", 1, 18));
            int x = 0, y = 40;
            g.drawString("\u00e7i\u00e7ekler", x + 30, y);
            y += 25;
            g.setFont(new Font("Times New Roman", 0, 12));

            int width = pj.getPageDimension().width - 60;

            java.util.StringTokenizer st = new java.util.StringTokenizer(aciklamalar.getText());
            java.awt.FontMetrics fm = g.getFontMetrics();
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                if ((x + fm.stringWidth(temp) + 30) > (30 + width)) {
                    x = 0;
                    y += 15;
                }
                g.drawString(temp, x + 30, y);
                x += (fm.stringWidth(temp + " "));
            }

            labirent.soruPanel.printWidth = pj.getPageDimension().width - 50;
            labirent.soruPanel.printHeight = pj.getPageDimension().height - 50;
            labirent.soruPanel.printX = 25;
            labirent.soruPanel.printY = y + 30;
            labirent.soruPanel.print = true;
            labirent.soruPanel.paintComponent(g);
            labirent.soruPanel.print = false;
            labirent.soruPanel.printX = 0;
            labirent.soruPanel.printY = 0;
            labirent.soruPanel.printWidth = 0;
            labirent.soruPanel.printHeight = 0;
            pj.end();
        } else if (source == kopyala) {
            kopyala();
        }
        if (_mult.isSelected()) {
            this.operator[0] = true;
        } else {
            this.operator[0] = false;
        }
        if (_sub.isSelected()) {
            this.operator[1] = true;
        } else {
            this.operator[1] = false;
        }
        if (_sum.isSelected()) {
            this.operator[2] = true;
        } else {
            this.operator[2] = false;
        }
    }

    public void kopyala() {
        int copymode = copylist.getSelectedIndex();

        if (copymode == 1) {

        } else if (copymode == 2) {
        } else if (copymode == 3) {
        }
        Toolkit.getDefaultToolkit().getSystemClipboard();

    }
}