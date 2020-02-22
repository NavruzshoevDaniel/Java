package stackcalculator;

import javafx.util.Pair;
import stackcalculator.commands.ICommand;
import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;

import java.io.*;

public class StackCalculator {
    private InputStream input;

    public StackCalculator(InputStream input) {
        this.input = input;
    }

    public void calculate() {
        BufferedReader bufferedReader = null;
        ICommand command = null;
        Context context = new Context();

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(input));
            String line;
            String[] words;
            while ((line = bufferedReader.readLine()) != null && (!line.equals("END"))) {
                if (line.charAt(0) != '#') {
                    words = line.split(" ");
                    String[] args = new String[words.length - 1];
                    String commandName = words[0];
                    System.arraycopy(words, 1, args, 0, words.length - 1);
                    command = Factory.getInstance().createCommand(commandName);
                    if (command != null)
                        command.execute(context, args);
                }
            }
        } catch (IOException | StackCalculatorExceptions e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException ex) {
                System.err.format("IOException: %s%n", ex);
            }

        }
    }

}
