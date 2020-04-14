package mvc.view.gui;

import mvc.model.game.components.Commons;

import javax.swing.*;

public class AppFrame extends JFrame {
    private final int FRAME_WIDTH = Commons.BOARD_WIDTH;
    private final int FRAME_HIGTH = Commons.BOARD_HEIGHT;
    private final String FRAME_NAME = "Arkanoid";

    public AppFrame() {
        initUI();
    }

    private void initUI() {
        setTitle(FRAME_NAME);
        setSize(FRAME_WIDTH,FRAME_HIGTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public int getWidth() {
        return FRAME_WIDTH;
    }

    public int getHigth() {
        return FRAME_HIGTH;
    }
}
