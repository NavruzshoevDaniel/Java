package mvc.view.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SliderParametrGUI extends JPanel {
    private final String nameParam;
    private JLabel nameParamLabel;
    private JSlider slider;
    private final JLabel leftBracket = new JLabel("[");
    private final JLabel rightBracket = new JLabel("]");
    private final JLabel delimiter = new JLabel(";");
    private final JLabel stepLabel = new JLabel(" Шаг:");
    private final JLabel curValueLabel = new JLabel("Значение");
    private JTextField left;
    private JTextField right;
    private JTextField stepText;
    private JTextField curValue;

    private double value = 15;


    public SliderParametrGUI(String name, int min, int max, int step, double cur) {
        nameParam = name;
        setSettings(name, min, max, step, cur);
    }

    private void setSettings(String name, int min, int max, int step, double cur) {
        nameParamLabel = new JLabel(name);
        left = new JTextField(String.valueOf(min), 5);
        right = new JTextField(String.valueOf(max), 5);
        stepText = new JTextField(String.valueOf(step), 3);
        slider = new JSlider(min, max, (int) cur);
        curValue = new JTextField(String.valueOf(cur));

        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPreferredSize(new Dimension(slider.getPreferredSize().width + 200,
                slider.getPreferredSize().height + 10));

        this.setLayout(new GridBagLayout());

        setGridGUILocation();
        setListeners();
    }

    private void setListeners() {
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateSlider();
            }
        });
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLeftBracket();
                curValue.setText(String.valueOf(slider.getValue()));
            }
        });
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRightBracket();
                curValue.setText(String.valueOf(slider.getValue()));
            }
        });
        stepText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStep();
                curValue.setText(String.valueOf(slider.getValue()));
            }
        });

        curValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCurValue();
            }
        });
    }

    private void updateLeftBracket() {

        try {
            if (checkBounds()) {
                slider.setMinimum(Integer.parseInt(left.getText()));
            }
        } catch (Exception e) {

        }
    }

    private void updateSlider() {

        try {
            value = Double.parseDouble(curValue.getText());

            if (Math.abs(Math.round(value) - slider.getValue()) != 0) {
                curValue.setText(String.valueOf(slider.getValue()));
            }

        } catch (Exception e) {

        }

    }

    private void updateCurValue() {

        try {
            value = Double.parseDouble(curValue.getText());
            slider.setValue((int) value);

        } catch (Exception e) {

        }

    }

    private void updateRightBracket() {

        try {
            if (checkBounds()) {
                slider.setMaximum(Integer.parseInt(right.getText()));

            }
        } catch (Exception e) {

        }

    }

    private void updateStep() {

        try {
            slider.setMinorTickSpacing(Integer.parseInt(stepText.getText()));
        } catch (Exception e) {

        }

    }

    private void setGridGUILocation() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        this.add(nameParamLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        this.add(slider, gridBagConstraints);

        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(leftBracket, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.05;
        this.add(left, gridBagConstraints);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.weightx = 0.0;
        this.add(delimiter, gridBagConstraints);
        gridBagConstraints.gridx = 3;
        gridBagConstraints.weightx = 0.05;
        this.add(right, gridBagConstraints);
        gridBagConstraints.gridx = 4;
        gridBagConstraints.weightx = 0.0;
        this.add(rightBracket, gridBagConstraints);
        gridBagConstraints.weightx = 0.05;
        gridBagConstraints.gridx = 5;
        this.add(stepLabel, gridBagConstraints);
        gridBagConstraints.gridx = 6;
        gridBagConstraints.weightx = 0.05;
        this.add(stepText, gridBagConstraints);
        gridBagConstraints.gridx = 7;
        this.add(curValueLabel, gridBagConstraints);
        gridBagConstraints.gridx = 8;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.05;
        this.add(curValue, gridBagConstraints);

    }

    private boolean checkBounds() {
        int leftBound = Integer.parseInt(this.left.getText());
        int rightBound = Integer.parseInt(this.right.getText());
        if (leftBound > rightBound)
            return false;
        return true;
    }

    public double getValue() {
        return value;
    }

    public JTextField getCurValue() {
        return curValue;
    }

    public JSlider getSlider() {
        return slider;
    }

    public String getNameParam() {
        return nameParam;
    }
}
