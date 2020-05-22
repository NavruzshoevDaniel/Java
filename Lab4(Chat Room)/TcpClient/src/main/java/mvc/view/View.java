package mvc.view;

import mvc.controller.Controller;
import mvc.model.Model;
import mvc.model.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class View implements Observer {
    private static final Logger logger = Logger.getLogger(View.class.getName());
    private final int FRAME_WIDTH = 400;
    private final int FRAME_HIGTH = 300;

    private Model model;
    private Controller controller;

    private JFrame appFrame;
    private JTextArea messages;
    private JTextArea textArea;
    private JButton sendButton;
    private String title = "Client";


    public View(Model model, Controller controller) {
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

        messages = new JTextArea("Добро поджаловать!\nВведите свое имя:\n");
        messages.setEditable(false);
        messages.setLineWrap(true);

        textArea = new JTextArea(title);
        textArea.setLineWrap(true);
        textArea.setEditable(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JScrollPane jScrollPane = new JScrollPane(messages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanel.add(jScrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(textArea, BorderLayout.CENTER);
        sendButton = new JButton("Отправить");
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
    public void updateView() {

    }
}
