package mvc.model;

import game.components.Ball;
import game.components.Brick;
import game.components.Plank;
import mvc.model.observers.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model implements IModel {
    private static final Logger logger =Logger.getLogger(Model.class.getName());

    private ArrayList<Observer> observers = new ArrayList<>();

    private Timer timer;
    private boolean inGame;
    private Ball ball;
    private Plank plank;
    private int speed = 10;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;


    //Example
    private Image image = new ImageIcon("resources/ball.png").getImage();
    private int x, y;


    @Override
    public void registerObserver(Observer observer) {

        if (observer == null) {
            throw new NullPointerException();
        }
        if (observers.contains(observer)) {
            throw new IllegalArgumentException("Repeated observer:" + observer);
        }
        observers.add(observer);
        logger.log(Level.INFO,"New observer subscribed: "+observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (observers.contains(observer)) {
            throw new IllegalArgumentException("Repeated observer:" + observer);
        }
        observers.remove(observer);
        logger.log(Level.INFO,"Observer unsubscribed: "+observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer obser : observers) {
            obser.updateField();
        }
    }

    @Override
    public void on() {
        timer = new Timer();
        timer.schedule(new ScheduleTask(),INITIAL_DELAY,PERIOD_INTERVAL);
    }

    private void updateBall() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            x += 1;
            y += 1;
            notifyObserver();
        }
    }
}
