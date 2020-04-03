package game.components.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Background extends JPanel {
    private static final Logger logger = Logger.getLogger(Background.class.getName());
    private static Image image;

    public Background(LayoutManager layout) {
        super(layout);
        if (image == null)
            init();
    }

    public Background() {
        init();
    }

    private void init() {
        try {
            image = ImageIO.read(Background.class.getResourceAsStream("/background.png"));
        } catch (IOException e) {
            logger.log(Level.WARNING, "Background file doesn't exits", e);
        }
        Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
