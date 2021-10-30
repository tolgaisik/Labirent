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
    App application;
    Image image = null;

    public Game game;

    public QuestionCanvas(App __labirent) {
        this.application = __labirent;
        try {
            image = Toolkit.getDefaultToolkit().createImage("827.gif");
            BufferedReader br = new BufferedReader(new FileReader("vs.dat"));
            duzen = br.readLine();
            duzenAl(duzen);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graph = (Graphics2D) g;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setColor(arkaPlan);
        graph.fillRect(0, 0, getWidth(), getHeight());
        try {

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    void drawLoadingIcon(Graphics2D g) {
        if (image != null) {
            g.clearRect(0, 0, getWidth(), getHeight());
            int x = (this.getWidth() - image.getWidth(null)) / 2;
            int y = (this.getHeight() - image.getHeight(null)) / 2;
            g.drawImage(image, x, y, this);
        }
    }

    void duzenAl(String dosya) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("ayarlar/" + dosya));
            arkaPlan = new Color(Integer.parseInt(br.readLine()));
            setBackground(arkaPlan);
            application.getContentPane().setBackground(arkaPlan);
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
            application.setBounds(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()),
                    Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));
            int tsize = Integer.parseInt(br.readLine());
            int tlights = Integer.parseInt(br.readLine());
            br.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(application, "Renk d\u00fczeni al\u0131namad\u0131", "Hata",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            kayitli = false;
        }
        setBackground(arkaPlan);
        repaint();
    }
}