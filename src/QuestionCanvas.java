/*
 * Created on 25.10.2021
 */

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Tolga Işık
 */

public class QuestionCanvas extends JPanel {
    String duzen = "", sorubasligi = "", cevapbasligi = "";
    boolean kayitli = true;
    Color tablo = Color.DARK_GRAY, tabloIc = Color.WHITE, cerceve = Color.BLACK, disipucu = Color.WHITE,
            disarka = Color.WHITE, cevap = Color.BLACK, arkaPlan = Color.WHITE, icipucu = Color.BLACK,
            hints = Color.BLACK;
    int cicekYuzde = 80;
    int hucreBoyu = 0; // rakamlarin geldigi hucrelerin en ve boyu
    Font font = null;
    boolean print = false; // yazdiriliyor mu
    int printWidth = 0, printHeight = 0; // kagit boyutlari
    int printX = 0, printY = 0; // kagitin sol ve ust bosluklari
    App labirent;

    public Game soru;

    public QuestionCanvas(App __labirent) {
        this.labirent = __labirent;
        try {
            BufferedReader br = new BufferedReader(new FileReader("vs.dat"));
            duzen = br.readLine();
            duzenAl(duzen);
            br.close();
        } catch (IOException e) {
        }
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    void duzenAl(String dosya) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("ayarlar/" + dosya));
            arkaPlan = new Color(Integer.parseInt(br.readLine()));
            setBackground(arkaPlan);
            labirent.getContentPane().setBackground(arkaPlan);
            cerceve = new Color(Integer.parseInt(br.readLine()));
            tabloIc = new Color(Integer.parseInt(br.readLine()));
            tablo = new Color(Integer.parseInt(br.readLine()));
            cevap = new Color(Integer.parseInt(br.readLine()));
            disipucu = new Color(Integer.parseInt(br.readLine()));
            disarka = new Color(Integer.parseInt(br.readLine()));
            icipucu = new Color(Integer.parseInt(br.readLine()));
            font = new Font(br.readLine(), Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));
            sorubasligi = br.readLine();
            cevapbasligi = br.readLine();
            labirent.setBounds(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()),
                    Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));
            int tsize = Integer.parseInt(br.readLine());
            int tlights = Integer.parseInt(br.readLine());
            labirent.controlPanel.boyut.setSelectedIndex(tsize - 5);
            br.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(labirent, "Renk d\u00fczeni al\u0131namad\u0131", "Hata",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            kayitli = false;
        }
        setBackground(arkaPlan);
        repaint();
    }
}