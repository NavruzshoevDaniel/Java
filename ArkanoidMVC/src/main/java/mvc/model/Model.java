package mvc.model;

import game.components.Ball;
import game.components.Plank;
import mvc.model.observers.Observer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model implements IModel {
    private static final Logger logger =Logger.getLogger(Model.class.getName());

    private ArrayList<Observer> observers = new ArrayList<>();
    private int INIT_BALL_X =500;
    private int INIT_BALL_Y =450;


    private Timer timer;
    private State stateGame=State.inStartMenu;
    private Ball ball;
    private Plank plank;
    private int speed = 10;
    private final int INITIAL_DELAY = 0;
    private final int PERIOD_INTERVAL = 25;


    //Example

    public Model() {
        initModel();
    }

    private void initModel() {
        this.timer = new Timer();
    }


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
    public void init() {
        timer = new Timer();
        timer.schedule(new ScheduleTask(),INITIAL_DELAY,PERIOD_INTERVAL);
    }

    private void updateBall() {

    }



    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            switch (stateGame){
                case inGame:{

                    doInGame();
                    break;
                }
                case inPause:{

                    break;
                }
                case inStartMenu:{

                    break;
                }

            }
            notifyObserver();
        }
    }

    private void doInGame() {
    }
}

enum State{
    inStartMenu,
    inGame,
    inPause
}
