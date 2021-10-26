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

@SuppressWarnings("serial")
public class App extends JFrame {
    public QuestionCanvas soruPanel;
    ControlPanel controlPanel;

    public App() {
        setTitle("\u0130\u015flem Tablosu");
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        // setMinimumSize(new Dimension(900,600));
        controlPanel = new ControlPanel(this);
        soruPanel = new QuestionCanvas(this);

        getContentPane().add(soruPanel, BorderLayout.CENTER);
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
