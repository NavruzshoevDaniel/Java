package mvc.model;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model implements Observable {
    private final Logger logger = Logger.getLogger(Model.class.getName());

    private Hashtable<String, Double> defineTable = new Hashtable<>();
    private final int SIZE_BOUNDS = 5000;
    private ArrayList<Root> roots = new ArrayList<>();
    private final Vector<Double> bounds = new Vector<>();
    private final double RIGHT = 0;
    private final double LEFT = -1;
    private final double e = 1e-15;

    private ArrayList<Observer> observers = new ArrayList<>();
    private boolean isUpdating = false;

    public Model() {
        init();
    }

    private void init() {
        defineTable.put("A", 15d);
        defineTable.put("B", 0.2);
        defineTable.put("U", 1d);
        defineTable.put("M", 1d);
        defineTable.put("H", 1d);
        double sizeBound = Math.abs(RIGHT - LEFT) / SIZE_BOUNDS;
        for (int i = 0; i < SIZE_BOUNDS; ++i) {
            bounds.add(i, LEFT + i * sizeBound);
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
    public void updateObservers(XYSeriesCollection collection) {
        for (Observer observer : observers) {
            observer.updateView(collection);
        }
    }

    public void update() {
        if (!isUpdating) {
            roots.clear();
            searchRoots();
            updateObservers(createPoints());
        }
    }

    private XYSeriesCollection createPoints() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        double a = defineTable.get("A");
        double b = defineTable.get("B");

        dataset.addSeries(createPotentialWell(a));

        for (int i = 0; i < roots.size(); i++) {

            XYSeries xySeries = new XYSeries(String.valueOf(i), true, true);
            Root root = roots.get(i);
            for (double x = -1 * a - 10; x < a + 10; x += 0.01) {
                if (i % 2 == 0) {
                    if (x <= -a / 2) {
                        xySeries.add(x, root.getC() * Math.exp(root.getK2() * x) - Math.abs(root.getE()));
                    } else if (x >= a / 2) {
                        xySeries.add(x, root.getC() * Math.exp(-root.getK2() * x) - Math.abs(root.getE()));
                    } else {
                        xySeries.add(x, b * Math.cos(root.getK1() * x) - Math.abs(root.getE()));
                    }
                } else {
                    if (x <= -a / 2) {
                        xySeries.add(x, -root.getC() * Math.exp(root.getK2() * x) - Math.abs(root.getE()));
                    } else if (x >= a / 2) {
                        xySeries.add(x, root.getC() * Math.exp(-root.getK2() * x) - Math.abs(root.getE()));
                    } else {
                        xySeries.add(x, b * Math.sin(root.getK1() * x) - Math.abs(root.getE()));
                    }
                }
            }

            dataset.addSeries(xySeries);
        }


        return dataset;

    }

    private XYSeries createPotentialWell(double a){
        XYSeries xySeries = new XYSeries(String.valueOf("Potential well"), true, true);

        for (double x = -1 * a - 10; x < a + 10; x += 0.01) {
            if (x <= -a / 2) {
                xySeries.add(x, 0);
            } else if (x >= a / 2) {
                xySeries.add(x, 0);
            } else {
                xySeries.add(x, -1);
            }
        }
        return xySeries;
    }

    private void searchRoots() {
        double left = 0;
        double right = 0;
        double mid = (left + right) / 2.0;
        boolean symm = true;

        double a = defineTable.get("A");
        double b = defineTable.get("B");
        double U = defineTable.get("U");
        double h = defineTable.get("H");
        double m = defineTable.get("M");

        for (int i = 0; i < SIZE_BOUNDS - 1; ++i) {
            left = bounds.get(i);
            right = bounds.get(i + 1);
            while (Math.abs(right - left) > e) {

                mid = (left + right) / 2.0;

                if ((f(mid, m, a, h, U, symm) * f(left, m, a, h, U, symm)) < 0) {
                    right = mid;
                } else if ((f(mid, m, a, h, U, symm) * f(right, m, a, h, U, symm)) < 0) {
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
                logger.log(Level.INFO, "New Root:E=" + root.getE() + "\nk1=" + root.getK1()
                        + "\nk2=" + root.getK2() + "\nC=" + root.getC());
            }

        }
    }

    private double f(double E, double m, double a, double h, double U, boolean symm) {
        if (symm)
            return Math.tan(Math.sqrt(2 * m * (E + U)) * a / (2 * h)) - 1 / Math.sqrt((U / Math.abs(E)) - 1);
        else
            return -1 / Math.tan((Math.sqrt(2 * m * (E + U)) * a) / (2 * h)) - 1 / Math.sqrt((U / Math.abs(E)) - 1);
    }

    public void putInDefineTable(String param, Double value) {
        defineTable.put(param, value);
    }
}
