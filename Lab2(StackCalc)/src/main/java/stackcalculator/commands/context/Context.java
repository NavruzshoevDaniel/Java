package stackcalculator.commands.context;

import java.util.HashMap;
import java.util.Stack;

public class Context {
    private Stack<Double> stack=new Stack<>();
    private HashMap<String,Double> defineTable=new HashMap<>();

    public Stack<Double> getStack() {
        return stack;
    }

    public HashMap<String, Double> getDefineTable() {
        return defineTable;
    }

}
