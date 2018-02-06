package gettingStarted.runtime

import groovy.time.TimeCategory

/**
 * GroovyObject Interface
 */
class SomeGroovyClass {
    def property1='ha'
    def field2='hu'
    def field1='he'
    def field4 = 'ho'
    String property

    void setProperty(String name,Object value){
        println "setProperty $name"
        this.@"$name" = "overridden"
    }

    def getField1() {
        return 'getHa'
    }
    def getProperty(String name) {
        if (name!='field3'){
            println "getProperty $name"
            metaClass.getProperty(this,name)
        }else {
            'field3'
        }
    }

    def invokeMethod(String name, Object args) {
        "called invokeMethod $name $args"
    }
    def test(){
        "method exists"
    }
}

def someGroovyClass=new SomeGroovyClass()
assert someGroovyClass.test() == 'method exists'
assert someGroovyClass.someMethod() == 'called invokeMethod someMethod []'

assert someGroovyClass.field1 == 'getHa'
assert someGroovyClass.field2 == 'hu'
assert someGroovyClass.field3 == 'field3'
assert someGroovyClass.field4 == 'ho'

someGroovyClass.property='a'
assert someGroovyClass.property == 'overridden'


/**
 * get/setAttribute
 */
class POGO {
    def field1 = 'ha'
    def field2 = 'ho'
    private String field
    String property1

    def getField1() {
        return 'getHa'
    }

    void setProperty1(String property1) {
        this.property1 = "setProperty1"
    }
}

def pogo = new POGO()
assert pogo.metaClass.getAttribute(pogo, 'field1') == 'ha'
assert pogo.metaClass.getAttribute(pogo, 'field2') == 'ho'


pogo.metaClass.setAttribute(pogo, 'field', 'ha')
pogo.metaClass.setAttribute(pogo, 'property1', 'ho')

assert pogo.field == 'ha'
assert pogo.property1 == 'ho'

/**
 * methodMissing propertyMissing
 */
class Foo {
    def storage = [:]
    def methodMissing(String name,args){
        if(storage[name] instanceof Closure){
            Closure closure = (Closure)storage[name];
            closure = (Closure)closure.clone();
            closure.setDelegate(this);
            closure.call(*args)
        }
        "miss method $name($args)"
    }
    def propertyMissing(String name) { storage[name] }
    def propertyMissing(String name,value) { storage[name]=value}
}

def f = new Foo()
assert f.someMethod(421) == 'miss method someMethod([421])'
f.boo = 'bar'
assert f.boo == 'bar'
f.bar = {id,name->
    println "$id $name"
}
f.bar(1,'tea')

/**
 * categories
 */
use(TimeCategory){
    println 10.minute.from.now
    println 3.month.ago

    def date = new Date()
    println date - 3.months
}

class Distance {
    def number
    String toString(){
        "${number}m"
    }
}

class NumberCategory {
    static Distance getMeters(Number number) {
        new Distance(number: number)
    }
}

use(NumberCategory){
    assert 42.meters.toString() == '42m'
}

/**
 * MetaClasses
 */
class Book {
    String title
    static invokeMe(){
        'book'
    }
    def look(){
        'look'
    }
}
class Publisher {
    def publish(){
        'publish'
    }
}

def properties = Collections.synchronizedMap([:])
Book.metaClass.titleInUpperCase = {
    title.toUpperCase()
}
Book.metaClass.author = 'Stephen King'
Book.metaClass.setPublisher = {Object value ->
    properties[System.identityHashCode(delegate)+'publisher'] = value
}
Book.metaClass.getPublisher = { ->
    properties[System.identityHashCode(delegate)+'publisher']
}
Book.metaClass.constructor = { String title ->
    new Book(title: title)
}
Book.metaClass.static.create = { String title ->
    new Book(title: title)
}
def publisher = new Publisher()
Book.metaClass.publish = publisher.&publish
def methodName='Golang'
Book.metaClass."changeTitle${methodName}" = {  ->
    delegate.title = methodName
}
Book.metaClass.'static'.invokeMethod = { String name,args ->
    def metaMethod = Book.metaClass.getStaticMetaMethod(name,args)
    println name
    if(metaMethod){
        metaMethod.invoke(delegate,args)
    }else {
        'bar'
    }
}


def book1 = new Book("Groovy")
def book = Book.create('Groovy')
assert book.titleInUpperCase() == 'GROOVY'
assert book.author == 'Stephen King'
book.publisher = publisher
assert book.publisher == publisher
assert book.publish() == 'publish'
book.changeTitleGolang()
assert book.titleInUpperCase() == 'GOLANG'
assert Book.invokeMe() == 'book'
assert Book.lookBook() == 'bar'
assert book.look() == 'look'

List.metaClass.doubleSize = {  ->
    delegate.size()*2
}
def list=[]
list+=1
list+=2
assert list.doubleSize() == 4

/**
 * extension module
 */
class MaxRetriesExtension {
    static void maxRetries(Integer self,Closure closure){
        int retries=0
        Throwable throwable
        while (retries<self){
            try {
                closure.call()
                break
            }catch (Throwable err){
                throwable=err
                retries++
            }

        }
        if(retries==0 && throwable){
            throw throwable
        }
    }
}
int i=0
5.maxRetries {
    i++
}
assert i == 1

class StaticStringExtension {
    static String greeting(String self){
        "hello, world"
    }
}

println String.greeting()