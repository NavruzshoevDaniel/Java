package mvc.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartButton extends JButton {
    private static final Logger logger = Logger.getLogger(StartButton.class.getName());
    private ImageIcon imageIcon;

    public StartButton() {
        super();
        setSettings();
    }

    public StartButton(Icon icon) {
        super(icon);
        setSettings();
    }

    public StartButton(String text) {
        super(text);
        setSettings();
    }

    public StartButton(Action a) {
        super(a);
        setSettings();
    }

    public StartButton(String text, Icon icon) {
        super(text, icon);
        setSettings();
    }

    private void setSettings() {
        Image image ;

        try {
            imageIcon = new ImageIcon();
            image = ImageIO.read(Background.class.getResourceAsStream("/button.png"));
            imageIcon.setImage(image);
            setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
            setIcon(imageIcon);
        } catch (IOException e) {
            logger.log(Level.WARNING,"StartButton file doesn't exists",e);
        }

       // setContentAreaFilled(false);
        setBorderPainted(false);
        //setFocusPainted(false);
        setOpaque(false);
    }
}
