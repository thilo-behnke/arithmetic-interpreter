package server.model;

import grails.gorm.annotation.Entity;
import org.bson.types.ObjectId;

@Entity
class VariableBinding {
    ObjectId id

    String varName
    Double value

    static mapping = {
        collection "binding"
    }

    static constraints = {
        varName nullable:false, blank:false
        value nullable:false
    }
}
