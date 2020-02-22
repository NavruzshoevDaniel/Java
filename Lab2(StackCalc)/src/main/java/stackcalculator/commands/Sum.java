package stackcalculator.commands;

import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackAmountElements;
import stackcalculator.exceptions.StackCalculatorExceptions;
import stackcalculator.exceptions.WrongNumberOfArguments;

import java.util.Stack;

public class Sum implements ICommand {

    @Override
    public void execute(Context context, String[] args) throws StackCalculatorExceptions {
        if (args == null || args.length != 0) throw new WrongNumberOfArguments("WrongArguments exceptions for Sum");
        Stack<Double> stack = context.getStack();
        if (stack.size() < 2) throw new StackAmountElements("The stack doesn't have the right amount for Sum");
        double slag1 = stack.pop();
        double slag2 = stack.pop();
        stack.push(slag1 + slag2);
    }
}
