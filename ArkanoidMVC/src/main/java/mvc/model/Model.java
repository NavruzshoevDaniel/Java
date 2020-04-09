package mvc.model;

import game.components.Ball;
import game.components.Commons;
import game.components.Plank;
import mvc.model.observers.Observer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model implements IModel, Commons {
    private static final Logger logger = Logger.getLogger(Model.class.getName());


    private ArrayList<Observer> observers = new ArrayList<>();
    private Timer timer;
    private State stateGame;
    private Ball ball;
    private Plank plank;
    private int BALL_WIDTH;
    private int BALL_HEIGTH;

    public void setBALL_WIDTH(int BALL_WIDTH) {
        this.BALL_WIDTH = BALL_WIDTH;
    }

    public void setBALL_HEIGTH(int BALL_HEIGTH) {
        this.BALL_HEIGTH = BALL_HEIGTH;
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
        ball =new Ball(INIT_BALL_X,INIT_BALL_Y);
        plank= new Plank(INIT_PLANK_X,INIT_PLANK_Y);
        stateGame=State.inStartMenu;
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
                    notifyGameObserver();
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
    }

    private void updateBall() {
        ball.move();

        if(ball.getX()<0){

            ball.setDirectionX(1);
        }

        if(ball.getX()>BOARD_WIDTH-BALL_WIDTH){
            ball.setDirectionX(-1);
        }

        if(ball.getY()<0){
            ball.setDirectionY(1);
        }

        if (ball.getY()>BOARD_HEIGHT-BALL_HEIGTH){
            ball.setDirectionY(-1);
        }

    }

}

