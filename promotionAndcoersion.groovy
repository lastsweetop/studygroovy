package promotionAndcoersion

import org.codehaus.groovy.ant.Groovy
import static java.lang.Math.*

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

/**
 * map to type coersion
 */
def map
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

/**
 * string to enum coersion
 */
enum State {
  up,
  down
}
State up='up'
assert up == State.up
def val  = 'up'
assert "$val" as State == State.up

State switchState(State state) {
  switch (state) {
    case 'up':
      return State.down
    case 'down':
      return State.up
  }
}
assert switchState('up' as State) == State.down
assert switchState(State.down) == State.up

/**
 * custom type coersion
 */
class Polar {
  double r
  double phi
  def asType(Class target) {
    if (Cartesian == target) {
      return new Cartesian(x:r*cos(phi),y:r*sin(phi))
    }
  }
}
class Cartesian {
  double x
  double y
}
def sigma = 1E-16
def polar = new Polar(r:1.0,phi:PI/2)
def cartesian = polar as Cartesian
assert abs(cartesian.x-sigma) < sigma

/**
 * class literals vs variables and the as operator
 */
interface Greeter2 {
  void greet()
}
Greeter2 greeter2 = {println 'Hello,Groovy!'}
greeter2.greet()


