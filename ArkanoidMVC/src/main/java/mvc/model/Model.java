package mvc.model;

import mvc.model.game.components.*;
import mvc.model.observers.Observer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model implements IModel, Commons {
    private static final Logger logger = Logger.getLogger(Model.class.getName());

    private ArrayList<Observer> observers = new ArrayList<>();
    private boolean changed = false;
    private Timer timer;
    private State stateGame;
    private Ball ball;
    private Plank plank;
    private Wall wall;
    private int countBrickWidth=6;
    private int countBrickHeigth=5;
    private int BALL_WIDTH=50;
    private int PLANK_WIDTH=200;
    private int BALL_HEIGTH=50;
    private int PLANK_HEIGTH=21;

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public Plank getPlank() {
        return plank;
    }

    public int getBALL_WIDTH() {
        return BALL_WIDTH;
    }

    public int getPLANK_WIDTH() {
        return PLANK_WIDTH;
    }

    public int getBALL_HEIGTH() {
        return BALL_HEIGTH;
    }

    public int getPLANK_HEIGTH() {
        return PLANK_HEIGTH;
    }

    public enum State {
        inStartMenu,
        inGame,
        inPause,
        wait
    }

    public Model() {
        initModel();
    }

    private void initModel() {
        this.timer = new Timer();
        ball = new Ball(INIT_BALL_X, INIT_BALL_Y,BALL_WIDTH,BALL_HEIGTH);
        plank = new Plank(INIT_PLANK_X, INIT_PLANK_Y,PLANK_WIDTH,PLANK_HEIGTH);
        stateGame = State.inStartMenu;
        Rectangle wallArea= new Rectangle(50,50,900,230);
        wall= new Wall(wallArea,countBrickWidth,countBrickHeigth,3,3);
    }

    public Ball getBall() {
        return ball;
    }

    public void setStateGame(State stateGame) {
        this.stateGame = stateGame;
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
        logger.log(Level.INFO, "New observer subscribed: " + observer);
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
        logger.log(Level.INFO, "Observer unsubscribed: " + observer);
    }

    @Override
    public void notifyGameObserver() {
        for (Observer obser : observers) {
            obser.updateField();
        }
    }

    @Override
    public void notifyStartMenu() {
        for (Observer obser : observers) {
            obser.updateStartMenu();
        }
    }

    @Override
    public void init() {
        timer = new Timer();
        timer.schedule(new ScheduleTask(), INITIAL_DELAY, PERIOD);
    }


    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            switch (stateGame) {
                case inGame: {
                    doInGame();
                    if (!changed){
                        changed=true;
                        notifyGameObserver();
                    }
                    break;
                }
                case inPause: {

                    break;
                }
                case inStartMenu: {
                    logger.log(Level.INFO, "In Start Menu");
                    notifyStartMenu();
                    stateGame = State.wait;
                    logger.log(Level.INFO, "Out Start Menu");
                    break;
                }
                default:

            }
        }
    }

    private void doInGame() {
        updateBall();
        updatePlank();
    }

    private void updatePlank() {
        plank.move();
        if(plank.getX()<0){
            plank.setX(0);
        }
        if (plank.getX()>BOARD_WIDTH-PLANK_WIDTH){
            plank.setX(BOARD_WIDTH-PLANK_WIDTH);
        }
    }

    private void updateBall() {
        ball.move();

        if (ball.getX() < 0) {

            ball.setDirectionX(1);
        }

        if (ball.getX() > BOARD_WIDTH - BALL_WIDTH) {
            ball.setDirectionX(-1);
        }

        if (ball.getY() < 0) {
            ball.setDirectionY(1);
        }

        if (ball.getY() > BOARD_HEIGHT - BALL_HEIGTH) {
            ball.setDirectionY(-1);
        }

    }

}

