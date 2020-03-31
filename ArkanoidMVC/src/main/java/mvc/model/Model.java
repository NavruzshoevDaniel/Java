package mvc.model;

import game.components.Ball;
import mvc.model.observers.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Model implements IModel {

    private ArrayList<Observer> observers = new ArrayList<>();

    private Timer timer;
    private Ball ball;
    private int speed=15;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;


    //Example
    private Image image= new ImageIcon("resources/ball.png").getImage();
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
    }

    @Override
    public void notifyObserver() {
        for (Observer obser:observers) {
            obser.updateField();
        }
    }

    @Override
    public void on() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                x += 1;
                y += 1;
                notifyObserver();
            }
        }, INITIAL_DELAY, PERIOD_INTERVAL);
    }

    private void updateBall() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
