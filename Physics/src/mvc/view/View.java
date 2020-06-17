package mvc.view;

import mvc.controller.Controller;
import mvc.model.Root;
import mvc.model.Model;
import mvc.model.Observer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.logging.Logger;

public class View implements Observer {
    private static final Logger logger = Logger.getLogger(View.class.getName());

    private Model model;
    private Controller controller;

    private final int FRAME_WIDTH = 1200;
    private final int FRAME_HIGTH = 900;

    private JFrame appFrame;
    private JTextPane info;
    private JPanel graph;
    private JSlider sliderA;
    private JTextArea b;
    private JButton button;
    private JFreeChart lineChart;
    private ChartPanel chartPanel;

    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        model.registerObserver(this);
    }

    private void setSwingSettings() {
        appFrame = new JFrame();
        appFrame.setSize(FRAME_WIDTH, FRAME_HIGTH);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setLocationRelativeTo(null);

        info = new JTextPane();

        sliderA = new JSlider(0, 70, 15);
        sliderA.setPaintLabels(true);
        sliderA.setMajorTickSpacing(10);

        b = new JTextArea("0.2");
        b.setLineWrap(true);
        b.setEditable(true);
        b.setToolTipText("B=");

        button = new JButton("Update");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.update(sliderA.getValue(), b.getText());
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        lineChart = ChartFactory.createXYLineChart("Physics",
                "X", "Y",
                new XYSeriesCollection(),
                PlotOrientation.VERTICAL,
                true, true, false);

        chartPanel = new ChartPanel(lineChart);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(sliderA);
        bottomPanel.add(b);
        bottomPanel.add(button);
        bottomPanel.setToolTipText("A=");

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(chartPanel, BorderLayout.CENTER);


        appFrame.getContentPane().add(mainPanel);
        appFrame.pack();
        appFrame.setVisible(true);
    }

    public void createView() {
        EventQueue.invokeLater(this::setSwingSettings);
    }

    private XYSeriesCollection createDataset(ArrayList<Root> roots) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < roots.size(); i++) {

            XYSeries xySeries = new XYSeries(String.valueOf(i), true, true);
            Root root = roots.get(i);
            for (double x = -1 * model.getA() - 10; x < model.getA() + 10; x += 0.01) {
                if (i % 2 == 0) {
                    if (x <= -model.getA() / 2) {
                        xySeries.add(x, root.getC() * Math.exp(root.getK2() * x) - root.getE());
                    } else if (x >= model.getA() / 2) {
                        xySeries.add(x, root.getC() * Math.exp(-root.getK2() * x) - root.getE());
                    } else {
                        xySeries.add(x, model.getB() * Math.cos(root.getK1() * x) - root.getE());
                    }
                } else {
                    if (x <= -model.getA() / 2) {
                        xySeries.add(x, -root.getC() * Math.exp(root.getK2() * x) - root.getE());
                    } else if (x >= model.getA() / 2) {
                        xySeries.add(x, root.getC() * Math.exp(-root.getK2() * x) - root.getE());
                    } else {
                        xySeries.add(x, model.getB() * Math.sin(root.getK1() * x) - root.getE());
                    }
                }
            }

            dataset.addSeries(xySeries);
        }

        return dataset;
    }


    private XYSeriesCollection createData(ArrayList<Root> roots) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Sine", true, true);

        for (int i = 0; i < roots.size(); i++) {

            XYSeries xySeries = new XYSeries(String.valueOf(i), true, true);
            Root root = roots.get(i);
            for (double x = -1 * model.getA() - 10; x < model.getA() + 10; x += 0.01) {
                if (i % 2 == 0) {
                    if (x <= -model.getA() / 2) {
                        series.add(i, Math.exp( i));
                    } else if (x >= model.getA() / 2) {
                        series.add(i,  Math.exp(  i));
                    } else {
                        series.add(i, Math.cos(i) );
                    }
                } else {
                    if (x <= -model.getA() / 2) {
                        series.add(i, -root.getC() * Math.exp(root.getK2() * i) - root.getE());
                    } else if (x >= model.getA() / 2) {
                        series.add(i, root.getC() * Math.exp(-root.getK2() * i) - root.getE());
                    } else {
                        series.add(i, model.getB() * Math.sin(root.getK1() * i) - root.getE());
                    }
                }
            }

            dataset.addSeries(xySeries);
        }

        return dataset;
    }
    @Override
    public void updateView(ArrayList<Root> roots) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                lineChart = ChartFactory.createXYLineChart("Physics",
                        "X", "Y",
                        createDataset(roots),
                        PlotOrientation.VERTICAL,
                        true, true, false);
chartPanel.setChart(lineChart);
            }
        });

    }

}
