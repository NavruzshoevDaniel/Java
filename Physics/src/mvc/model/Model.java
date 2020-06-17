package mvc.model;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model implements Observable {
    private final Logger logger = Logger.getLogger(Model.class.getName());

    private final int SIZE_BOUNDS = 10000;
    private ArrayList<Root> roots = new ArrayList<>();
    private Vector<Double> bounds = new Vector<>();
    private double right = 0;
    private double left = -1;
    private double e = 1e-15;
    private double U = 1;
    private double m = 1;
    private double h = 1;
    private double a = 15;
    private double b = 0.2;
    private ArrayList<Observer> observers = new ArrayList<>();

    public Model() {
        init();
    }

    private void init() {
        double sizeBound = Math.abs(right - left) / SIZE_BOUNDS;
        for (int i = 0; i < SIZE_BOUNDS; ++i) {
            bounds.add( i, left + i * sizeBound);
        }
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
    }

    @Override
    public void updateObservers() {
        for (Observer observer : observers) {
            observer.updateView(roots);
        }
    }

    public void update(int a, double b) {

        roots.clear();
        setA(a);
        setB(b);
        searchRoots(a, b);
        updateObservers();
    }

    private void searchRoots(int a, double b) {
        double left = 0;
        double right = 0;
        double mid = (left + right) / 2.0;
        boolean symm = true;

        for (int i = 0; i < SIZE_BOUNDS - 1; ++i) {
            left = bounds.get(i);
            right = bounds.get(i + 1);
            while (Math.abs(right - left) > e) {

                mid = (left + right) / 2.0;

                if ((f(mid, symm) * f(left, symm)) < 0) {
                    right = mid;
                } else if ((f(mid, symm) * f(right, symm)) < 0) {
                    left = mid;
                } else {
                    mid = 0;
                    break;
                }
            }
            Root root = new Root(mid, m, U, h, b, a, symm);
            if (mid != 0 && root.getC() != 0.0 && root.getC() != Double.POSITIVE_INFINITY
                    && root.getC() != Double.NEGATIVE_INFINITY) {
                symm = !symm;
                roots.add(root);
                logger.log(Level.INFO,"New Root:E="+root.getE()+"\nk1="+root.getK1()
                        +"\nk2="+root.getK2()+"\nC="+root.getC());
            }

        }
    }

    private double f(double E, boolean symm) {
        if (symm)
            return Math.tan(Math.sqrt(2 * m * (E + U)) * a / (2 * h)) - 1 / Math.sqrt((U / Math.abs(E)) - 1);
        else
            return -1 / Math.tan((Math.sqrt(2 * m * (E + U)) * a) / (2 * h)) - 1 / Math.sqrt((U / Math.abs(E)) - 1);
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }
}
