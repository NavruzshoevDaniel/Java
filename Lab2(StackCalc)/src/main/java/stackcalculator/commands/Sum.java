package stackcalculator.commands;

import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackAmountElements;
import stackcalculator.exceptions.StackCalculatorExceptions;
import stackcalculator.exceptions.WrongNumberOfArguments;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Sum implements ICommand {
    private final static Logger logger = Logger.getLogger(Sum.class.getName());
    @Override
    public void execute(Context context, String[] args) throws StackCalculatorExceptions {
        if (args == null || args.length != 0) throw new WrongNumberOfArguments("WrongArguments exceptions for Sum");

        if (context.getStackSize() < 2) throw new StackAmountElements("The stack doesn't have the right amount for Sum");
        double slag1 = context.pop();
        double slag2 = context.pop();
        context.push(slag1 + slag2);
        logger.log(Level.FINE,"Successfully did operation {0}", this.getClass().getName());
    }
}
