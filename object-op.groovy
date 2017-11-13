#!/usr/bin/env groovy

def user
def displayName = user?.name ?: 'Anonymous'
println displayName

class User {
    String name
    String getName() { "Name: $name" }
}
user = new User(name:'Bob')
assert user.name == 'Name: Bob'
assert user.@name == 'Bob'

def str = 'example of method reference'
def fun = str.&toUpperCase
def upper = fun()
assert upper == str.toUpperCase()

class Person {
    String name
    Integer age
}

def transform={
  List elements,Closure action ->
    def result = []
    elements.each {
        result << action(it)
    }
    result
}

String describe(Person p) {
    "$p.name is $p.age"
}
def action = this.&describe
def list = [
    new Person(name: 'Bob',   age: 42),
    new Person(name: 'Julia', age: 35)]

 assert transform(list, action) == ['Bob is 42', 'Julia is 35']


def doSomething(String str) { str.toUpperCase() }
def doSomething(Integer x) { 2*x }
def reference = this.&doSomething
assert reference('foo') == 'FOO'
assert reference(123)   == 246
