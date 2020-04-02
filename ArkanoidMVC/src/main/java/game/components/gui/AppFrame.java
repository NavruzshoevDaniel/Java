package game.components.gui;

import javax.swing.*;



public class AppFrame extends JFrame {
    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HIGTH = 700;
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
}
