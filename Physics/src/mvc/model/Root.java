package mvc.model;

public class Root {
    private double E;
    private double k1;
    private double k2;
    private double C;

    public Root(double e, double m, double U, double h, double B, double a, boolean symm) {
        this.E = e;
        init(m, U, h, B, a, symm);
    }

    private void init(double m, double U, double h, double B, double a, boolean symm) {
        k1 = Math.sqrt((2 * m * (E + U)) / h);
        k2 = Math.sqrt(2 * m * Math.abs(E));
        if (symm) {
            C = B * Math.cos((k1 * a) / 2) * Math.exp((k2 * a) / 2);
        } else {
            C = B * Math.sin((k1 * a) / 2) * Math.exp((k2 * a) / 2);
        }

    }


    public double getE() {
        return E;
    }

    public double getK1() {
        return k1;
    }

    public double getK2() {
        return k2;
    }

    public double getC() {
        return C;
    }
}
