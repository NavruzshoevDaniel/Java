package game.components.gui;

import javax.swing.*;
import java.awt.*;

public class StartButton extends JButton {

    public StartButton() {
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
    private void setSettings(){
        setPreferredSize(new Dimension(200,100));
        setFont(new Font("Arial",Font.PLAIN,40));
    }
}
