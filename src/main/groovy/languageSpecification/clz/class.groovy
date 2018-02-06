package languageSpecification.clz

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

def p = new languageSpecification.typings.Person()

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
