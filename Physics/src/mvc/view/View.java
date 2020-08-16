package mvc.view;

import mvc.controller.Controller;
import mvc.model.Model;
import mvc.model.Observer;
import mvc.view.gui.SliderParametrGUI;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.logging.Logger;

public class View implements Observer {
    private static final Logger logger = Logger.getLogger(View.class.getName());

    private Model model;
    private Controller controller;

    private final int FRAME_WIDTH = 1200;
    private final int FRAME_HIGTH = 900;

    private JFrame appFrame;
    private JFreeChart lineChart;
    private ChartPanel chartPanel;
    private LinkedList<SliderParametrGUI> sliderParametrGUIS = new LinkedList<>();

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

        JPanel mainPanel = new JPanel(new BorderLayout());
        lineChart = ChartFactory.createXYLineChart("Physics",
                "X", "Y",
                new XYSeriesCollection(),
                PlotOrientation.VERTICAL,
                true, true, false);

        chartPanel = new ChartPanel(lineChart);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        SliderParametrGUI sliderParametrAGUI = new SliderParametrGUI("A", 0, 70, 1, 15);
        SliderParametrGUI sliderParametrBGUI = new SliderParametrGUI("B", 0, 1, 1, 0.2);
        SliderParametrGUI sliderParametrHGUI = new SliderParametrGUI("H", 0, 10, 1, 1);
        SliderParametrGUI sliderParametrMGUI = new SliderParametrGUI("M", 0, 10, 1, 1);
        SliderParametrGUI sliderParametrUGUI = new SliderParametrGUI("U", 0, 10, 1, 1);

        sliderParametrGUIS.add(sliderParametrAGUI);
        sliderParametrGUIS.add(sliderParametrBGUI);
        sliderParametrGUIS.add(sliderParametrHGUI);
        sliderParametrGUIS.add(sliderParametrMGUI);
        sliderParametrGUIS.add(sliderParametrUGUI);

        for (SliderParametrGUI slider : sliderParametrGUIS) {
            slider.getCurValue().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        controller.update(slider.getNameParam(), Double.parseDouble(slider.getCurValue().getText()));

                    } catch (Exception ex) {

                    }
                }
            });
            slider.getSlider().addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    try {
                        controller.update(slider.getNameParam(), Double.parseDouble(slider.getCurValue().getText()));
                    } catch (Exception ex) {

                    }
                }
            });

            leftPanel.add(slider);
        }

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(chartPanel, BorderLayout.CENTER);

        appFrame.getContentPane().add(mainPanel);
        appFrame.pack();
        appFrame.setVisible(true);
    }

    public void createView() {
        EventQueue.invokeLater(this::setSwingSettings);
    }

    @Override
    public void updateView(XYSeriesCollection collection) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                lineChart = ChartFactory.createXYLineChart("Physics",
                        "X", "Y",
                        collection,
                        PlotOrientation.VERTICAL,
                        true, true, false);
                chartPanel.setChart(lineChart);
                Toolkit.getDefaultToolkit().sync();
            }
        });

    }
}
