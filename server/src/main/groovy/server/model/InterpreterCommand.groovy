package server.model

import com.fasterxml.jackson.annotation.JsonValue
import lang.interpreter.Interpreter
import server.service.VariableBindingService

interface InterpreterCommand<T> {
    void execute(VariableBindingService variableBindingService);
    T getValue();
}
