package stackcalculator;

import stackcalculator.commands.ICommand;
import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StackCalculator {
    private static Logger logger = Logger.getLogger(StackCalculator.class.getName());

    private void commandExecuting(ICommand command, Context context, String[] args, String commandName) {
        if (command != null) {
            logger.log(Level.FINE, "The program started executing the command:{0}",
                    command.getClass().getName());
            try {
                command.execute(context, args);
            } catch (StackCalculatorExceptions e) {
                logger.log(Level.WARNING, "Stack Calculator Exceptions:", e);
            }

        } else {
            logger.log(Level.WARNING, "No such command: {0}", commandName);
        }
    }

    public void calculate(InputStream input) {
        BufferedReader bufferedReader = null;
        ICommand command = null;
        Context context = new Context();

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(input));
            logger.log(Level.FINE, "BufferedReader was created");
            String line;
            String[] words;
            while ((line = bufferedReader.readLine()) != null && (!line.equals("END"))) {
                if (line.length()!=0 && line.charAt(0) != '#') {
                    words = line.split(" ");
                    String[] args = new String[words.length - 1];
                    String commandName = words[0];
                    System.arraycopy(words, 1, args, 0, words.length - 1);
                    command = Factory.getInstance().createCommand(commandName);
                    commandExecuting(command,context,args,commandName);
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "IOException:", e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    logger.log(Level.FINE, "BufferedReader was closed");
                }

            } catch (IOException ex) {
                logger.log(Level.WARNING, "The program couldn't close BufferedReader");
            }

        }
    }

}
