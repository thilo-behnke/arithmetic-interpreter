package lang

import lang.exception.SyntaxErrorException
import spock.lang.Specification

class CalcTest extends Specification {

    Calc calc

    void setup() {
        calc = new Calc()
    }

    def 'just a number'() {
        given:
        def digit = "55"
        when:
        def result = calc.eval(digit)
        then:
        result == 55d
    }

    def 'just a number in brackets'() {
        given:
        def digit = "(55)"
        when:
        def result = calc.eval(digit)
        then:
        result == 55d
    }

    def 'just a number in double brackets'() {
        given:
        def digit = "((55))"
        when:
        def result = calc.eval(digit)
        then:
        result == 55d
    }

    def 'just an identifier'() {
        given:
        def identifier = "test"
        def bindingValue = 10d
        calc.bindings().put(identifier, bindingValue)
        when:
        def result = calc.eval(identifier)
        then:
        result == 10d
    }

    def 'just an identifier in brackets'() {
        given:
        def identifier = "(test)"
        def bindingValue = 10d
        calc.bindings().put("test", bindingValue)
        when:
        def result = calc.eval(identifier)
        then:
        result == 10d
    }

    def 'just an identifier in double brackets'() {
        given:
        def identifier = "((test))"
        def bindingValue = 10d
        calc.bindings().put("test", bindingValue)
        when:
        def result = calc.eval(identifier)
        then:
        result == 10d
    }

    def '0 is a valid number'() {
        given:
        def number = "0"
        when:
        def result = calc.eval(number)
        then:
        result == 0
    }

    def '0.0 is a valid number'() {
        given:
        def number = "0.0"
        when:
        def result = calc.eval(number)
        then:
        result == 0
    }

    def 'number must not start with 0'() {
        given:
        def number = "09"
        when:
        calc.eval(number)
        then:
        thrown(UnsupportedOperationException)
    }

    def 'single digit arithmetic operation'() {
        expect:
        def operation = left + operator + right
        result == calc.eval(operation)
        where:
        left | operator | right || result
        5    | '+'      | 4     || 9d
        5    | '-'      | 4     || 1d
        2    | '-'      | 4     || -2d
        5    | '*'      | 4     || 20d
        0    | '*'      | 4     || 0d
        8    | '/'      | 4     || 2d
        4    | '/'      | 8     || 0.5d
    }

    def 'multi digit arithmetic operation'() {
        expect:
        def operation = left + operator + right
        result == calc.eval(operation)
        where:
        left | operator | right || result
        50   | '+'      | 41    || 91d
        54   | '-'      | 4     || 50d
        20   | '-'      | 80    || -60d
        22   | '*'      | 4     || 88d
        80   | '/'      | 40    || 2d
        12   | '/'      | 24    || 0.5d
    }

    def 'double arithmetic operation'() {
        expect:
        def operation = left + operator + right
        result == calc.eval(operation)
        where:
        left  | operator | right || result
        2.5d  | '+'      | 2.2d  || 4.7d
        7.5d  | '-'      | 3.8d  || 3.7d
        70.1d | '*'      | 0.0d  || 0.0d
        10.5d | '/'      | 2.0d  || 5.25d
    }

    def 'simple arithmetic operation in parenthesis'() {
        expect:
        def operation = "(" + left + operator + right + ")"
        result == calc.eval(operation)
        where:
        left | operator | right || result
        5    | '+'      | 4     || 9d
        5    | '-'      | 4     || 1d
        2    | '-'      | 4     || -2d
        5    | '*'      | 4     || 20d
        0    | '*'      | 4     || 0d
        8    | '/'      | 4     || 2d
        4    | '/'      | 8     || 0.5d
    }

    def 'complex arithmetic operation in parenthesis'() {
        expect:
        result == calc.eval(operation);
        where:
        operation   || result
        '1+(2*6)'   || 13d
        '10+(12*6)' || 82d
        '10*(12-6)' || 60d
        '12/(12-6)' || 2d
    }

    def 'even more complex arithmetic operation in parenthesis'() {
        expect:
        result == calc.eval(operation);
        where:
        operation              || result
        '1+((2*(10/5))-(5*2))' || -5d
        '(7*5)-(3*(3*3))'      || 8d
    }

    def 'simple assignment'() {
        expect:
        def assignment = identifier + "=" + value
        value == calc.eval(assignment)
        calc.bindings().get(identifier) == value
        where:
        identifier | value
        'a'        | 5d
        'test'     | 10d
        'test1'    | 22.22d
    }

    def 'assignment to expression'() {
        expect:
        def assignment = identifier + "=" + value
        result == calc.eval(assignment)
        calc.bindings().get(identifier) == result
        where:
        identifier | value         || result
        'a'        | "(10+5)"      || 15d
        'test'     | "(10+(10*5))" || 60d
    }

    def 'use assigned value in arithmetic operation (left)'() {
        given:
        calc.bindings().put(identifier, bindingValue)
        expect:
        result == calc.eval(identifier + operation)
        where:
        identifier | operation | bindingValue || result
        'a'        | '+20'     | 10d          || 30d
    }

    def 'use assigned value in arithmetic operation (right)'() {
        given:
        calc.bindings().put(identifier, bindingValue)
        expect:
        result == calc.eval(operation + identifier)
        where:
        identifier | operation | bindingValue || result
        'a'        | '20+'     | 10d          || 30d
    }

    def 'use assigned value in arithmetic operation (both)'() {
        given:
        calc.bindings().put(identifierA, bindingValueA)
        calc.bindings().put(identifierB, bindingValueB)
        expect:
        result == calc.eval(identifierA + operation + identifierB)
        where:
        identifierA | identifierB | bindingValueA | bindingValueB | operation || result
        'a'         | 'bee'       | 20d           | 10d           | '+'       || 30d
    }

    def 'use value of assignment in arithmetic operation'() {
        expect:
        result == calc.eval(operation + "(" + identifier + "=" + bindingValue + ")")
        calc.bindings().get(identifier) == bindingValue
        where:
        identifier | bindingValue | operation || result
        'a'        | 10d          | "(20*5)+" || 110d
    }

    def 'use reserved function'() {
        expect:
        result == calc.eval(function + "(" + input + ")")
        where:
        function | input || result
        'sqrt'   | 4     || 2d
    }

    def 'expression with negative sign'() {
        expect:
        result == calc.eval("-" + expression)
        where:
        expression     || result
        '5'            || -5d
        '(5)'          || -5d
        '(-(5))'       || 5d
        '(-(-(5)))'    || -5d
        '(10+6)'       || -16d
        '(10+sqrt(9))' || -13d
        '(10-(-(5)))'  || -15d
    }

    def 'multiple arithmetic operations without parenthesis'() {
        expect:
        result == calc.eval(operation)
        where:
        operation       || result
        '5+10+20'       || 35d
        '5+(10)+20'     || 35d
        '(5)+(10)+(20)' || 35d
    }

    def 'multiple arithmetic operations without parenthesis - operator precedence'() {
        expect:
        result == calc.eval(operation)
        where:
        operation           || result
        '5+10*20'           || 205d
        '5+10*20/10'        || 25d
        '5+10*20/(10+10)'   || 15d
        '(5+10)*20/(30+10)' || 7.5d
        '450*363-78+4774*3' || 177594d
    }

    def 'divide by 0'() {
        given:
        def expression = '5/0'
        when:
        calc.eval(expression)
        then:
        thrown(ArithmeticException)
    }

    def 'should ignore whitespaces'() {
        expect:
        result == calc.eval(operation)
        where:
        operation                   || result
        '5+10 *   20'               || 205d
        '5  +10 *20/10'             || 25d
        '5 +10  *20/  (10+10)'      || 15d
        '(5+10)*20/(  30+10)'       || 7.5d
        '450  *  363-  78+  4774*3' || 177594d
        '450 '                      || 450d
    }

    def 'should throw exception if a number is followed by a number'() {
        given:
        def expression = '5 8'
        when:
        calc.eval(expression)
        then:
        thrown(SyntaxErrorException)
    }
}
