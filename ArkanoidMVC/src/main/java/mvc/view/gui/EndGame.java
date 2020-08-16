package mvc.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EndGame extends JPanel {
    private static final Logger logger = Logger.getLogger(EndGame.class.getName());
    private Image backGround;
    private JPanel manager;
    private JLabel winGUI;
    private JLabel loseGUI;
    private BackButton backButton;


    public EndGame(BackButton backButton, int width, int hiegth) {
        this.backButton = backButton;
        loseGUI = new JLabel();
        winGUI = new JLabel();
        manager = new JPanel(new GridBagLayout());
        init(width, hiegth);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, null);
    }


    private void init(int width, int hiegth) {
        this.add(manager);
        manager.setOpaque(false);
        try {
            backGround = ImageIO.read(EndGame.class.getResourceAsStream("/boardBackground.png"));
            GuiComponent tmpLoseGUI = new GuiComponent("/gameOver.png", width, hiegth);
            GuiComponent tmpWinGUI = new GuiComponent("/winner.png", width, hiegth);
            loseGUI.setIcon(new ImageIcon(tmpLoseGUI.getImage()));
            winGUI.setIcon(new ImageIcon(tmpWinGUI.getImage()));
        } catch (IOException e) {
            logger.log(Level.WARNING, "Board background file doesn't exits", e);
        }
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        manager.add(backButton, gridBagConstraints);
        this.add(manager);
    }

    public void setWin() {
        logger.log(Level.INFO, "Setting win image");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        manager.remove(loseGUI);
        manager.add(winGUI, gridBagConstraints);
    }

    public void setLose() {
        logger.log(Level.INFO, "Setting lose image");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        manager.remove(winGUI);
        manager.add(loseGUI, gridBagConstraints);
    }
}
