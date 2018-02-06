package integratingGroovy.groovyScriptEngine

import groovy.util.logging.Log

def dir = new File("/Users/apple/Documents/mydream/groovy/studygroovy/src/main/groovy/integratingGroovy/groovyScriptEngine/")
def bind = new Binding()
def engine = new GroovyScriptEngine([dir.toURI().toURL()] as URL[])

while (true){
    def greeter=engine.run('ReloadingTest.groovy',bind)
    println greeter.sayHello()
    sleep(1000)
}
