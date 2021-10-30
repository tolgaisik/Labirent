import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * @author Tolga Işık
 * @param <App>
 */

public class ControlPanel extends JPanel {
    JButton settings, copy, create, exit, mark;
    JTextArea questionDescription;
    JComboBox<String> rows, columns, copylist, clu;
    JRadioButton shouldIncludeClues;
    App application;
    JPanel buttonPanel;
    boolean busy = false;
    private Thread thread = null;
    private String[] copyOptions = { "Yan Yana", "Alt Alta", "Soru", "Cevap" };
    private String[] rowColOptions = { "3", "4", "5", "6" };
    private String[] clueOptions = { "1", "2", "3" };
    Info info = null;
    ThreadSlayer slayer = new ThreadSlayer();

    static class ThreadSlayer {
        Boolean shouldKill = false;

        void kill() {
            shouldKill = true;
        }
    }

    public ControlPanel(App __application) {

        this.application = __application;
        setLayout(new BorderLayout());
        createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

    }

    void calculateLast() {
        last = Integer.parseInt(rows.getSelectedItem().toString())
                * Integer.parseInt(columns.getSelectedItem().toString());
        questionDescription.setText("Labirentin 1. kutusundan başlayıp " + last
                + ". kutusunda bitireceğiniz ve her kutuda tam olarak bir defa bulunacağınız bir yol oluşturacaksınız. Bulunduğunuz kutudaki okun işaret yönünde bulunan herhangi bir kutuya gidebilirsiniz. İlerleme sıranıza göre kutuları numaralandırınız.");
    }

    void createButtonPanel() {

        buttonPanel = new JPanel();

        rows = new JComboBox<String>(rowColOptions);
        rows.setSelectedIndex(0);
        rows.addActionListener((ActionEvent e) -> {
            calculateLast();
        });

        columns = new JComboBox<String>(rowColOptions);
        columns.setSelectedIndex(0);
        columns.addActionListener((ActionEvent e) -> {
            calculateLast();
        });
        clu = new JComboBox<String>(clueOptions);
        clu.setSelectedIndex(0);

        shouldIncludeClues = new JRadioButton();

        create = new JButton("Yeni Soru");
        create.addActionListener((ActionEvent e) -> {
            NewGame();
        });

        settings = new JButton("Ayarlar");
        settings.addActionListener((ActionEvent e) -> {
            new Settings(application);
        });

        copylist = new JComboBox<String>(copyOptions);

        copy = new JButton("Kopyala");
        copy.addActionListener((ActionEvent e) -> {
            copyQuestionCanvasToClipboard();
        });

        exit = new JButton("Çık");
        exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        mark = new JButton("?");
        mark.addActionListener((ActionEvent e) -> {
            showQuestionDetail();
        });
        prepareDescription();
        buttonPanel.add(new JLabel("Satır : "));
        buttonPanel.add(rows);
        buttonPanel.add(new JLabel("Sutün : "));
        buttonPanel.add(columns);
        buttonPanel.add(new JLabel("İpucu Sayısı : "));
        buttonPanel.add(clu);
        buttonPanel.add(new JLabel("İpucu : "));
        buttonPanel.add(shouldIncludeClues);
        buttonPanel.add(create);
        buttonPanel.add(settings);
        buttonPanel.add(copylist);
        buttonPanel.add(copy);
        buttonPanel.add(exit);
        buttonPanel.add(mark);

    }

    void NewGame() {
        application.canvas.game = null;
        application.canvas.repaint();
        if (thread != null && thread.isAlive()) {
            slayer.kill();
            return;
        }
        thread = new Thread(() -> {
            busy = true;
            create.setText("İptal");
            application.canvas.repaint();
            slayer.shouldKill = false;
            application.canvas.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            application.canvas.game = new Game(rows.getSelectedIndex() + 3, columns.getSelectedIndex() + 3,
                    shouldIncludeClues.isSelected(), clu.getSelectedIndex() + 1, slayer);
            create.setText("Yeni Soru");
            application.canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            busy = false;
            application.canvas.repaint();
        });
        thread.start();
    }

    int last = 9;

    void prepareDescription() {
        questionDescription = new JTextArea();
        questionDescription.setVisible(true);
        questionDescription.setEditable(true);
        questionDescription.setBackground(java.awt.Color.WHITE);
        questionDescription.setLineWrap(true);
        questionDescription.setWrapStyleWord(true);

        questionDescription.setText("Labirentin 1. kutusundan başlayıp " + last
                + " kutusunda bitireceğiniz ve her kutuda tam olarak bir defa bulunacağınız bir yol oluşturacaksınız. Bulunduğunuz kutudaki okun işaret yönünde bulunan herhangi bir kutuya gidebilirsiniz. İlerleme sıranıza göre kutuları numaralandırınız.");
        add(questionDescription, BorderLayout.CENTER);
    }

    public void copyQuestionCanvasToClipboard() {
        int copymode = copylist.getSelectedIndex();
        Point topLeft = application.canvas.getCanvas();
        int width = application.canvas.width();
        Point rect = application.canvas.gameRect();
        int height = application.canvas.height();
        BufferedImage image = new BufferedImage(width + 2, height + 2, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.translate(-topLeft.x, -topLeft.y);
        application.canvas.paintComponent(g);
        BufferedImage question = image.getSubimage(0, 0, application.canvas.gameRect().x + 2, image.getHeight());
        BufferedImage answer = image.getSubimage(image.getWidth() - rect.x - 2, 0, rect.x + 2, image.getHeight());

        if (copymode == 1) {
            BufferedImage vertical = new BufferedImage(application.canvas.gameRect().x + 2,
                    (height << 1) + application.canvas.squareSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = vertical.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, vertical.getWidth(), vertical.getHeight());
            g2.drawImage(question, 0, 0, null);
            g2.translate(0, height + application.canvas.squareSize - 2);
            g2.drawImage(answer, 0, 0, null);
            image = vertical;
        } else if (copymode == 2) {
            image = question;
        } else if (copymode == 3) {
            image = answer;
        }
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new ImageSelection(image), null);
    }

    void showQuestionDetail() {
        info = new Info(0);
        info.init();
    }
}