package mvc.view;


import game.components.gui.*;
import mvc.controller.Controller;
import mvc.model.Model;
import mvc.model.observers.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class View implements Observer {
    private static final Logger logger = Logger.getLogger(View.class.getName());
    private Model model;
    private Controller controller;

    private AppFrame appFrame;
    private JPanel manager;
    private Background startMenu;
    private JPanel pauseMenu;
    private Board board;
    private StartButton startButton;
    private CardLayout cardLayout;
    private BallGUI ballGUI;
    private PlankGUI plankGUI;


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
        cardLayout = new CardLayout();
        manager = new JPanel(cardLayout);
        startMenu = new Background(new GridBagLayout());
        startButton = new StartButton();
        ballGUI = new BallGUI(model.getBall().getX(), model.getBall().getY());
        plankGUI = new PlankGUI(model.getPlank().getX(),model.getPlank().getY());
        board = new Board(ballGUI,plankGUI);

        manager.setFocusable(true);

        board.setFocusable(true);
        board.addKeyListener(new ViewAdapter());

        model.setBALL_WIDTH(ballGUI.getImageWidth());
        model.setBALL_HEIGTH(ballGUI.getImageWidth());
        model.setPLANK_HEIGTH(plankGUI.getHeight());
        model.setPLANK_WIDTH(plankGUI.getWidth());

        startButton.addActionListener(e -> controller.startGame());

        setLayoutForStartMenu();

        manager.add(startMenu, "start");
        manager.add(board, "board");

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
        plankGUI.setX(model.getPlank().getX());
        plankGUI.setY(model.getPlank().getY());

        appFrame.repaint();
       //logger.log(Level.INFO, "Ball coords[" + model.getBall().getX() + "] [" + model.getBall().getY() + "]");
        model.setChanged(false);
    }

    @Override
    public void updateStartMenu() {
        if (cardLayout != null)
            cardLayout.show(manager, "start");
    }

    public void enableGame() {
        cardLayout.show(manager, "board");
    }

    private class ViewAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            logger.log(Level.INFO,"IN KEY PRESSED");
            controller.keyPressed(e);

        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            logger.log(Level.INFO,"IN KEY RELEASED");
            controller.keyReleased(e);
        }
    }
}
