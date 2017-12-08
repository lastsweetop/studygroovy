package gettingStarted.compileTime

import com.sun.corba.se.spi.orbutil.threadpool.Work
import groovy.transform.*
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy
import groovy.transform.builder.InitializerStrategy
import groovy.transform.builder.SimpleStrategy
import groovy.util.logging.Log
import groovyjarjarantlr4.v4.runtime.misc.NotNull
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import javax.swing.*
import java.util.concurrent.TimeUnit

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


//If true, also delegates methods annotated with @Deprecated
class WithDeprecation {
    @Deprecated
    void foo() {}
}
class WithoutDeprecation {
    @Deprecated
    void bar() {}
}
class Delegating {
    @Delegate(deprecated = true) WithDeprecation with = new WithDeprecation()
    @Delegate WithoutDeprecation without = new WithoutDeprecation()
}
def d = new Delegating()
d.foo() // passes thanks to deprecated=true
//d.bar() // fails because of @Deprecated

@interface A {

}

class WithAnnotations {
    @A
    void method(@NotNull String str){

    }
}

class DelegatingWithoutAnnotations {
    @Delegate WithAnnotations withAnnotations
}

class DelegatingWithAnnotations {
    @Delegate(methodAnnotations = true,parameterAnnotations = true) WithAnnotations withAnnotations1
}
def d1 = new DelegatingWithoutAnnotations()
def d2 = new DelegatingWithAnnotations()

class Worker {
    void task1(){

    }
    void task2(){

    }
}

class DelegatingWorker {
    @Delegate(includes = ['task1']
    //        ,excludes=['task2']
    )
    Worker worker
}

new DelegatingWorker(worker: new Worker()).task1()

//A list of interfaces containing method signatures to be excluded from delegation
interface AppendStringSelector {
    StringBuilder append(String str)
}

@ToString(includeNames = true,includePackage = false)
class UpperStringBuilder {
    @Delegate(includeTypes = AppendStringSelector,interfaces = false)
    StringBuilder stringBuilder1=new StringBuilder()
    @Delegate(excludeTypes = AppendStringSelector,interfaces = false)
    StringBuilder stringBuilder2=new StringBuilder()
}

UpperStringBuilder upperStringBuilder=new UpperStringBuilder()
upperStringBuilder.append(1)
upperStringBuilder.append("dddd")
upperStringBuilder.append(true)

println upperStringBuilder

interface AppendBooleanSelector {
    StringBuilder append(boolean b)
}
interface AppendFloatSelector {
    StringBuilder append(float b)
}

@ToString(includePackage = false,includeNames = true)
class NumberBooleanBuilder {
    @Delegate(includeTypes=AppendBooleanSelector, interfaces=false)
    StringBuilder  bools= new StringBuilder()
    @Delegate(includeTypes=[AppendFloatSelector], interfaces=false)
    StringBuilder  nums= new StringBuilder()
}
NumberBooleanBuilder numberBooleanBuilder=new NumberBooleanBuilder()
numberBooleanBuilder.append(true)
numberBooleanBuilder.append(11.1)

println numberBooleanBuilder

@Immutable
class Point {
    int x,y
}

@Immutable
@TupleConstructor
@ToString(includePackage = false,includeNames = true)
class Point2 {
    int x,y
}

@Immutable(knownImmutableClasses = [Point2])
class Triangle {
    Point2 x,y,z
}

@Immutable(knownImmutables = ['x','y','z'])
class Triangle2 {
    Point2 x,y,z
}

@Immutable(copyWith = true)
class User {
    String name
    Integer age
}

def bob=new User( 'Bob',43)
def alice=bob.copyWith(name:'Alice')
assert alice.name == 'Alice'
assert alice.age == 43

@Memoized
long longComputation(int seed){
    Thread.sleep(1000*seed)
    System.nanoTime()
}

def x = longComputation(1)
def y = longComputation(1)
assert x == y

@Singleton(property = 'theOne')
class GreetingService {
    String greeting(String name){
        "Hello, $name"
    }
}

assert GreetingService.theOne.greeting('sweetop') == 'Hello, sweetop'

class Collaborator {
    public static boolean init = false
}

@Singleton(lazy = true,strict = false)
class GreetingService2 {
    static void init(){}
    GreetingService2(){
        Collaborator.init = true
    }
    String greeting(String name){
        "Hello, $name"
    }
}
GreetingService2.init()
assert Collaborator.init == false
assert GreetingService2.instance.greeting('Bob')=='Hello, Bob'
assert Collaborator.init == true

/**
 * Logging Improvements
 */
@groovy.util.logging.Commons
class Greeter4 {
    void greet(){
        log.debug 'method:greeting'
        println 'Hello world'
    }
}



