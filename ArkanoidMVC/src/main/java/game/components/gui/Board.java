package game.components.gui;

import mvc.model.Model;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;



    public Board(Model model) {
        initBoard();
    }

    private void initBoard() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
