package gettingStarted.scriptclass

import gettingStarted.delegatesTo.EmailSpec
@BaseScript(MyBaseClass)
import groovy.transform.BaseScript
import org.codehaus.groovy.control.CompilerConfiguration

setName 'Judith'
greet()

def config = new CompilerConfiguration()
config.scriptBaseClass = 'MyBaseClass'
def shell = new GroovyShell(this.class.classLoader, config)
def result=shell.evaluate """
    setName 'Judith'                                                    
    greet()
"""

assert result == 1

def script = shell.parse("println 'Ok'")
assert script.run() == 1
assert script.run() == 2


