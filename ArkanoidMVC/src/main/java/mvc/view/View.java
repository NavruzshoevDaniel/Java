package mvc.view;


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
