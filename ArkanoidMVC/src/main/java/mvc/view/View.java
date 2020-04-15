package mvc.view;


import mvc.model.game.components.Wall;
import mvc.view.gui.*;
import mvc.controller.Controller;
import mvc.model.Model;
import mvc.model.observers.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
    private GuiComponent ballGUI;
    private GuiComponent plankGUI;
    private ArrayList<GuiComponent> brickGUIS;


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
        ballGUI = new GuiComponent("/ball.png", model.getBALL_WIDTH(), model.getBALL_HEIGTH());
        plankGUI = new GuiComponent("/plank.png", model.getPLANK_WIDTH(), model.getPLANK_HEIGTH());
        brickGUIS = new ArrayList<>();
        initBricks();
        updateCoords();
        board = new Board(ballGUI, plankGUI, brickGUIS);

        board.addKeyListener(new ViewKeyAdapter());
        board.addMouseListener(new ViewMouseAdapter());

        startButton.addActionListener(e -> controller.startGame());

        setLayoutForStartMenu();

        manager.add(startMenu, "start");
        manager.add(board, "board");

        appFrame.getContentPane().add(manager);
        appFrame.setVisible(true);
    }

    private void initBricks() {
        int brickWidth = model.getWall().getBrickWidth();
        int brickHeight = model.getWall().getBrickHeight();

        for (int i = 0; i < model.getWall().getCount(); i++) {
            brickGUIS.add(i, new GuiComponent("/brick.png", brickWidth, brickHeight));
        }


    }

    private void updateCoords() {
        updateBallCoords();

        updatePlankCoords();

        updateWallCoords();
    }

    private void updateWallCoords() {
        Wall wallModel = model.getWall();

        for (int i = 0; i < model.getWall().getCount(); i++) {
            if(!wallModel.getBricks().get(i).isDestroyed()){
                brickGUIS.get(i).setX(wallModel.getBricks().get(i).getX());
                brickGUIS.get(i).setY(wallModel.getBricks().get(i).getY());
            }

        }
    }

    private void updatePlankCoords() {
        plankGUI.setX(model.getPlank().getX());
        plankGUI.setY(model.getPlank().getY());
    }

    private void updateBallCoords() {
        ballGUI.setX(model.getBall().getX());
        ballGUI.setY(model.getBall().getY());
    }

    private void setLayoutForStartMenu() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        startMenu.add(startButton, gridBagConstraints);
    }


    @Override
    public void updateField() {
        updateCoords();
        appFrame.repaint();
        model.setChanged(false);
    }

    @Override
    public void updateWall(int index) {
        brickGUIS.get(index).setVisible(false);
        appFrame.repaint();
        model.setChanged(false);
    }

    @Override
    public void updateBall() {
        updateBallCoords();
        appFrame.repaint();
        model.setChanged(false);
    }

    @Override
    public void updatePlank() {
        updatePlankCoords();
        appFrame.repaint();
        model.setChanged(false);
    }

    @Override
    public void updateStartMenu() {
        if (cardLayout != null)
            cardLayout.show(manager, "start");
    }

    public void enableGame() {
        cardLayout.show(manager, "board");
        board.requestFocusInWindow();
    }

    private class ViewKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            logger.log(Level.INFO, "IN KEY PRESSED");
            controller.keyPressed(e);

        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            logger.log(Level.INFO, "IN KEY RELEASED");
            controller.keyReleased(e);
        }
    }

    private class ViewMouseAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            logger.log(Level.INFO, "IN MOUSE CLICKED");
            if (!model.isBallStart())
                controller.chooseDirection(e);
        }
    }
}
