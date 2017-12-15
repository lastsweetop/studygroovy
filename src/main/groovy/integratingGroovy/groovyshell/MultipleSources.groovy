package integratingGroovy.groovyshell


//Multiple Sources
def shell = new GroovyShell()
def script = shell.evaluate """3*5"""
def script1 = shell.evaluate new StringReader('3*5')
assert script == script1

def script2 = shell.parse '3*5'
assert script2 instanceof Script
assert script2.run() == 15