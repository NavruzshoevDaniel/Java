package stackcalculator.exceptions;

public class StackCalculatorExceptions extends Exception {
    public StackCalculatorExceptions(String string){
        super(string);
    }

    public StackCalculatorExceptions() {
    }

    public StackCalculatorExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public StackCalculatorExceptions(Throwable cause) {
        super(cause);
    }

}
