package integratingGroovy.groovyClassLoader

def gcl = new GroovyClassLoader()
def clazz = gcl.parseClass('class Foo { void doIt(){ println "ok"} }')
assert clazz.name == 'Foo'
def o = clazz.newInstance()
o.doIt()

def c1=gcl.parseClass('class Foo{}')
def c2=gcl.parseClass('class Foo{}')
assert c1.name == 'Foo'
assert c2.name == 'Foo'
assert c1 != c2

File file=new File("/Users/apple/Documents/mydream/groovy/studygroovy/src/main/groovy/integratingGroovy/groovyClassLoader/Foo.groovy")
c1 = gcl.parseClass(file)
c2 = gcl.parseClass(new File(file.absolutePath))
assert c1.name == 'integratingGroovy.groovyClassLoader.Foo'
assert c2.name == 'integratingGroovy.groovyClassLoader.Foo'
assert c1 == c2