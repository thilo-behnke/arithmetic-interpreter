package server.model

import lang.interpreter.Interpreter
import server.service.VariableBindingService

import javax.inject.Inject

class ClearBindingsCommand implements InterpreterCommand<ClearBindingsDTO> {

    @Override
    void execute(VariableBindingService variableBindingService) {
        variableBindingService.clearBindings()
    }

    @Override
    ClearBindingsDTO getValue() {
        return new ClearBindingsDTO(message: 'Clear Successful')
    }
}

class ClearBindingsDTO {
    String message
}
