package stackcalculator.exceptions;

public class WrongNumberOfArguments extends StackCalculatorExceptions {
    public WrongNumberOfArguments(String string){
        super(string);
    }

    public WrongNumberOfArguments() {
        super();
    }

    public WrongNumberOfArguments(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongNumberOfArguments(Throwable cause) {
        super(cause);
    }
}
