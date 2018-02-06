package languageSpecification.typings

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FirstParam
import groovy.transform.stc.FromString

import java.util.function.Predicate

/**
 * optional typing
 */
def asSring = 'foo'
assert asSring.toUpperCase()


def concat(a,b){
    a+b
}

assert concat(1, 2) == 3
assert concat('a', 'bbb') == 'abbb'

/**
 * static type checking
 */
class Person {
    String firstName
    String lastName
}

Person.metaClass.getFormattedName = {"$firstName $lastName"}

def p = new Person(firstName: 'Raymond',lastName: 'Devos')
assert p.formattedName == 'Raymond Devos'

/**
 * TypeChecked annotation
 */
@TypeChecked
class GreetingService {
    String greeting(){
        doGreet()
    }

    @TypeChecked(TypeCheckingMode.SKIP)
    private String doGreet() {
//        def b = new SentenceBuilder()
//        b.Hello.my.name.is.John
        b
    }
}

/**
 * type checking assignment
 */
Class clazz = "java.lang.String"

@TupleConstructor
class Person1 {
    String firstName
    String lastName
}

Person1 person = new Person1("Raymond",'Devos')
Person1 person1 = ['Raymond','Devos']
Person1 person2 = [firstName:'Raymond',lastName: 'Devos']

class Duck {
    void quack(){
        println 'Quack!'
    }
}

class QuackingBird {
    void quack(){
        println 'Quack!'
    }
}

void accept(quacker){
    quacker.quack()
}
accept(new Duck())

/**
 * type inference
 */
class SomeClass {
    def someUntypedField
    String someTypedField

    void someMethod(){
        someUntypedField = '123'
        someUntypedField = someUntypedField.toUpperCase()
    }

    void someSafeMethod(){
        someTypedField = '123'
        someTypedField = someTypedField.toUpperCase()
    }
}
def someClass=new SomeClass()
someClass.someMethod()

/**
 * flow type
 */
void flowTypingWithExplicitType(){
    List list=['a','b','c']
    list = list*.toUpperCase()
    list = 'foo'
}

int compute(String string) { string.length() }
String compute(Object o) { "Nope" }
def o = 'string'
def result = compute(o)
println result

def text = 'Hello, world!'
def closure = {
    println text
}
closure.call()

void doSomething (str){
    println str('hi')
}

result='hello'
doSomething { String it ->
    result = "Result: $it"
    result
}
result = result?.toUpperCase()
println result

/**
 * return type infrence
 */
@TypeChecked
int testClosureReturnTypeInference(String arg) {
    def cl = { "Arg: $arg" }
    def val = cl()

    val.length()
}


class A {
    def compute() { 'some string' }
    def computeFully() {
        compute().toUpperCase()
    }
}
@TypeChecked
class B extends A {
    def compute() { 123 }
}

/**
 * Closures vs methods
 */
class Person2 {
    String name
    int age
}

void inviteIf(Person2 p,Closure<Boolean> predicate) {
    if (predicate.call(p)) {
        println p.name
    }
}
@CompileStatic
void passesCompilation() {
    Person2 p2 = new Person2(name: 'Gerard', age: 55)
    inviteIf1(p2) {
        it.age >= 18 // No such property: age
    }
}
passesCompilation()

void inviteIf1(Person2 p,@ClosureParams(FirstParam)  Closure<Boolean> predicate) {
    if (predicate.call(p)) {
        println p.name
    }
}

@TypeChecked
void passesCompilation1() {
    Person2 p2 = new Person2(name: 'Gerard', age: 55)
    inviteIf1(p2) {
        it.age >= 18 // No such property: age
    }
}
passesCompilation1()

void inviteIf2(Person2 p,Predicate<Person2> predicate) {
    if (predicate.test(p)) {
        println p.name
    }
}

@TypeChecked
void passesCompilation2() {
    Person2 p = new Person2(name: 'Gerard', age: 55)

    inviteIf2(p) {
        it.age >= 18
    }
}

passesCompilation2()

public <T> void inviteIf3(T p,@ClosureParams(value=FromString, options=["T"])  Closure<Boolean> predicate) {
    if (predicate.call(p)) {
        println p.name
    }
}
@TypeChecked
void passesCompilation3() {
    Person2 p = new Person2(name: 'Gerard', age: 55)

    inviteIf3(p) {
        it.age >= 18
    }
}
passesCompilation3()