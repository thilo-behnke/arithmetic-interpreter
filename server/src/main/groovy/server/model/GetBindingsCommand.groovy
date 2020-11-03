package server.model

import server.service.VariableBindingService

class GetBindingsCommand implements InterpreterCommand<Set<Map.Entry<String, Double>>> {
    private bindings

    @Override
    void execute(VariableBindingService variableBindingService) {
        bindings = variableBindingService.getBindings().collectEntries {[it.varName, it.value]}
    }

    @Override
    Set<Map.Entry<String, Double>> getValue() {
        return bindings.entrySet()
    }
}
