package lang.utils

import spock.lang.Specification

class UtilsTest extends Specification {

    def 'should explode non empty string correctly'() {
        given:
        def string = 'splitme'
        when:
        def result = Utils.explode(string)
        then:
        result == ['s', 'p', 'l', 'i', 't', 'm', 'e']
    }

    def 'should return an empty list for an empty string'() {
        given:
        def string = ''
        when:
        def result = Utils.explode(string)
        then:
        result == []
    }

}
