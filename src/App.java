import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * @author Tolga Işık
 */

public class App extends JFrame {
    QuestionCanvas canvas;
    ControlPanel controlPanel;
    QuestionInputPanel inputPanel;
    public App() {
        setTitle("Labirent");
        setPreferredSize(new Dimension(1200, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        controlPanel = new ControlPanel(this);
        canvas = new QuestionCanvas(this);
        inputPanel = new QuestionInputPanel(this);
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        setVisible(true);
        pack();
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
