package mvc.view;

import messages.Message;
import mvc.controller.Controller;
import mvc.model.Observer;
import mvc.model.OutputType;
import mvc.model.TcpClient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class View implements Observer {
    private static final Logger logger = Logger.getLogger(View.class.getName());
    private final int FRAME_WIDTH = 400;
    private final int FRAME_HIGTH = 300;

    private TcpClient model;
    private Controller controller;

    private JFrame appFrame;
    private JTextPane messages;
    private JTextArea textToSend;
    private JButton sendButton;
    private String title = "Client";


    public View(TcpClient model, Controller controller) {
        this.model = model;
        this.controller = controller;
        model.registerObserver(this);
    }

    void setSwingSettings() {
        appFrame = new JFrame();
        appFrame.setTitle(title);
        appFrame.setSize(FRAME_WIDTH, FRAME_HIGTH);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setLocationRelativeTo(null);

        messages = new JTextPane();
        messages.setText("Enter your name:\n");
        messages.setEditable(false);

        textToSend = new JTextArea();
        textToSend.setLineWrap(true);
        textToSend.setEditable(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JScrollPane jScrollPane = new JScrollPane(messages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanel.add(jScrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(textToSend, BorderLayout.CENTER);
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textMessage = textToSend.getText();
                if (textMessage != null) {
                    controller.sendMessage(textMessage);
                }
                textToSend.setText("");
            }
        });
        bottomPanel.add(sendButton, BorderLayout.EAST);
        bottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        appFrame.getContentPane().add(mainPanel);


        appFrame.setVisible(true);
    }

    public void createView() {

        EventQueue.invokeLater(() -> {
            logger.log(Level.INFO, "Start EventQueue");
            setSwingSettings();
            logger.log(Level.INFO, "End EventQueue");
        });
    }

    @Override
    public void updateView(OutputType type, String text) {
        StyledDocument doc= messages.getStyledDocument();
        Style style= messages.addStyle("",null);
        switch (type) {
            case ERROR:{
                StyleConstants.setForeground(style,Color.RED);
                break;
            }
            case SHARED:{
                StyleConstants.setForeground(style,Color.BLACK);
                break;
            }
            case SYSTEM:{
                StyleConstants.setForeground(style,Color.BLUE);
                break;
            }
        }
        try {
            doc.insertString(doc.getLength(),text+"\n",style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
