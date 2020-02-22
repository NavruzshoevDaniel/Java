package stackcalculator.commands;

import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackAmountElements;
import stackcalculator.exceptions.StackCalculatorExceptions;
import stackcalculator.exceptions.WrongNumberOfArguments;

import java.util.Stack;

public class Sub implements ICommand {

    @Override
    public void execute(Context context, String[] args) throws StackCalculatorExceptions {
        if (args == null || args.length != 0)
            throw new WrongNumberOfArguments("WrongArguments exceptions for Sub");
        Stack<Double> stack = context.getStack();
        if (stack.size() < 2) throw new StackAmountElements("The stack doesn't have the right amount for Sub");
        double number1 = stack.pop();
        double number2 = stack.pop();
        stack.push(number1 - number2);
    }
}
