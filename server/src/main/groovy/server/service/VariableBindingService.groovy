package server.service

import grails.gorm.transactions.Transactional
import server.model.VariableBinding

import javax.inject.Singleton

@Singleton
class VariableBindingService {

    Set<VariableBinding> saveBindings(Map<String, Double> bindings) {
        bindings.entrySet().collect {saveBinding(it.key, it.value)}
    }

    @Transactional
    VariableBinding saveBinding(String varName, Double value) {
        def varBinding = VariableBinding.findByVarName(varName)
        if (varBinding) {
            varBinding.setValue(value)
        } else {
            varBinding = new VariableBinding(varName: varName, value: value)
        }
        varBinding.save()
    }

    Set<VariableBinding> getBindings() {
        return VariableBinding.findAll()
    }

    void clearBindings() {
        VariableBinding.where{}.deleteAll()
    }
}
