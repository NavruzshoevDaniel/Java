package mvc.view;


import game.components.gui.AppFrame;
import game.components.gui.Board;
import game.components.gui.StartButton;
import mvc.controller.Controller;
import mvc.model.Model;
import mvc.model.observers.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class View implements ActionListener, Observer {
    private static final Logger logger = Logger.getLogger(View.class.getName());
    private Model model;
    private Controller controller;

    private boolean atomic = false;
    private AppFrame appFrame;
    private JPanel manager;
    private JPanel startMenu;
    private JPanel pauseMenu;
    private Board board;
    private Image background;
    private StartButton startButton;
    CardLayout cardLayout;


    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        try {
            this.background = ImageIO.read(View.class.getResourceAsStream("/bluemoon.png"));
        } catch (IOException e) {
            logger.log(Level.WARNING, "Background file doesn't exits", e);
        }
        model.registerObserver(this);
    }

    public void createView() {

        EventQueue.invokeLater(() -> {
            logger.log(Level.INFO, "Start EventQueue");
            setSwingSettings();
            logger.log(Level.INFO, "End EventQueue");
        });
    }

    private void setSwingSettings() {
        appFrame = new AppFrame();
        board = new Board(model);
        cardLayout = new CardLayout();
        manager = new JPanel();
        manager.setLayout(cardLayout);

        startMenu = new JPanel(new GridBagLayout());
        startButton = new StartButton("Start");

        setLayoutForStartMenu();

        manager.add(startMenu, "start");
        manager.add(board, "board");

        appFrame.getContentPane().add(manager);
        cardLayout.show(manager, "start");
        appFrame.setVisible(true);
    }

    private void setLayoutForStartMenu() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        startMenu.add(startButton, gridBagConstraints);
    }


    @Override
    public void updateField() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
