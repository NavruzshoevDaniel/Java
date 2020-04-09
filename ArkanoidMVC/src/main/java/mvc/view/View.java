package mvc.view;


import game.components.Commons;
import game.components.gui.*;
import mvc.controller.Controller;
import mvc.model.Model;
import mvc.model.observers.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class View implements ActionListener, Observer {
    private static final Logger logger = Logger.getLogger(View.class.getName());
    private Model model;
    private Controller controller;

    private boolean atomic = false;
    private AppFrame appFrame;
    private JPanel manager;
    private Background startMenu;
    private JPanel pauseMenu;
    private Board board;
    private StartButton startButton;
    private CardLayout cardLayout;
    private BallGUI ballGUI;


    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
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
        manager = new JPanel(cardLayout);
        startMenu = new Background(new GridBagLayout());
        startButton = new StartButton();
        ballGUI = new BallGUI(model.getBall().getX(), model.getBall().getY());

        model.setBALL_WIDTH(ballGUI.getImageWidth());
        model.setBALL_HEIGTH(ballGUI.getImageWidth());

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startGame();
            }
        });

        setLayoutForStartMenu();

        manager.add(startMenu, "start");
        manager.add(ballGUI, "board");

        appFrame.getContentPane().add(manager);
        appFrame.setVisible(true);
    }

    private void setLayoutForStartMenu() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        startMenu.add(startButton, gridBagConstraints);
    }


    @Override
    public void updateField() {
        ballGUI.setX(model.getBall().getX());
        ballGUI.setY(model.getBall().getY());
        appFrame.repaint();
        logger.log(Level.INFO, "Ball coords[" + model.getBall().getX() + "] [" + model.getBall().getY() + "]");
    }

    @Override
    public void updateStartMenu() {
        if (cardLayout != null)
            cardLayout.show(manager, "start");
    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }

    public void enableGame() {
        cardLayout.show(manager, "board");
    }
}
