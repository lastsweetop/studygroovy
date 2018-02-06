package integratingGroovy.groovyScriptEngine

import com.google.gson.Gson

class Greeter {
    String sayHello() {
        Gson gson=new Gson()
        def greet = new Dependency().message
        greet
    }
}

new Greeter()