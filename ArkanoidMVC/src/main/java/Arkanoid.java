

import mvc.controller.Controller;
import mvc.model.Model;

import javax.swing.*;

public class Arkanoid extends JFrame {

    public static void main(String[] args) {
        Model model=new Model();
        Controller controller=new Controller(model);
    }
}
