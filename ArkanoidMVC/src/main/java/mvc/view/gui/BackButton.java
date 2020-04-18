package mvc.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackButton extends JButton {
    private static final Logger logger = Logger.getLogger(BackButton.class.getName());
    private ImageIcon imageIcon;

    public BackButton() {
        super();
        setSettings();
    }

    private void setSettings() {
        Image image ;

        try {
            imageIcon = new ImageIcon();
            image = ImageIO.read(Background.class.getResourceAsStream("/back.png"));
            this.imageIcon.setImage(image);
            setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
            setIcon(imageIcon);
        } catch (IOException e) {
            logger.log(Level.WARNING,"BackButton file doesn't exists",e);
        }

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
    }
}
