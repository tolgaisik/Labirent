
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Info extends JFrame implements ActionListener {
    JButton edit, save;
    JTextArea text;
    JPanel pnl;
    int NODE = 0;

    Info(int _node) {
        super();
        NODE = _node;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void init() {
        setTitle("Bilgiler");
        setBounds(200, 200, 400, 400);
        getContentPane().setLayout(new BorderLayout());
        setVisible(true);
        pnl = new JPanel();
        edit = new JButton("Duzenle");
        save = new JButton("Kaydet");
        edit.addActionListener(this);
        save.addActionListener(this);
        text = new JTextArea();
        text.setVisible(true);
        text.setEditable(false);
        text.setBackground(java.awt.Color.WHITE);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setFont(new Font("Arial", Font.BOLD, 14));
        text.setPreferredSize(new Dimension(400, 323));
        String str = "";
        try {
            str = getInfo(NODE);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        text.setText(str);
        add(text);
        pnl.add(save);
        pnl.add(edit);
        add(pnl, BorderLayout.SOUTH);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == edit) {
            text.setEditable(true);
        } else if (source == save) {
            text.setEditable(false);
            StringBuilder builder = new StringBuilder(text.getText());
            try {
                updateInfo(NODE, builder.toString());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
            dispose();
        }

    }

    Document getRootElement(String FILENAME) throws ParserConfigurationException, SAXException, IOException {
        if (FILENAME == null)
            FILENAME = "info.xml";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(new File(FILENAME));
        doc.getDocumentElement().normalize();
        return doc;
    }

    String getInfo(int id) throws ParserConfigurationException, SAXException, IOException {
        Document doc = getRootElement(null);
        NodeList nodes = doc.getElementsByTagName("game");
        Node node = nodes.item(id);
        StringBuilder builder = new StringBuilder();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            builder.append(element.getTextContent());
        }
        return builder.toString();
    }

    void updateInfo(int id, String info)
            throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Document doc = getRootElement(null);
        NodeList nodes = doc.getElementsByTagName("game");
        Node node = nodes.item(id);
        node.setTextContent(info);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File("info.xml"));
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
    }

    public static void main(String[] args) {
        Info info = new Info(0);
        info.init();

    }
}
