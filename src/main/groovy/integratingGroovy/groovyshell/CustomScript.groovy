package integratingGroovy.groovyshell

import org.codehaus.groovy.control.CompilerConfiguration

def config = new CompilerConfiguration()
config.scriptBaseClass = 'integratingGroovy.groovyshell.MyScript'

def shell = new GroovyShell(this.class.classLoader,new Binding(),config)
def script = shell.parse('greet()')

assert script instanceof MyScript
script.setName('sweetop')
assert script.run() == 'Hello, sweetop'