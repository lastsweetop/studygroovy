def someMethod() { 'method called' }
String anotherMethod() { 'another method called' }
def thirdMethod(param1) { "$param1 passed" }
static String fourthMethod(String param1) { "$param1 passed" }

def foo(Map args) { "${args.name}: ${args.age}" }
println foo(name: 'Marie', age: 1)

def foo(String par1, Integer par2 = 1) { [name: par1, age: par2] }
assert foo('Marie').age == 1

def foo(...args) { args.length }
assert foo() == 0
assert foo(1) == 1
assert foo(1, 2) == 2

def foo2(Object[] args) { args.length }
assert foo2() == 0
assert foo2(1) == 1
assert foo2(1, 2) == 2

def foo3(Object... args) { args }
assert foo3(null) == null

def foo4(Object... args) { args }
Integer[] ints = [1, 2]
assert foo4(ints) == [1, 2]

def foo5(Object... args) { 1 }
def foo5(Object x) { 2 }
assert foo5() == 1
assert foo5(1) == 2
assert foo5(1, 2) == 1
