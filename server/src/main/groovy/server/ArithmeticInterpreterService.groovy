package server

import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import lang.interpreter.Interpreter
import lang.lexer.Lexer
import lang.parser.Parser
import lang.parser.ast.ASTNode
import server.dto.InterpreterResultDTO
import server.model.ClearBindingsCommand
import server.model.CommandType
import server.model.GetBindingsCommand
import server.model.InterpreterCommand
import server.model.VariableBinding
import server.service.VariableBindingService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArithmeticInterpreterService {

    @Inject
    private VariableBindingService variableBindingService

    private Interpreter interpreter

    @EventListener
    void onStartup(StartupEvent startupEvent) {
        def bindings = getBindings()
        interpreter = new Interpreter(new Parser(new Lexer()), bindings)
    }

    InterpreterResultDTO evaluateExpression(String expression) {
        def bindings = getBindings();
        interpreter.setBindings(bindings)
        interpreter.setExpression(expression)
        double result = interpreter.eval()
        ASTNode ast = interpreter.getAst()

        bindings = interpreter.bindings()
        variableBindingService.saveBindings(bindings)

        return new InterpreterResultDTO(result, ast, bindings.entrySet())
    }

    InterpreterCommand executeCommand(String commandName) {
        def command
        if (commandName == CommandType.GET_BINDINGS.name()) {
            command = new GetBindingsCommand();
        } else if (commandName == CommandType.CLEAR_BINDINGS.name()) {
            command = new ClearBindingsCommand();
        } else {
            throw new IllegalArgumentException("Received unknown command: ${commandName}")
        }
        command.execute(variableBindingService);
        return command
    }

    private getBindings() {
        variableBindingService.getBindings().<String, Double, VariableBinding>collectEntries{[it.varName, it.value]}
    }
}
