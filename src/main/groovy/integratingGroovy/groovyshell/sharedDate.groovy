package integratingGroovy.groovyshell


def shareDate = new Binding()
shareDate.setProperty('text', 'I am shared data!')
def now = new Date()
shareDate.setProperty('date', now)
def shell = new GroovyShell(shareDate)

def result = shell.evaluate '"At $date, $text"'
assert result == "At $now, I am shared data!"




shell.evaluate('foo=3')
assert shareDate.getProperty('foo') == 3


shell.evaluate('int bar=4')
try {
    assert shell.getProperty('bar')
} catch (MissingPropertyException e) {
    println e.message
}


def b1 = new Binding(x: 3)
def b2 = new Binding(x: 4)
def script = shell.parse("x*=2")
script.binding = b1
script.run()
script.binding = b2
script.run()
assert b1.getProperty('x') == 6
assert b2.getProperty('x') == 8
assert b1 != b2



b1 = new Binding(x: 3)
b2 = new Binding(x: 4)
script.binding = b1
script.binding = b2
def t1 = Thread.start {
    script.run()
}
def t2 = Thread.start {
    script.run()
}
[t1,t2]*.join()
assert b1.getProperty('x') == 3
assert b2.getProperty('x') == 16
assert t1!=t2



b1 = new Binding(x: 3)
b2 = new Binding(x: 4)
def script1 = shell.parse('x = 2*x')
def script2 = shell.parse('x = 2*x')
script1.binding = b1
script2.binding = b2
t1 = Thread.start {
    script1.run()
}
t2 = Thread.start {
    script2.run()
}
[t1,t2]*.join()
assert b1.getProperty('x') == 6
assert b2.getProperty('x') == 8
assert t1!=t2