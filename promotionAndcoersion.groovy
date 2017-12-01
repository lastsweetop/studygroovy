import org.codehaus.groovy.ant.Groovy

/**
 * Assigning a closure to a SAM type
 */
interface Predicate<T> {
    boolean accept(T obj)
}

Predicate filter = {it.contains 'G'}
assert filter.accept('Groovy') == true

abstract class Greeter {
    abstract String getName()
    void greet() {
        println "hello, $name"
    }
}
Greeter greeter = {"Groovy"}
greeter.greet()

boolean doFilter(String s){
    s.contains('G')
}
Predicate predicate=this.&doFilter
assert predicate.accept('Groovy') == true
Greeter greeter1=GroovySystem.&getVersion
greeter1.greet()

/**
 *  Calling a method accepting a SAM type with a closure
 */
public <T> List<T> filter2(List<T> source,Predicate<T> predicate){
    source.findAll {predicate.accept(it)}
}

assert filter2(['Groovy','Kotlin']) { it.contains 'G' }  == ['Groovy']

interface FooBar {
    int foo()
    void bar()
}
def fooBar = {
    println 'ok'
    123
} as FooBar
assert fooBar.foo() == 123
fooBar.bar()

class FooBar1 {
    int foo(){
        1
    }
    void bar(){
        println 'bar'
    }
}
def fooBar1 = {
    println 'ok'
    123
} as FooBar1
assert fooBar1.foo() == 123
fooBar1.bar()


map = [
        i:10,
        hasNext:{map.i>0},
        next: {map.i--}
]
def iter = map as Iterator
for (def i : iter){
    println i
}

interface X {
    void f()
    void g(int n)
    void h(String s,int n)
}

x = [
        f:{ println 'f called'},
        g:{ println "g($it) called "}
] as X
x.f()
x.g(3)

