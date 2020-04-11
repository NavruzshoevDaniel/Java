package game.components.gui;


import game.components.Commons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Board extends JPanel {
    private static final Logger logger = Logger.getLogger(Board.class.getName());
    private Image backGround;
    private BallGUI ballGUI;
    private PlankGUI plankGUI;


    public Board(BallGUI ballGUI, PlankGUI plank) {
        this.plankGUI=plank;
        this.ballGUI = ballGUI;
        setFocusable(true);
        try {
            backGround=ImageIO.read(Board.class.getResourceAsStream("/boardBackground.png"));
        } catch (IOException e) {
            logger.log(Level.WARNING,"Board background file doesn't exits",e);
        }
        setPreferredSize(new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT));

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround,0,0,null);
        g.drawImage(ballGUI.getImage(),ballGUI.getX(),ballGUI.getY(),null);
        g.drawImage(plankGUI.getImage(),plankGUI.getX(),plankGUI.getY(),null);
    }


}
