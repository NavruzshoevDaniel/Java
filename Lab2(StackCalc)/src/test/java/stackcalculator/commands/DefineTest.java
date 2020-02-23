package stackcalculator.commands;

import org.junit.jupiter.api.Test;
import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;

import static org.junit.jupiter.api.Assertions.*;

class DefineTest {

    @Test
    void execute() throws StackCalculatorExceptions {
        Context context= new Context();
        Define define=new Define();
        String[] args=new String[2];
        args[0]="a";
        args[1]="4";

        define.execute(context,args);
        assertEquals(context.getDefineTable().get("a"),4.0,0.00000001);
        args[0]="b";
        args[1]="4";
        define.execute(context,args);
        assertEquals(context.getDefineTable().get("a"),4.0,0.00000001);

    }
}