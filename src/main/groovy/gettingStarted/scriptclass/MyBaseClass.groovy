package gettingStarted.scriptclass

import org.codehaus.groovy.control.CompilerConfiguration

abstract class MyBaseClass extends Script{
    int count
    String name
    public void greet(){
        println "hello $name"
    }

    abstract void scriptBody()

    def run(){
        count++
        scriptBody()
        count
    }
}
