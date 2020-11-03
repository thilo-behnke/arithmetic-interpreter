package lang.utils

import spock.lang.Specification

class RegexUtilsTest extends Specification {

    def 'matchNumber should return true for a single number digit'() {
        given:
        def string = '7'
        when:
        def result = RegexUtils.matchNumber(string)
        then:
        result
    }

    def 'matchNumber should return false for any other single digit string'() {
        given:
        def string = 's'
        when:
        def result = RegexUtils.matchNumber(string)
        then:
        !result
    }

    def 'matchOperator should return true for an arithmetic operator'() {
        expect:
        RegexUtils.matchOperator(operator)
        where:
        operator | _
        '+' | _
        '-' | _
        '*' | _
        '/' | _
    }
}
