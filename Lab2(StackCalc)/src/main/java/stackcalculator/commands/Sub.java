package stackcalculator.commands;

import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackAmountElements;
import stackcalculator.exceptions.StackCalculatorExceptions;
import stackcalculator.exceptions.WrongNumberOfArguments;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sub implements ICommand {
    private final static Logger logger = Logger.getLogger(Sub.class.getName());
    @Override
    public void execute(Context context, String[] args) throws StackCalculatorExceptions {
        if (args == null || args.length != 0)
            throw new WrongNumberOfArguments("WrongArguments exceptions for Sub");
        Stack<Double> stack = context.getStack();
        if (stack.size() < 2) throw new StackAmountElements("The stack doesn't have the right amount for Sub");
        double number1 = stack.pop();
        double number2 = stack.pop();
        stack.push(number1 - number2);
        logger.log(Level.FINE,"Successfully did operation {0}", this.getClass().getName());
    }
}
