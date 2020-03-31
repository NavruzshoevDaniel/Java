package game.components.gui;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;

    private Image star;
    private int x, y;

    public Board() {
        initBoard();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/ball.png");
        star = ii.getImage();
    }

    private void initBoard() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawStar(g);
    }

    private void drawStar(Graphics g) {

        g.drawImage(star, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    public void Repaint(){
        repaint();
    }

    public void changeCoords(int x,int y){
        this.x=x;
        this.y=y;
    }
}
