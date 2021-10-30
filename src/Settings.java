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
import javax.swing.JOptionPane;
/*
 * Created on 29.10.2021
 *
 * @author Tolga Işık
 */

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
        td = new JButton("Tablo Çerçeve Rengi");
        tc = new JButton("Tablo Çizgi Rengi");
        tb = new JButton("Tablo İç Rengi");
        ap = new JButton("Arkaplan Rengi");
        ii = new JButton("İç İpucu Rengi");
        di = new JButton("Dış İpucu Rengi");
        da = new JButton("Dış ipucu Arkaplan Rengi");
        yt = new JButton("Yazı Tipi");
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
        add(yt);
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

                bw.write(soru.canvas.arkaPlan.getRGB() + "");
                bw.newLine();
                bw.write(soru.canvas.cerceve.getRGB() + "");
                bw.newLine();
                bw.write(soru.canvas.tabloIc.getRGB() + "");
                bw.newLine();
                bw.write(soru.canvas.tablo.getRGB() + "");
                bw.newLine();
                bw.write(soru.canvas.cevap.getRGB() + "");
                bw.newLine();
                bw.write(soru.canvas.disipucu.getRGB() + "");
                bw.newLine();
                bw.write(soru.canvas.disarka.getRGB() + "");
                bw.newLine();
                bw.write(soru.canvas.icipucu.getRGB() + "");
                bw.newLine();
                bw.write(soru.canvas.font.getFamily());
                bw.newLine();
                bw.write(soru.canvas.font.getStyle() + "");
                bw.newLine();
                bw.write(soru.canvas.font.getSize() + "");
                bw.newLine();
                bw.write(soru.canvas.sorubasligi);
                bw.newLine();
                bw.write(soru.canvas.cevapbasligi);
                bw.newLine();
                bw.write(soru.getX() + "");
                bw.newLine();
                bw.write(soru.getY() + "");
                bw.newLine();
                bw.write(soru.getWidth() + "");
                bw.newLine();
                bw.write(soru.getHeight() + "");
                bw.newLine();
                bw.write(soru.canvas.game.size + "");
                bw.newLine();
                bw.write(soru.canvas.game.size + "");
                bw.newLine();
                bw.close();
                soru.canvas.kayitli = true;
                soru.canvas.duzen = dosya;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(ayarlar, "Renk d\u00fczeni kaydedilemedi", "Hata",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (event.getSource() == sorubaslik) {
            String newbaslik = JOptionPane.showInputDialog(ayarlar,
                    "Soru Ba\u015fl\u0131\u011f\u0131n\u0131 Se\u00e7iniz", soru.canvas.sorubasligi);
            if (newbaslik == null)
                return;
            soru.canvas.sorubasligi = newbaslik;
            soru.canvas.kayitli = false;
        } else if (event.getSource() == cevapbaslik) {
            String newbaslik = JOptionPane.showInputDialog(ayarlar,
                    "Cevap Ba\u015fl\u0131\u011f\u0131n\u0131 Se\u00e7iniz", soru.canvas.cevapbasligi);
            if (newbaslik == null)
                return;
            soru.canvas.cevapbasligi = newbaslik;
            soru.canvas.kayitli = false;
        } else if (event.getSource() == cz) {
            Color c = JColorChooser.showDialog(null, "\u00c7i\u00e7ek Rengini Se\u00e7iniz", soru.canvas.cevap);
            if (c != null) {
                soru.canvas.cevap = c;
                soru.canvas.kayitli = false;
            }
        } else if (event.getSource() == yt) {
            int result = fontChooser.showDialog(soru.canvas.font);

            if (result != JFontChooser.CANCEL_OPTION) {
                soru.canvas.font = fontChooser.getFont();
                soru.canvas.setFont(soru.canvas.font);
                soru.canvas.kayitli = false;
            }
        } else if (event.getSource() == ii) {
            Color c = JColorChooser.showDialog(null, "\u0130\u00e7 \u0130pucu Rengini Se\u00e7iniz",
                    soru.canvas.icipucu);
            if (c != null) {
                soru.canvas.icipucu = c;
                soru.canvas.kayitli = false;
            }
        } else if (event.getSource() == di) {
            Color c = JColorChooser.showDialog(null, "D\u0131\u015f \u0130pucu Rengini Se\u00e7iniz",
                    soru.canvas.disipucu);
            if (c != null) {
                soru.canvas.disipucu = c;
                soru.canvas.kayitli = false;
            }
        } else if (event.getSource() == da) {
            Color c = JColorChooser.showDialog(null, "D\u0131\u015f \u0130pucu Arkaplan Rengini Se\u00e7iniz",
                    soru.canvas.disarka);
            if (c != null) {
                soru.canvas.disarka = c;
                soru.canvas.kayitli = false;
            }
        } else if (event.getSource() == tc) {
            Color c = JColorChooser.showDialog(null, "Tablo \u00c7izgi Rengini Se\u00e7iniz", soru.canvas.tablo);
            if (c != null) {
                soru.canvas.tablo = c;
                soru.canvas.kayitli = false;
            }
        } else if (event.getSource() == tb) {
            Color c = JColorChooser.showDialog(null, "Tablo \u0130\u00e7i Rengini Se\u00e7iniz", soru.canvas.tabloIc);
            if (c != null) {
                soru.canvas.tabloIc = c;
                soru.canvas.kayitli = false;
            }
        } else if (event.getSource() == hints) {
            Color c = JColorChooser.showDialog(null, "Tablo \u00c7er\u00e7eve Rengini Se\u00e7iniz",
                    soru.canvas.cerceve);
            if (c != null) {
                soru.canvas.hints = c;
            }
        } else if (event.getSource() == td) {
            Color c = JColorChooser.showDialog(null, "Tablo \u00c7er\u00e7eve Rengini Se\u00e7iniz",
                    soru.canvas.cerceve);
            if (c != null) {
                soru.canvas.cerceve = c;
                soru.canvas.kayitli = false;
            }
        } else if (event.getSource() == ap) {
            Color c = JColorChooser.showDialog(null, "Arkaplan Rengini Se\u00e7iniz", soru.canvas.getBackground());
            if (c != null) {
                soru.canvas.arkaPlan = c;
                soru.canvas.setBackground(soru.canvas.arkaPlan);
                soru.getContentPane().setBackground(soru.canvas.arkaPlan);
                soru.controlPanel.buttonPanel.setBackground(soru.canvas.arkaPlan);
                soru.canvas.kayitli = false;
            }
        }
        soru.repaint();
    }
}
