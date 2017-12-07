package gettingStarted.compileTime

import groovy.transform.*
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy
import groovy.transform.builder.InitializerStrategy
import groovy.transform.builder.SimpleStrategy

import javax.swing.*

/**
 * code generation transformations
 */
//@EqualsAndHashCode
//@TupleConstructor
//@ToString
@Canonical
@Sortable
class Person {
    String firstName
    String lastName
    Integer born
}

def person = new Person('Wu', 'Dong')
println person

def p1 = new Person('Jack', 'Nicholson')
def p2 = new Person('Jack', 'Nicholson')
assert p1 == p2

@InheritConstructors
class CustomException extends Exception {
}

new CustomException()
new CustomException('customException')
new CustomException('costomException', new RuntimeException())
new CustomException(new RuntimeException())

@Category(Integer)
class TripleCategory {
    public Integer triple() {
        3 * this
    }
}

use(TripleCategory) {
    assert 3.triple() == 9
}

class SomeBean {
    @IndexedProperty
    String[] someArray = new String[2]
    @IndexedProperty
    List someList = []
}

def someBean = new SomeBean()
someBean.someArray[1] = 'value'
assert someBean.someArray[1] == 'value'
someBean.someList[0] = 123
assert someBean.someList == [123]

class ABean {
    @Lazy
    LinkedList filed = { [a, b, c] }()
}

@Canonical
class Tree {
    Leaf leaf
}

@Canonical
class Leaf {
    String name
}

@Canonical
@Newify([Tree, Leaf])
class TreeBuilder {
    Tree tree = Tree(Leaf('c'))
}

println new TreeBuilder()

def people = [
        new Person(firstName: 'Johnny', lastName: 'Depp', born: 1963),
        new Person(firstName: 'Keira', lastName: 'Knightley', born: 1985),
        new Person(firstName: 'Geoffrey', lastName: 'Rush', born: 1951),
        new Person(firstName: 'Orlando', lastName: 'Bloom', born: 1977)
]
assert people[0] > people[2]
assert people.sort()*.lastName==['Rush', 'Depp', 'Knightley', 'Bloom']
println  people.sort(false,Person.comparatorByBorn())*.lastName = ["Rush", "Depp", "Bloom", "Knightley"]

@Builder(builderStrategy = SimpleStrategy,prefix = '')
class Person2 {
    String firstName
    String lastName
    Integer born
}
new Person2()
def p21 = new Person2().firstName('Wu').lastName('Dong').born(33)
assert "$p21.firstName $p21.lastName" == 'Wu Dong'

@Canonical
class Person3 {
    String first
    String last
    int born
}

@Builder(builderStrategy = ExternalStrategy,forClass = Person3,includes = ['first','last'],prefix = 'with',buildMethodName = 'create')
class Person3Builder {
}

def p31 = new Person3Builder().withFirst('a').withLast('b').create()
assert "$p31.first $p31.last" == 'a b'

@Builder(builderStrategy = ExternalStrategy,forClass = DefaultButtonModel)
class ButtonModelBuilder {
}

ButtonModel buttonModel=new ButtonModelBuilder().enabled(true).pressed(false).armed(true).rollover(true).selected(true).build()
assert buttonModel.enabled

@Builder(builderMethodName = 'maker',buildMethodName = 'make',prefix = 'with',excludes = 'age')
class Person4 {
    String firstName
    String lastName
    int age
}

def p41 = Person4.maker().withFirstName('a').withLastName('b').make()
assert "$p41.firstName $p41.lastName" == 'a b'


@ToString(includePackage = false)
@Builder
class Person5 {
    String first, last
    int born

    Person5(){}

    @Builder(builderClassName='MovieBuilder', builderMethodName='byRoleBuilder')
    Person5(String roleName) {
        if (roleName == 'Jack Sparrow') {
            this.first = 'Johnny'; this.last = 'Depp'; this.born = 1963
        }
    }

    @Builder(builderClassName='NameBuilder', builderMethodName='nameBuilder', prefix='having', buildMethodName='fullName')
    static String join(String first, String last) {
        first + ' ' + last
    }

    @Builder(builderClassName='SplitBuilder', builderMethodName='splitBuilder')
    static Person5 split(String name, int year) {
        def parts = name.split(' ')
        new Person5(first: parts[0], last: parts[1], born: year)
    }
}

assert Person5.splitBuilder().name("Johnny Depp").year(1963).build().toString() == 'Person5(Johnny, Depp, 1963)'
assert Person5.byRoleBuilder().roleName("Jack Sparrow").build().toString() == 'Person5(Johnny, Depp, 1963)'
assert Person5.nameBuilder().havingFirst('Johnny').havingLast('Depp').fullName() == 'Johnny Depp'
assert Person5.builder().first("Johnny").last('Depp').born(1963).build().toString() == 'Person5(Johnny, Depp, 1963)'

@ToString(includePackage = false)
@Builder(builderStrategy = InitializerStrategy)
@Immutable
class Person6 {
    String firstName
    String lastName
    int age
}

@CompileStatic
def firstLastAge() {
    assert new Person6(Person6.createInitializer().firstName('a').lastName('b').age(14)).toString() == 'Person6(a, b, 14)'
}
firstLastAge()

/**
 * class design annotations
 */
class Event {
    @Delegate Date when
    String title
}

def event = new Event(title: 'Groovy keynote', when: Date.parse('yyyy/MM/dd','2017/11/12'))
assert event.before(new Date())
assert event instanceof Serializable

interface Greeter{
    void sayHello()
}


//Should the interfaces implemented by the field be implemented by the class too
class MyGreeter implements Greeter {
    @Override
    void sayHello(){
        println 'hello'
    }
}

class DelegatingGreeter {
    @Delegate MyGreeter myGreeter=new MyGreeter()
}

def greeter = new DelegatingGreeter()
assert greeter instanceof Greeter



