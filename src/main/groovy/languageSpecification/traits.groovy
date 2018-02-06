package languageSpecification

import groovy.transform.SelfType
import groovy.transform.CompileStatic

trait FlyingAbility {
  String fly(){
    "I'm flying!"
  }
}

class Bird implements FlyingAbility {

}
def b = new Bird()
assert b.fly() == "I'm flying!"

trait Greetable {
  abstract String name()
  String greeting() {
    "hello ${name()}"
  }
}

class Person6 implements Greetable{
  String name() { 'Bob' }
}

def p=new Person6()
assert p.greeting()=='hello Bob'

trait Greeter3 {
  private def greetingMessage(){
    'Hello from a private method!'
  }
  String greet() {
       def m = greetingMessage()
       println m
       m
   }
}

class GreetingMachine implements Greeter3 {
}

def g=new GreetingMachine()
assert g.greet()=='Hello from a private method!'

try{
  assert g.greetingMessage()
}catch(MissingMethodException e) {
  println "greetingMessage is private in trait"
}

trait Introspector {
  def whoAmI() { this }
}
class Foo implements Introspector{
}

def foo=new languageSpecification.clz.Foo()
assert foo.whoAmI()==foo

interface Named {
  def name()
}
trait Greetable1 implements Named {
  def greeting() { "Hello, ${name()}!" }
}
class Person7 implements Greetable1 {
    def name() { 'Bob' }
}

p=new Person7()
assert p.greeting()=='Hello, Bob!'
assert p instanceof Named
assert p  instanceof Greetable1

trait Named1 {
    String name
}
class Person8 implements Named1 {}
p = new Person8(name: 'Bob')
assert p.name == 'Bob'
assert p.getName() == 'Bob'

trait Counter {
    private int count = 0
    int count() { count += 1; count }
}
class Foo1 implements Counter {}
def f = new Foo1()
assert f.count() == 1
assert f.count() == 2

trait Named2 {
    public String name
}
class Person9 implements Named2 {}
p = new Person9()
// p.Named2__name = 'Bob'


trait SpeakingAbility {
    def speak() { "I'm speaking!" }
}

class Duck implements FlyingAbility, SpeakingAbility {
  String quack() { "Quack!" }
   String speak() { quack() }
}

def d = new languageSpecification.typings.Duck()
assert d.fly() == "I'm flying!"
assert d.quack() == "Quack!"
assert d.speak() == "Quack!"

trait Named3 {
    String name
}
trait Polite extends Named3 {
    String introduce() { "Hello, I am $name" }
}
class Person10 implements Polite {}
p = new Person10(name: 'Alice')
assert p.introduce() == 'Hello, I am Alice'

trait WithId {
    Long id
}
trait WithName {
    String name
}
trait Identified implements WithId, WithName {

}

trait SpeakingDuck2 {
    def speak() { quack() }
}
class Duck2 implements SpeakingDuck2 {
    String methodMissing(String name, args) {
        "${name.capitalize()}!"
    }
}
d = new Duck2()
assert d.speak() == 'Quack!'


trait DynamicObject {
    private Map props = [:]
    def methodMissing(String name, args) {
        name.toUpperCase()
    }
    def propertyMissing(String prop) {
        props[prop]
    }
    void setProperty(String prop, Object value) {
        props[prop] = value
    }
}

class Dynamic implements DynamicObject {
    String existingProperty = 'ok'
    String existingMethod() { 'ok' }
}
d = new Dynamic()
assert d.existingProperty == 'ok'
assert d.foo == null
d.foo = 'bar'
assert d.foo == 'bar'
assert d.existingMethod() == 'ok'
assert d.someMethod() == 'SOMEMETHOD'

trait A {
    String exec() { 'A' }
}
trait B {
    String exec() { 'B' }
}
class C implements A,B {

}
def c = new C()
assert c.exec() == 'B'

class C1 implements A,B {
    String exec() { A.super.exec() }
}
c = new C1()
assert c.exec() == 'A'

trait Extra {
    String extra() { "I'm an extra method" }
}
class Something {
    String doSomething() { 'Something' }
}
def s = new Something() as Extra
println s.extra()
println s.doSomething()

trait A2 { def methodFromA() {'methodFromA'} }
trait B2 { def methodFromB() {'methodFromB'} }
class C2 { def methodFromC() {'methodFromC'}}
c = new C2().withTraits A2, B2
println c.methodFromA()
println c.methodFromB()
println c.methodFromC()
println c.class

interface MessageHandler {
    void on(String message, Map payload)
}

trait DefaultHandler implements MessageHandler {
    void on(String message, Map payload) {
        println "Received $message with payload $payload"
    }
}

class SimpleHandlerWithLogging implements DefaultHandler {
    void on(String message, Map payload) {
        println "Seeing $message with payload $payload"
        DefaultHandler.super.on(message, payload)
    }
}

trait LoggingHandler implements MessageHandler {
    void on(String message, Map payload) {
        println "Seeing $message with payload $payload"
        super.on(message, payload)
    }
}

class HandlerWithLogger implements DefaultHandler, LoggingHandler {}
def loggingHandler = new HandlerWithLogger()
loggingHandler.on('test logging', [:])
println '============='
trait SayHandler implements MessageHandler {
    void on(String message, Map payload) {
        if (message.startsWith("say")) {
            println "I say ${message - 'say'}!"
        } else {
            super.on(message, payload)
        }
    }
}
class Handler implements DefaultHandler, SayHandler, LoggingHandler {}
def h = new Handler()
h.on('foo', [:])
println '------------'
h.on('sayHello', [:])
println '============='
class AlternateHandler implements DefaultHandler, LoggingHandler, SayHandler {}
h = new AlternateHandler()
h.on('foo', [:])
println '------------'
h.on('sayHello', [:])


trait Filtering {
    StringBuilder append(String str) {
        def subst = str.replace('o','')
        super.append(subst)
    }
    String toString() { super.toString() }
    def reverse() {super.reverse()}
}
def sb = new StringBuilder().withTraits Filtering
sb.append('Groovy')
assert sb.reverse().toString() == 'yvrG'
println sb.class

trait Greeter1 {
    String greet() { "Hello $name" }
    abstract String getName()
}

Greeter1 greeter = { 'Alice' }
void greet(Greeter1 g) { println g.greet() }
greet { 'Alice' }

class Person11 {
    String name
}
trait Bob {
    String getName() { 'Bob' }
}

 p = new Person11(name: 'Alice')
assert p.name == 'Alice'
 p2 = p as languageSpecification.annotation.Bob
assert p2.name == 'Bob'

class A3 { String methodFromA() { 'A' } }
class B3 { String methodFromB() { 'B' } }
A3.metaClass.mixin B3
def o = new A3()
assert o.methodFromA() == 'A'
assert o.methodFromB() == 'B'
assert o instanceof A3
assert !(o instanceof B3)

trait TestHelper {
    public static boolean CALLED = false
    static void init() {
        CALLED = true
    }
}
class Foo3 implements TestHelper {}
Foo3.init()
assert Foo3.TestHelper__CALLED

class Bar implements TestHelper {}
class Baz implements TestHelper {}
languageSpecification.annotation.Bar.init()
assert languageSpecification.annotation.Bar.TestHelper__CALLED
assert !Baz.TestHelper__CALLED

trait IntCouple {
    int x = 1
    int y = 2
    int sum() {  getX()+getY() }
}

class BaseElem implements IntCouple {
    int f() { sum() }
}
def base = new BaseElem()
assert base.f() == 3

class Elem implements IntCouple {
    int x = 3
    int y = 4
    int f() { sum() }
}
def elem = new Elem()
assert elem.f() == 7

class CommunicationService {
    static void sendMessage(String from, String to, String message) {
        println "$from sent [$message] to $to"
    }
}

class Device { String id }

@SelfType(Device)
@CompileStatic
trait Communicating {
    void sendMessage(Device to, String message) {
        SecurityService.check(this)
        SecurityService.check(to)
        CommunicationService.sendMessage(id, to.id, message)
    }
}

class MyDevice extends Device implements Communicating {

}
class SecurityService {
    static void check(Device d) { if (d.id==null) throw new SecurityException() }
}

def bob = new MyDevice(id:'Bob')
def alice = new MyDevice(id:'Alice')
bob.sendMessage(alice,'secret')

trait Counting {
    int x
    void inc() {
        // x++
    }
    void dec() {
        // --x
    }
}
class Counter1 implements Counting {}
c = new Counter1()
c.inc()
