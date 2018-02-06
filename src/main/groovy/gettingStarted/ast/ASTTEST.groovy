package gettingStarted.ast

import groovy.transform.ASTTest
import org.codehaus.groovy.ast.ClassNode

import static org.codehaus.groovy.control.CompilePhase.CONVERSION

@ASTTest(phase=CONVERSION, value={
    assert 1 == 2
    assert node instanceof ClassNode
    node.name = 'Person1'
})
class Person {
    void say(){
        println 'say'
    }
}

new gettingStarted.compileTime.Person().say()