class Foo {
  static int i
}

assert Foo.class.getDeclaredField('i').type == int.class
assert Foo.i.class != int.class && Foo.i.class == Integer.class

class Person {

    String name
    Integer age

    def increaseAge(Integer years) {
        this.age += years
    }
}

def p = new Person()

class Outer {
    private String privateStr="some string1"

    def callInnerMethod() {
        new Inner().methodA()
    }

    class Inner {
        def methodA() {
            println "${privateStr}."
        }
    }
}

new Outer().callInnerMethod()


class Outer2 {
    private String privateStr = 'some string2'

    def startThread() {
       new Thread(new Inner2()).start()
    }

    class Inner2 implements Runnable {
        void run() {
            println "${privateStr}."
        }
    }
}

new Outer2().startThread()

class Outer3 {
    private String privateStr = 'some string3'

    def startThread() {
        new Thread(new Runnable() {
            void run() {
                println "${privateStr}."
            }
        }).start()
    }
}

new Outer3().startThread()


abstract class Abstract {
    String name

    abstract def abstractMethod()

    def concreteMethod() {
        println 'concrete'
    }
}

interface Greeter {
  void greet(String name)
}

class SystemGreeter implements Greeter {

    @Override
    void greet(String name) {
      println "hello $name"
    }
}

def greeter=new SystemGreeter()
assert greeter instanceof Greeter

interface ExtendedGreeter extends Greeter {
  void sayBye(String name)
}
class DefaultGreeter {
    void greet(String name) { println "Hello" }
}

greeter = new DefaultGreeter()
assert !(greeter instanceof Greeter)

greeter = new DefaultGreeter()
coerced = greeter as Greeter
assert coerced instanceof Greeter

class PersonConstructor {
    String name
    Integer age

    PersonConstructor(name, age) {
        this.name = name
        this.age = age
    }
}

def person1 = new PersonConstructor('Marie', 1)
def person2 = ['Marie', 2] as PersonConstructor
PersonConstructor person3 = ['Marie', 3]

class PersonWOConstructor {
    String name
    Integer age
}

def person4 = new PersonWOConstructor()
def person5 = new PersonWOConstructor(name: 'Marie')
def person6 = new PersonWOConstructor(age: 1)
def person7 = new PersonWOConstructor(name: 'Marie', age: 2)
