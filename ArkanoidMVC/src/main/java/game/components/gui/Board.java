package game.components.gui;

import mvc.model.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Board extends JPanel {
    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private Image image;



    public Board(Model model) {
        initBoard();
    }

    private void initBoard() {
        try {
            image= ImageIO.read(Board.class.getResourceAsStream("/ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,100,100,null);
    }
}
