package stackcalculator.commands;

import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;
import stackcalculator.exceptions.WrongNumberOfArguments;

import java.util.Stack;

public class Push implements ICommand {


    @Override
    public void execute(Context context, String[] args) throws StackCalculatorExceptions {
        if (args == null || args.length != 1)
            throw new WrongNumberOfArguments("WrongArguments exceptions for PUSH");

        Stack<Double> stack = context.getStack();

        if(ICommand.isNumeric(args[0])){
            stack.push(Double.valueOf(args[0]));
        } else {
            Double defineValue=context.getDefineTable().get(args[0]);
            if(defineValue==null)
                throw new WrongNumberOfArguments("WrongArguments exceptions for PUSH");
            else {
                stack.push(defineValue);
            }

        }
    }
}
