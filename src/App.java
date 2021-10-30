/*
 * Created on 07.May.2007
 */

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * @author Fatih Keles
 */

public class App extends JFrame {
    QuestionCanvas canvas;
    ControlPanel controlPanel;

    public App() {
        setTitle("Labirent");
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        controlPanel = new ControlPanel(this);
        canvas = new QuestionCanvas(this);

        getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
        new App();
    }

    public void setTitle(int i) {
        setTitle(i + "");
    }
}
