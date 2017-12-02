/**
 * Defining a closure
 */
def temp
temp={item++}
temp={->item++}
temp={println it}
temp={it->println it}
temp={name->println name}
temp={String x,int y->
        println "hex ${x} the value is ${y}"
     }
temp={reader->
      def line=reader.readLine()
      line.trim()
    }
/**
 * closure as a object
 */
def listener = { e ->
  println "Clicked on $e.source"
}
assert listener instanceof Closure
Closure callback = {println 'Done!'}
Closure<Boolean> isTextFile = {
  File it -> it.name.endWith('.txt')
}

/**
 * Call a closure
 */
def code={123}
assert code()==123
assert code.call()==123

def isOdd={int i -> i%2 !=0}
assert isOdd(3)==true
assert isOdd.call(2)==false

def isEven={int i -> i%2==0}
assert isEven(3)==false
assert isEven.call(2)==true

/**
 * normal parameters
 */
def closureWithOneArg = { str -> str.toUpperCase()}
assert closureWithOneArg('groovy')=='GROOVY'

def closureWithOneArgAndExplicitType = { String str -> str.toUpperCase()}
assert closureWithOneArgAndExplicitType('groovy') == 'GROOVY'

def closureWithTwoArgs = { a,b -> a+b }
assert closureWithTwoArgs(1,2) == 3

def closureWithTwoArgsAndExplicitTypes = { int a,int b -> a+b }
assert closureWithTwoArgsAndExplicitTypes(1,2) == 3

def closureWithTwoArgsAndOptionalTypes = { a,int b -> a+b }
assert closureWithTwoArgsAndOptionalTypes(1,2) == 3

def closureWithTwoArgsAndDefaultValue = { int a,int b=2 -> a+b }
assert closureWithTwoArgsAndDefaultValue(1) == 3

/**
 * implicit parameters
 */
def greeting = {"hello,$it!"}
assert greeting('Patrick')=='hello,Patrick!'

greeting = {it -> "hello,$it!"}
assert greeting('Patrick')=='hello,Patrick!'

/**
 * varargs
 */
def concat1 = { String... args -> args.join('')}
assert concat1('abc','def')=='abcdef'

def concat2 = { String[] args ->
  args.join('')
}
assert concat2('abc','def')=='abcdef'

def multiConcat = { int n,String... args->
  args.join('')*2
}
assert multiConcat(2,'abc','def') == 'abcdefabcdef'

/**
 * The meaning of this
 */
class Enclosing {
  void run(){
    def whatIsThisObject = { getThisObject()}
    assert whatIsThisObject() == this
    def whatIsThis = {this}
    assert whatIsThis() == this
  }
}

class EnclosedInInnerClass {
  class Inner {
    Closure cl={this}
  }
  void run(){
    def inner=new Inner()
    assert inner.cl() == inner
  }
}

class NestedClosures {
    void run() {
        def nestedClosures = {
            def cl = { this }
            cl()
        }
        assert nestedClosures() == this
    }
}

def e1 = new Enclosing()
e1.run()
def e2 = new EnclosedInInnerClass()
e2.run()
def e3 = new NestedClosures()
e3.run()

class Person12 {
    String name
    int age
    String toString() { "$name is $age years old" }

    String dump() {
        def cl = {
            String msg = this.toString()
            println msg
            msg
        }
        cl()
    }
}
def p = new Person12(name:'Janice', age:74)
assert p.dump() == 'Janice is 74 years old'

/**
 * Owner of a closure
 */
 class Enclosing1 {
     void run() {
         def whatIsOwnerMethod = { getOwner() }
         assert whatIsOwnerMethod() == this
         def whatIsOwner = { owner }
         assert whatIsOwner() == this
     }
 }
 class EnclosedInInnerClass1 {
     class Inner {
         Closure cl = { owner }
     }
     void run() {
         def inner = new Inner()
         assert inner.cl() == inner
     }
 }
 class NestedClosures1 {
     void run() {
         def nestedClosures = {
             def cl = { owner }
             cl()
         }
         assert nestedClosures() == nestedClosures
     }
 }
 def e11 = new Enclosing()
 e11.run()
 def e12 = new EnclosedInInnerClass()
 e12.run()
 def e13 = new NestedClosures()
 e13.run()

/**
 * delegate of a closure
 */
 class Enclosing3 {
     void run() {
         def cl = { getDelegate() }
         def cl2 = { delegate }
         assert cl() == cl2()
         assert cl() == this
         def enclosed = {
             { -> delegate }.call()
         }
         assert enclosed() == enclosed
     }
 }
 def e31 = new Enclosing3()
 e31.run()

 class Person13 {
   String name
 }

 class Thing {
   String name
 }

def p1 = new Person13(name:"Norman")
def t1 = new Thing(name:"Teapot")

def upperCasedName = { delegate.name.toUpperCase() }
upperCasedName.delegate = p1
assert upperCasedName() == 'NORMAN'
upperCasedName.delegate = t1
assert upperCasedName() == 'TEAPOT'

def target=p1
def upperCasedNameUsingVar = { target.name.toUpperCase() }
assert upperCasedNameUsingVar() == 'NORMAN'
target=t1
assert upperCasedNameUsingVar() == 'TEAPOT'

/**
 * delegation strategy
 */
class Person14 {
  String name
}

def p2 = new Person14(name:'Igor')
def cl = {name.toUpperCase()}
cl.delegate=p2
assert cl() == 'IGOR'

class Person15 {
  String name
  def pretty  = {  ->
    "My name is $name"
  }
  String toString(){
    pretty()
  }
}

class Thing15 {
  String name
}

def p15 = new Person15(name:'Sarah')
def t15 = new Thing15(name:'Teapot')
assert p15.toString() == 'My name is Sarah'
p15.pretty.delegate = t15
assert p15.toString() == 'My name is Sarah'
p15.pretty.resolveStrategy = Closure.DELEGATE_FIRST
assert p15.toString() == 'My name is Teapot'


class Person16 {
  String name
  int age
  def fetchAge = {  ->
    age
  }
}

class Thing16 {
  String name
}

def p16 = new Person16(name:'Jessica', age:42)
def t16 = new Thing16(name:'Printer')
def cl16 = p16.fetchAge
cl16.delegate = p16
assert cl16() == 42
cl16.delegate = t16
assert cl16() == 42
cl16.resolveStrategy = Closure.DELEGATE_ONLY
cl16.delegate = p16
assert cl16() == 42
cl16.delegate = t16
try{
  cl16()
  assert false
}catch(MissingPropertyException e) {
  // "age" is not defined on the delegate
}
cl16.resolveStrategy = Closure.DELEGATE_FIRST
assert cl16() == 42

/**
 * Closure in GString
 */
 def x=1
 def gs = "x=${x}"
 assert gs == 'x=1'
 x=2
 assert gs != 'x=2'

 def gs1 = "x=${->x}"
 x=1
 assert gs1 == 'x=1'
 x=2
 assert gs1 == 'x=2'

 class Person21 {
     String name
     String toString() {
         name
     }
 }
 def sam = new Person21(name:'Sam')
 def lucy = new Person21(name:'Lucy')

 p = sam
 def gs2 = "Name : ${p}"
 assert gs2 == 'Name : Sam'
 p == lucy
 assert gs2 == 'Name : Sam'
 sam.name = 'Lily'
 assert gs2 == 'Name : Lily'

 def gs3= "Name : ${->p}"
 p = sam
 assert gs3 == 'Name : Lily'
 p = lucy
 assert gs3 == 'Name : Lucy'

/**
 * Memoization
 */
def fib
fib = { long n -> n<2?n:fib(n-1)+fib(n-2) }
assert fib(25) == 75025 // slow!

fib = { long n -> n<2?n:fib(n-1)+fib(n-2) }.memoize()
assert fib(25) == 75025 // fast!
