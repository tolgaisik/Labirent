/*
 * Created on 25.10.2021
 */

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Tolga Işık
 */

public class QuestionCanvas extends JPanel {
    String duzen = "";
    boolean kayitli = true;
    Color tablo = Color.DARK_GRAY, tabloIc = Color.blue, cerceve = Color.BLACK, disipucu = Color.WHITE,
            disarka = Color.WHITE, cevap = Color.BLACK, arkaPlan = Color.WHITE, icipucu = Color.BLACK,
            hints = Color.BLACK;
    Font font = null;
    App application;
    Image image = null;
    String errorMessage = null;
    public Game game = null;
    Integer component_width = 0, component_height = 0, squareSize = 0;
    public Boolean hasError = false;
    public QuestionCanvas(App __labirent) {
        super(true);
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
            if (game != null && game.isDone) {
                drawQuestion(graph);
            } else if (application.controlPanel.busy) {
                drawLoadingIcon(graph);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    void drawQuestion(Graphics2D graph) {
        component_width = getWidth();
        component_height = getHeight();
        squareSize = component_width / ((game.col << 1) + 4) > component_height / (game.row)
                ? component_height / game.row
                : component_width / ((game.col << 1) + 4);
        Point center = new Point(component_width >> 1, component_height >> 1);
        Point leftTop = new Point(center.x - squareSize * (game.col + 1), center.y - ((game.row * squareSize) >> 1));
        Point gameRect = new Point(game.col * squareSize, game.row * squareSize);
        graph.setColor(tabloIc); // tablo iç rengi
        graph.fillRect(leftTop.x, leftTop.y, squareSize * game.col, squareSize * game.row);
        graph.fillRect(leftTop.x + gameRect.x + (squareSize << 1), leftTop.y, squareSize * game.col,
                squareSize * game.row);
        graph.setColor(tablo); // çizgi rengi
        graph.drawRect(leftTop.x, leftTop.y, squareSize * game.col, squareSize * game.row);
        graph.drawRect(leftTop.x + gameRect.x + (squareSize << 1), leftTop.y, squareSize * game.col,
                squareSize * game.row);
        graph.setFont(new Font(font.getName(), Font.PLAIN, squareSize >> 2));
        FontMetrics metrics = graph.getFontMetrics();
        // question
        for (int i = 1; i < game.col; i++) {
            graph.drawLine(leftTop.x + squareSize * i, leftTop.y, leftTop.x + squareSize * i, leftTop.y + gameRect.y);
        }
        for (int i = 1; i < game.row; i++) {
            graph.drawLine(leftTop.x, leftTop.y + squareSize * i, leftTop.x + gameRect.x, leftTop.y + squareSize * i);
        }
        for (int i = 0; i < game.row; i++) {
            for (int j = 0; j < game.col; ++j) {
                graph.setColor(disipucu);
                graph.fillOval(leftTop.x + (squareSize >> 2) + squareSize * j,
                        leftTop.y + (squareSize >> 2) + squareSize * i, squareSize >> 1, squareSize >> 1);
                graph.setColor(cerceve);
                graph.drawOval(leftTop.x + (squareSize >> 2) + squareSize * j,
                        leftTop.y + (squareSize >> 2) + squareSize * i, squareSize >> 1, squareSize >> 1);
                graph.setColor(icipucu);
                String clue = game.question[i][j].order == 0 ? "" : Integer.toString(game.question[i][j].order);
                Rectangle2D rect = metrics.getStringBounds(clue, graph);
                graph.drawString(clue, leftTop.x + (squareSize >> 1) + squareSize * j - ((int) rect.getWidth() >> 1),
                        leftTop.y + (squareSize >> 1) + squareSize * i + ((int) (rect.getHeight() * 0.3)));
                graph.setColor(disarka);
                graph.setFont(new Font(font.getName(), Font.BOLD, squareSize >> 2));
                Point xy = drawDirection(graph, leftTop, i, j, squareSize, metrics);
                int direction = game.question[i][j].getDirection();
                graph.drawString(Game.getDirectionSymbol(direction), xy.x + j * squareSize, xy.y + i * squareSize);
                graph.setFont(new Font(font.getName(), Font.PLAIN, squareSize >> 2));
            }
        }
        // answer
        graph.setColor(tablo);
        graph.translate(gameRect.x + (squareSize << 1), 0);
        if(game.hasInput && game.moreThanOne) {
            if(game.answers.size() == 0) {
                JOptionPane.showMessageDialog(application, "Geçerli cevap bulunamadı.");
                return;
            }
            for(int i = 0; i < game.answers.size(); i++) {
                graph.drawString(game.answers.get(i), leftTop.x, leftTop.y);
            }
        }
        for (int i = 1; i < game.col; i++) {
            graph.drawLine(leftTop.x + squareSize * i, leftTop.y, leftTop.x + squareSize * i, leftTop.y + gameRect.y);
        }
        for (int i = 1; i < game.row; i++) {
            graph.drawLine(leftTop.x, leftTop.y + squareSize * i, leftTop.x + gameRect.x, leftTop.y + squareSize * i);
        }
        for (int i = 0; i < game.row; i++) {
            for (int j = 0; j < game.col; ++j) {
                graph.setColor(disipucu);
                graph.fillOval(leftTop.x + (squareSize >> 2) + squareSize * j,
                        leftTop.y + (squareSize >> 2) + squareSize * i, squareSize >> 1, squareSize >> 1);
                graph.setColor(cerceve);
                graph.drawOval(leftTop.x + (squareSize >> 2) + squareSize * j,
                        leftTop.y + (squareSize >> 2) + squareSize * i, squareSize >> 1, squareSize >> 1);
                graph.setColor(icipucu);
                String clue = Integer.toString(game.answer[i][j].order);
                Rectangle2D rect = metrics.getStringBounds(clue, graph);
                graph.drawString(clue, leftTop.x + (squareSize >> 1) + squareSize * j - ((int) rect.getWidth() >> 1),
                        leftTop.y + (squareSize >> 1) + squareSize * i + ((int) (rect.getHeight() * 0.3)));
                graph.setColor(disarka);
                graph.setFont(new Font(font.getName(), Font.BOLD, squareSize >> 2));
                Point xy = drawDirection(graph, leftTop, i, j, squareSize, metrics);
                int direction = game.question[i][j].getDirection();
                graph.drawString(Game.getDirectionSymbol(direction), xy.x + j * squareSize, xy.y + i * squareSize);
                graph.setFont(new Font(font.getName(), Font.PLAIN, squareSize >> 2));

            }
        }
        graph.translate(-(gameRect.x + (squareSize << 1)), 0);
    }

    public int height() {
        return game.row * squareSize;
    }

    public int width() {
        return ((game.col << 1) + 2) * squareSize;
    }

    public Point gameRect() {
        Point gameRect = new Point(game.col * squareSize, game.row * squareSize);
        return gameRect;
    }

    public Point getCanvas() {
        Point center = new Point(component_width >> 1, component_height >> 1);
        Point leftTop = new Point(center.x - squareSize * (game.col + 1), center.y - ((game.row * squareSize) >> 1));
        return leftTop;
    }

    private Point drawDirection(Graphics2D graph, Point leftTop, int i, int j, int squareSize, FontMetrics metrics) {
        int x = 0, y = 0;
        int direction = game.question[i][j].getDirection();
        int width = (int) metrics.getStringBounds(Integer.toString(direction), graph).getWidth();
        int height = (int) metrics.getStringBounds(Integer.toString(direction), graph).getHeight();
        switch (direction) {
        case 1:
            x = leftTop.x + (squareSize >> 1) - (width >> 1); // TODO
            y = leftTop.y + (height >> 1) + (int) (squareSize * 0.1);
            break;
        case 2:
            x = leftTop.x + squareSize - width - (int) (squareSize * 0.1);
            y = leftTop.y + (height >> 1) + (int) (squareSize * 0.1);

            break;
        case 3:
            x = leftTop.x + squareSize - width - (int) (squareSize * 0.1);
            y = leftTop.y + (squareSize >> 1) + (int) (height * 0.3);
            break;
        case 4:
            x = leftTop.x + squareSize - width - (int) (squareSize * 0.1);
            y = leftTop.y + squareSize - (int) (squareSize * 0.1);
            break;
        case 5:
            x = leftTop.x + (squareSize >> 1) - (width >> 1); // TODO
            y = leftTop.y + squareSize - (int) (squareSize * 0.1);
            break;
        case 6:
            x = leftTop.x + (int) (squareSize * 0.1);
            y = leftTop.y + squareSize - (int) (squareSize * 0.1);
            break;
        case 7:
            x = leftTop.x + (int) (squareSize * 0.1);
            y = leftTop.y + (squareSize >> 1) + (int) (height * 0.3);

            break;
        case 8:
            x = leftTop.x + (int) (squareSize * 0.1);
            y = leftTop.y + (height >> 1) + (int) (squareSize * 0.1);

            break;
        default:
            x = 0;
            y = 0;
            break;
        }
        return new Point(x, y);
    }

    void drawLoadingIcon(Graphics2D g) {
        if (image != null) {
            g.clearRect(0, 0, getWidth(), getHeight());
            int x = (this.getWidth() - image.getWidth(null)) / 2;
            int y = (this.getHeight() - image.getHeight(null)) / 2;
            g.drawImage(image, x, y, this);
        }
    }

    // legacy
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
            br.readLine();
            br.readLine();
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