import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
/*
 * Created on 10.Nis.2007
 */
import javax.swing.JOptionPane;

/**
 * @author Fatih Keles
 */

@SuppressWarnings("serial")
public class Settings extends JDialog implements ActionListener {
    App soru;
    JButton td, tc, tb, ap, di, cz, yt, ii, kd, al, da, sorubaslik, cevapbaslik, hints;
    JFontChooser fontChooser;
    final Settings ayarlar = this;

    public Settings(App soru) {
        super(soru, "Ayarlar", false);
        fontChooser = new JFontChooser(soru);
        this.soru = soru;
        int w = 170, h = 300;
        setBounds(soru.getX() + (soru.getWidth() - w) / 2, soru.getY() + (soru.getHeight() - h) / 2, w, h);
        setLayout(new GridLayout(0, 1));
        td = new JButton("Tablo \u00c7er\u00e7eve Rengi");
        tc = new JButton("Tablo \u00c7izgi Rengi");
        tb = new JButton("Tablo \u0130\u00e7i Rengi");
        ap = new JButton("Arkaplan Rengi");
        ii = new JButton("\u0130\u00e7 \u0130pucu Rengi");
        di = new JButton("D\u0131\u015f \u0130pucu Rengi");
        da = new JButton("D\u0131\u015f \u0130pucu Arkaplan Rengi");
        yt = new JButton("Yaz\u0131 Tipi");
        cz = new JButton("\u00c7i\u00e7ek Rengi");
        kd = new JButton("Kaydet");
        al = new JButton("Al");
        hints = new JButton("Aritmetik Sonu\u00e7 Rengi");
        sorubaslik = new JButton("Soru Ba\u015fl\u0131\u011f\u0131");
        cevapbaslik = new JButton("Cevap Ba\u015fl\u0131\u011f\u0131");
        td.addActionListener(this);
        da.addActionListener(this);
        tc.addActionListener(this);
        tb.addActionListener(this);
        ap.addActionListener(this);
        di.addActionListener(this);
        yt.addActionListener(this);
        cz.addActionListener(this);
        ii.addActionListener(this);
        kd.addActionListener(this);
        al.addActionListener(this);
        hints.addActionListener(this);
        sorubaslik.addActionListener(this);
        cevapbaslik.addActionListener(this);
        add(td);
        add(tc);
        add(tb);
        add(ap);
        add(ii);
        add(di);
        add(da);
        add(hints);
        add(cz);
        add(yt);
        add(sorubaslik);
        add(cevapbaslik);
        add(kd);
        add(al);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == al) {
            new Retrive(soru);
        } else if (event.getSource() == kd) {
            String dosya = JOptionPane.showInputDialog(ayarlar, "Renk d\u00fczenine isim veriniz", "Kaydet",
                    JOptionPane.QUESTION_MESSAGE);
            if (dosya == null)
                return;
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("ayarlar/" + dosya));

                bw.write(soru.soruPanel.arkaPlan.getRGB() + "");
                bw.newLine();
                bw.write(soru.soruPanel.cerceve.getRGB() + "");
                bw.newLine();
                bw.write(soru.soruPanel.tabloIc.getRGB() + "");
                bw.newLine();
                bw.write(soru.soruPanel.tablo.getRGB() + "");
                bw.newLine();
                bw.write(soru.soruPanel.cevap.getRGB() + "");
                bw.newLine();
                bw.write(soru.soruPanel.disipucu.getRGB() + "");
                bw.newLine();
                bw.write(soru.soruPanel.disarka.getRGB() + "");
                bw.newLine();
                bw.write(soru.soruPanel.icipucu.getRGB() + "");
                bw.newLine();
                bw.write(soru.soruPanel.font.getFamily());
                bw.newLine();
                bw.write(soru.soruPanel.font.getStyle() + "");
                bw.newLine();
                bw.write(soru.soruPanel.font.getSize() + "");
                bw.newLine();
                bw.write(soru.soruPanel.sorubasligi);
                bw.newLine();
                bw.write(soru.soruPanel.cevapbasligi);
                bw.newLine();
                bw.write(soru.getX() + "");
                bw.newLine();
                bw.write(soru.getY() + "");
                bw.newLine();
                bw.write(soru.getWidth() + "");
                bw.newLine();
                bw.write(soru.getHeight() + "");
                bw.newLine();
                bw.write(soru.soruPanel.soru.size + "");
                bw.newLine();
                bw.write(soru.soruPanel.soru.size + "");
                bw.newLine();
                bw.close();
                soru.soruPanel.kayitli = true;
                soru.soruPanel.duzen = dosya;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(ayarlar, "Renk d\u00fczeni kaydedilemedi", "Hata",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (event.getSource() == sorubaslik) {
            String newbaslik = JOptionPane.showInputDialog(ayarlar,
                    "Soru Ba\u015fl\u0131\u011f\u0131n\u0131 Se\u00e7iniz", soru.soruPanel.sorubasligi);
            if (newbaslik == null)
                return;
            soru.soruPanel.sorubasligi = newbaslik;
            soru.soruPanel.kayitli = false;
        } else if (event.getSource() == cevapbaslik) {
            String newbaslik = JOptionPane.showInputDialog(ayarlar,
                    "Cevap Ba\u015fl\u0131\u011f\u0131n\u0131 Se\u00e7iniz", soru.soruPanel.cevapbasligi);
            if (newbaslik == null)
                return;
            soru.soruPanel.cevapbasligi = newbaslik;
            soru.soruPanel.kayitli = false;
        } else if (event.getSource() == cz) {
            Color c = JColorChooser.showDialog(null, "\u00c7i\u00e7ek Rengini Se\u00e7iniz", soru.soruPanel.cevap);
            if (c != null) {
                soru.soruPanel.cevap = c;
                soru.soruPanel.kayitli = false;
            }
        } else if (event.getSource() == yt) {
            int result = fontChooser.showDialog(soru.soruPanel.font);

            if (result != JFontChooser.CANCEL_OPTION) {
                soru.soruPanel.font = fontChooser.getFont();
                soru.soruPanel.setFont(soru.soruPanel.font);
                soru.soruPanel.kayitli = false;
            }
        } else if (event.getSource() == ii) {
            Color c = JColorChooser.showDialog(null, "\u0130\u00e7 \u0130pucu Rengini Se\u00e7iniz",
                    soru.soruPanel.icipucu);
            if (c != null) {
                soru.soruPanel.icipucu = c;
                soru.soruPanel.kayitli = false;
            }
        } else if (event.getSource() == di) {
            Color c = JColorChooser.showDialog(null, "D\u0131\u015f \u0130pucu Rengini Se\u00e7iniz",
                    soru.soruPanel.disipucu);
            if (c != null) {
                soru.soruPanel.disipucu = c;
                soru.soruPanel.kayitli = false;
            }
        } else if (event.getSource() == da) {
            Color c = JColorChooser.showDialog(null, "D\u0131\u015f \u0130pucu Arkaplan Rengini Se\u00e7iniz",
                    soru.soruPanel.disarka);
            if (c != null) {
                soru.soruPanel.disarka = c;
                soru.soruPanel.kayitli = false;
            }
        } else if (event.getSource() == tc) {
            Color c = JColorChooser.showDialog(null, "Tablo \u00c7izgi Rengini Se\u00e7iniz", soru.soruPanel.tablo);
            if (c != null) {
                soru.soruPanel.tablo = c;
                soru.soruPanel.kayitli = false;
            }
        } else if (event.getSource() == tb) {
            Color c = JColorChooser.showDialog(null, "Tablo \u0130\u00e7i Rengini Se\u00e7iniz",
                    soru.soruPanel.tabloIc);
            if (c != null) {
                soru.soruPanel.tabloIc = c;
                soru.soruPanel.kayitli = false;
            }
        } else if (event.getSource() == hints) {
            Color c = JColorChooser.showDialog(null, "Tablo \u00c7er\u00e7eve Rengini Se\u00e7iniz",
                    soru.soruPanel.cerceve);
            if (c != null) {
                soru.soruPanel.hints = c;
            }
        } else if (event.getSource() == td) {
            Color c = JColorChooser.showDialog(null, "Tablo \u00c7er\u00e7eve Rengini Se\u00e7iniz",
                    soru.soruPanel.cerceve);
            if (c != null) {
                soru.soruPanel.cerceve = c;
                soru.soruPanel.kayitli = false;
            }
        } else if (event.getSource() == ap) {
            Color c = JColorChooser.showDialog(null, "Arkaplan Rengini Se\u00e7iniz", soru.soruPanel.getBackground());
            if (c != null) {
                soru.soruPanel.arkaPlan = c;
                soru.soruPanel.setBackground(soru.soruPanel.arkaPlan);
                soru.getContentPane().setBackground(soru.soruPanel.arkaPlan);
                soru.controlPanel.buttonPanel.setBackground(soru.soruPanel.arkaPlan);
                soru.soruPanel.kayitli = false;
            }
        }
        soru.repaint();
    }
}
