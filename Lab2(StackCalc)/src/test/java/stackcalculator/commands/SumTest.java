package stackcalculator.commands;

import org.junit.jupiter.api.Test;
import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumTest {

    @Test
    void execute() throws StackCalculatorExceptions {
        Context context= new Context();
        Sum sum=new Sum();
        context.push(5.0d);
        context.push(4.0d);
        context.push(1.0d);
        sum.execute(context,new String[0]);
        assertEquals(context.peek(),5.0,0.00000001);
        sum.execute(context,new String[0]);
        assertEquals(context.peek(),10.0,0.00000001);
    }
}