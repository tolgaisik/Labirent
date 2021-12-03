import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.lang.Runnable;
public class QuestionInputPanel extends JPanel {
    JTextField questionInput;
    StringBuilder questionInputString = new StringBuilder();
    Thread thread;
    App application;
    JComboBox<String> rows, cols;
    JButton create;
    String[] row_string_list = { "3", "4", "5", "6" };
    String[] col_string_list = { "3", "4", "5", "6" };
    QuestionInputPanel(App _application) {
        application = _application;
        questionInput = new JTextField(100);
        questionInput.addActionListener((ActionEvent e) -> {
            
        });
        questionInput.setColumns(25);
        rows = new JComboBox<String>(row_string_list);
        cols = new JComboBox<String>(col_string_list);
        create = new JButton("Yeni Soru");
        create.addActionListener((ActionEvent e) -> {
            generateGameFromInput();
        });
        add(questionInput);
        add(rows);
        add(cols);
        add(create);
    }
    void generateGameFromInput() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                application.canvas.game = null;
                try {
                    application.canvas.game = new Game(questionInput.getText(), rows.getSelectedIndex()+3, cols.getSelectedIndex()+3);
                    application.canvas.repaint();
                } catch (Game.InputStringTypeIsNotValid e) {
                    JOptionPane.showMessageDialog(application, e.getMessage());                    
                }

            }
        });
        thread.start();
    }
 }
