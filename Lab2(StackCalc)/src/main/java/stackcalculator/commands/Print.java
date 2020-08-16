package stackcalculator.commands;

import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;
import stackcalculator.exceptions.WrongNumberOfArguments;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Print implements ICommand {
    private final static Logger logger = Logger.getLogger(Print.class.getName());
    @Override
    public void execute(Context context, String[] args) throws StackCalculatorExceptions {
        if (args == null || args.length != 0)
            throw new WrongNumberOfArguments("WrongArguments exceptions for Print");

        System.out.println(context.peek());
        logger.log(Level.FINE,"Successfully did operation {0}", this.getClass().getName());
    }
}
