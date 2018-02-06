package integratingGroovy.groovyshell

abstract class MyScript extends Script{
    String name
    def greet(){
        "Hello, $name"
    }
}
