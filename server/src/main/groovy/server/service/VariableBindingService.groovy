package server.service

import grails.gorm.transactions.Transactional
import server.model.VariableBinding

import javax.inject.Singleton

@Singleton
class VariableBindingService {

    @Transactional
    Set<VariableBinding> saveBindings(Map<String, Double> bindings) {
        def mappedBindings = bindings.entrySet().collect {mapToBinding(it.key, it.value)}
        VariableBinding.saveAll(mappedBindings)
        return mappedBindings
    }

    private VariableBinding mapToBinding(String varName, Double value) {
        def varBinding = VariableBinding.findByVarName(varName)
        if (varBinding) {
            varBinding.setValue(value)
        } else {
            varBinding = new VariableBinding(varName: varName, value: value)
        }
        return varBinding
    }

    Set<VariableBinding> getBindings() {
        return VariableBinding.findAll()
    }

    void clearBindings() {
        VariableBinding.where{}.deleteAll()
    }
}
