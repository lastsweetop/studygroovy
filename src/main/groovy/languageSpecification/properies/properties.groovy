package languageSpecification.properies
class Person {
    String name
    int age
}

class Person1 {
    final String name
    final int age
    Person1(String name, int age) {
        this.name = name
        this.age = age
    }
}

class Person2 {
    String name
    void name(String name) {
        this.name = "Wonder$name"
    }
    String wonder() {
        this.name
    }
}
def p = new languageSpecification.typings.Person2()
p.name = 'Marge'
assert p.name == 'Marge'
p.name('Marge')
assert p.wonder() == 'WonderMarge'

class Person3 {
    String name
    int age
}
p = new Person3()
assert p.properties.keySet().containsAll(['name','age'])

class PseudoProperties {
    // a pseudo property "name"
    void setName(String name) {}
    String getName() {}

    // a pseudo read-only property "age"
    int getAge() { 42 }

    // a pseudo write-only property "groovy"
    void setGroovy(boolean groovy) {  }
}
p = new PseudoProperties()
p.name = 'Foo'
assert p.age == 42
p.groovy = true
