package mvc.view;


import mvc.view.gui.*;
import mvc.controller.Controller;
import mvc.model.Model;
import mvc.model.observers.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
        ballGUI = new GuiComponent("/ball.png",model.getBALL_WIDTH(),model.getBALL_HEIGTH());
        plankGUI = new GuiComponent("/plank.png",model.getPLANK_WIDTH(),model.getPLANK_HEIGTH());
        brickGUIS=new ArrayList<>();
        initStartCoords();
        board = new Board(ballGUI, plankGUI,brickGUIS);

        board.addKeyListener(new ViewAdapter());

        startButton.addActionListener(e -> controller.startGame());

        setLayoutForStartMenu();

        manager.add(startMenu, "start");
        manager.add(board, "board");

        appFrame.getContentPane().add(manager);
        appFrame.setVisible(true);
    }

    private void initStartCoords() {
        ballGUI.setX(model.getBall().getX());
        ballGUI.setY(model.getBall().getY());

        plankGUI.setX(model.getPlank().getX());
        plankGUI.setY(model.getPlank().getY());

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

    private class ViewAdapter extends KeyAdapter {

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
}
