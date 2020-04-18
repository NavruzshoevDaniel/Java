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
    private int countBrickWidth = 10;
    private int countBrickHeigth = 5;
    private int countDestroyed;
    private boolean ballStart;
    private boolean win;

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

    public Wall getWall() {
        return wall;
    }

    public void setBallStart(boolean ballStart) {
        this.ballStart = ballStart;
    }

    public boolean isBallStart() {
        return ballStart;
    }

    public enum State {
        inStartMenu,
        inGame,
        endGame,
        wait
    }

    public Model() {
        initModel();
    }

    private void initModel() {
        this.timer = new Timer();
        ball = new Ball(INIT_BALL_X, INIT_BALL_Y, BALL_WIDTH, BALL_HEIGTH);
        plank = new Plank(INIT_PLANK_X, INIT_PLANK_Y, PLANK_WIDTH, PLANK_HEIGTH);
        stateGame = State.inStartMenu;
        Rectangle wallArea = new Rectangle(INIT_WALL_X, INIT_WALL_Y, BOARD_WIDTH - 2 * INIT_WALL_X, BOARD_HEIGHT / 4);
        wall = new Wall(wallArea, countBrickWidth, countBrickHeigth, OFFSET_BRICKS, OFFSET_BRICKS);
        ballStart = false;
        countDestroyed = 0;
        win = false;
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
    public void notifyGameObserver(Operation operation, Object arg) {
        for (Observer obser : observers) {
            switch (operation) {
                case ALL: {
                    obser.updateField();
                    break;
                }
                case BALL: {
                    obser.updateBall();
                    break;
                }
                case WALL: {
                    obser.updateWall((Integer) arg);
                    break;
                }
                case PLANK: {
                    obser.updatePlank();
                    break;
                }
                case START_MENU: {
                    obser.updateStartMenu();
                    break;
                }
                case WIN: {
                    obser.win();
                    break;
                }
                case LOSE: {
                    obser.lose();
                    break;
                }
                case RESET: {
                    obser.reset();
                    break;
                }
            }
        }
    }

    @Override
    public void init() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD);
    }


    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            switch (stateGame) {
                case inGame: {
                    doInGame();
                    if (!changed) {
                        changed = true;
                        notifyGameObserver(Operation.ALL, null);
                    }
                    break;
                }
                case endGame: {
                    if (win) {
                        notifyGameObserver(Operation.WIN, null);
                    } else {
                        notifyGameObserver(Operation.LOSE, null);
                    }
                    stateGame = State.wait;
                    resetGame();
                    break;
                }
                case inStartMenu: {
                    logger.log(Level.INFO, "In Start Menu");
                    notifyGameObserver(Operation.START_MENU, null);
                    stateGame = State.wait;
                    logger.log(Level.INFO, "Out Start Menu");
                    break;
                }
                default:

            }
        }
    }

    private void resetGame() {
        win = false;
        ballStart = false;
        wall.resetBricks();
        plank.setX(INIT_PLANK_X);
        plank.setY(INIT_PLANK_Y);
        plank.setDx(0);
        notifyGameObserver(Operation.RESET, null);
        countDestroyed = 0;
    }

    private void doInGame() {
        updateBall();
        updatePlank();
        checkCollision();
        checkEndGame();
    }

    private void checkEndGame() {
        if (ball.getY() + ball.getHiegth() > BOARD_HEIGHT) {
            win = false;
            stateGame = State.endGame;

        }
        if (countDestroyed == countBrickHeigth * countBrickWidth) {
            win = true;
            stateGame = State.endGame;
        }
    }

    private void checkCollision() {

        checkBallAndPlank();

        checkBallAndBricks();
    }

    private void checkBallAndBricks() {
        for (int i = 0; i < wall.getCount(); i++) {
            ArrayList<Brick> bricks = wall.getBricks();
            if (ball.getArea().intersects(bricks.get(i).getArea())) {
                int ballLeft = (int) ball.getArea().getMinX();
                int ballTop = (int) ball.getArea().getMinY();

                Point pointRight = new Point(ballLeft + ball.getWidth() + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom = new Point(ballLeft, ballTop + ball.getHiegth() + 1);

                if (!bricks.get(i).isDestroyed()) {
                    if (bricks.get(i).getArea().contains(pointTop)
                            || bricks.get(i).getArea().contains(pointBottom)) {
                        ball.setDirectionY(-1 * ball.getDirectionY());
                    } else if (bricks.get(i).getArea().contains(pointRight)
                            || bricks.get(i).getArea().contains(pointLeft)) {
                        ball.setDirectionX(-1 * ball.getDirectionX());
                    }
                    notifyGameObserver(Operation.WALL, i);
                    ++countDestroyed;
                    bricks.get(i).setDestroyed(true);
                }
            }
        }
    }

    private void checkBallAndPlank() {
        if (ball.getArea().intersects(plank.getArea()) && plank.isInMove()) {

            ball.setDirectionY(-1 * ball.getDirectionY());

            if (plank.getDx() < 0) {
                if (ball.getDirectionX() > 0)
                    ball.setDirectionX(-1 * ball.getDirectionX());
            } else if (plank.getDx() > 0) {
                if (ball.getDirectionX() < 0)
                    ball.setDirectionX(-1 * ball.getDirectionX());
            }

        } else if (ball.getArea().intersects(plank.getArea()) && !plank.isInMove()) {
            ball.setDirectionY(-1 * ball.getDirectionY());
        }
    }

    private void updatePlank() {
        plank.move();
        if (plank.getX() < 0) {
            plank.setX(0);
        }
        if (plank.getX() > BOARD_WIDTH - PLANK_WIDTH) {
            plank.setX(BOARD_WIDTH - PLANK_WIDTH);
        }
    }

    private void updateBall() {
        if (ballStart) {
            ball.move();
        } else {
            int plankX = plank.getX();
            int plankY = plank.getY();
            ball.setX(plankX + plank.getWidth() / 2 - ball.getWidth() / 2);
            ball.setY(plankY - ball.getHiegth());
        }


        int ballX = ball.getX();
        int ballY = ball.getY();

        if (ballX < 0 || ballX > BOARD_WIDTH - BALL_WIDTH) {

            ball.setDirectionX(ball.getDirectionX() * (-1));
        }

        if (ballY < 0) {
            ball.setDirectionY(ball.getDirectionY() * (-1));
        }

    }


    public enum Operation {
        ALL,
        BALL,
        WALL,
        PLANK,
        START_MENU,
        WIN,
        LOSE,
        RESET
    }
}

