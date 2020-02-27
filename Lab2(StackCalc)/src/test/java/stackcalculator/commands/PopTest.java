package stackcalculator.commands;

import org.junit.jupiter.api.Test;
import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;

import static org.junit.jupiter.api.Assertions.*;

class PopTest {

    @Test
    void execute() throws StackCalculatorExceptions {
        Context context= new Context();
        Pop pop=new Pop();
        context.push(5.0d);
        context.push(4.0d);
        context.push(1.0d);
        pop.execute(context,new String[0]);
        assertEquals(context.peek(),4,0.00000001);
        pop.execute(context,new String[0]);
        assertEquals(context.peek(),5,0.00000001);
    }
}