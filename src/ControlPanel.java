/*
 * Created on 10.Nis.2007
 */

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;
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
        createButtonPanel();

    }

    void createButtonPanel() {

        buttonPanel = new JPanel();

        rows = new JComboBox<String>(rowColOptions);
        rows.setSelectedIndex(0);

        columns = new JComboBox<String>(rowColOptions);
        columns.setSelectedIndex(0);

        clu = new JComboBox<String>(clueOptions);
        clu.setSelectedIndex(0);

        shouldIncludeClues = new JRadioButton();

        createNewGameButton();

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
        buttonPanel.add(new JLabel("Satır : "));
        buttonPanel.add(rows);
        buttonPanel.add(new JLabel("Sutün : "));
        buttonPanel.add(columns);
        buttonPanel.add(new JLabel("İpucu Sayısı : "));
        buttonPanel.add(clu);
        buttonPanel.add(new JLabel("İpucu : "));
        buttonPanel.add(shouldIncludeClues);
        buttonPanel.add(create);
        buttonPanel.add(copylist);
        buttonPanel.add(copy);
        buttonPanel.add(exit);
        buttonPanel.add(mark);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    void createNewGameButton() {
        create = new JButton("Yeni");
        create.addActionListener((ActionEvent e) -> {
            if (thread != null && thread.isAlive()) {
                slayer.kill();
                busy = false;
                application.canvas.game = null;
                application.canvas.repaint();
                return;
            }
            thread = new Thread(() -> {
                busy = true;
                create.setText("İptal");
                slayer.shouldKill = false;
                application.canvas.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                application.canvas.game = new Game(rows.getSelectedIndex() + 3, columns.getSelectedIndex() + 3,
                        shouldIncludeClues.isSelected(), clu.getSelectedIndex() + 1, slayer);
                create.setText("Yeni");
                application.canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                busy = false;
                application.canvas.repaint();
            });
            thread.start();
        });
    }

    void prepareDescription() {
        questionDescription.setVisible(true);
        questionDescription.setEditable(true);
        questionDescription.setBackground(java.awt.Color.WHITE);
        questionDescription.setLineWrap(true);
        questionDescription.setWrapStyleWord(true);
        add(questionDescription, BorderLayout.CENTER);
    }

    public void copyQuestionCanvasToClipboard() {
        int copymode = copylist.getSelectedIndex();

        if (copymode == 1) {

        } else if (copymode == 2) {
        } else if (copymode == 3) {
        }
        Toolkit.getDefaultToolkit().getSystemClipboard();

    }

    void showQuestionDetail() {
        info = new Info(1);
        info.init();
    }
}