package languageSpecification

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
assert !(greeter instanceof promotions.Greeter)

greeter = new DefaultGreeter()
coerced = greeter as promotions.Greeter
assert coerced instanceof promotions.Greeter
