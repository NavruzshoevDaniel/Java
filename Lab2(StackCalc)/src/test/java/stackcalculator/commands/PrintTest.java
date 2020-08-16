package stackcalculator.commands;

import org.junit.jupiter.api.Test;
import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintTest {

    @Test
    void execute() throws StackCalculatorExceptions {
        Context context= new Context();
        Print print=new Print();
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        context.push(5.0d);
        print.execute(context,new String[0]);
        Double actual=Double.valueOf(out.toString());
        assertEquals(context.peek(),actual,0.00000001);


    }
}