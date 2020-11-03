package lang;

import lang.interpreter.Interpreter;
import lang.lexer.Lexer;
import lang.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Calc {

    Interpreter interpreter;

    public Calc() {
        interpreter = new Interpreter(new Parser(new Lexer()), new LinkedHashMap<>());
    }
    public double eval(String expr) {
        interpreter.setExpression(expr);
        return interpreter.eval();
    }

    public Map<String, Double> bindings() {
        return interpreter.bindings();
    }
}
