package gettingStarted.testAssistance

import groovy.transform.ASTTest
import groovy.transform.Memoized
import groovy.transform.NotYetImplemented
import org.codehaus.groovy.ast.ClassNode

import static org.codehaus.groovy.control.CompilePhase.*

class Maths {
    @Memoized
    static int fib(int n){
        (n==1||n==2)?1:fib(n-1)+fib(n-2)
    }
}

class MathsTest extends GroovyTestCase {

//    @NotYetImplemented
    void testFib(){
        def dataTable = [
                1:1,
                2:1,
                3:2,
                4:3,
                5:5,
                6:8,
                7:13
        ]
        dataTable.each {i,r->
            assert Maths.fib(i)==r
        }
    }
}

