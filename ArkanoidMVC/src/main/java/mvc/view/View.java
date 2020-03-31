package mvc.view;


import game.components.gui.Board;
import mvc.controller.Controller;
import mvc.model.Model;
import mvc.model.observers.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View implements ActionListener, Observer {
    private Model model;
    private Controller controller;

    private JFrame appFrame;
    private JPanel jPanel;
    private Board board= new Board();
    private JDialog gameOver;

    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        model.registerObserver(this);
    }

    public void createView() {
        appFrame= new JFrame();
        setStartAppSettings(appFrame);
    }

    private void setStartAppSettings(JFrame appFrame) {
        appFrame.setTitle("Arkanoid");
        appFrame.add(board);
        appFrame.setSize(1000,700);
        appFrame.setLocationRelativeTo(null);
        appFrame.setResizable(false);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EventQueue.invokeLater(()-> appFrame.setVisible(true));
    }

    @Override
    public void updateField() {
        board.Repaint();
        board.changeCoords(model.getX(),model.getY());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
