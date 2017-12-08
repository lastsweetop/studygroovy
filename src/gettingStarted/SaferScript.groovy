package gettingStarted.safeScript

import groovy.transform.ThreadInterrupt
import groovy.transform.TimedInterrupt
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer

import java.util.concurrent.TimeUnit

/**
 * safer scripting
 */
def config = new CompilerConfiguration()
config.addCompilationCustomizers(new ASTTransformationCustomizer(ThreadInterrupt))
def bind = new Binding(i:0)
def shell = new GroovyShell(bind, config)
def userCode="""
while (true) {
    i++
}
"""
def t = Thread.start {
    shell.evaluate(userCode)
}
t.join(3000) // give at most 1000ms for the script to complete
if (t.alive) {
    t.interrupt()
}

@TimedInterrupt(value = 500L,unit = TimeUnit.MILLISECONDS,applyToAllClasses = false)
class Slow {
    def fib(n) {
        n<2?n:fib(n-1)+fib(n-2)
    }
}
def result
def t1 = Thread.start {
    result = new Slow().fib(500)
}
t1.join(5000)
assert result == null
assert t1.alive == false



