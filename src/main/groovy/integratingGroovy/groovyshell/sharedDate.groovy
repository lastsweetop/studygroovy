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