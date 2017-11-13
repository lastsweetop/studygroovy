#!/usr/bin/env groovy

class Car {
    String make
    String model
}
def cars = [
       new Car(make: 'Peugeot', model: '508'),
       new Car(make: 'Renault', model: 'Clio')]
def makes = cars*.make
assert makes == ['Peugeot', 'Renault']

cars = [
   new Car(make: 'Peugeot', model: '508'),
   null,
   new Car(make: 'Renault', model: 'Clio')]
assert cars*.make == ['Peugeot', null, 'Renault']
assert null*.make == null


class Component {
    Long id
    String name
}
class CompositeObject implements Iterable<Component> {
    def components = [
        new Component(id: 1, name: 'Foo'),
        new Component(id: 2, name: 'Bar')]

    @Override
    Iterator<Component> iterator() {
        components.iterator()
    }
}
def composite = new CompositeObject()
assert composite*.id == [1,2]
assert composite*.name == ['Foo','Bar']


int add(int x, int y, int z) {
    x*y+z
}

def args=[4,5,6]

assert this.&add(*args)==26

args=[4]

assert this.&add(*args,5,6)==26

def m1=[c:3,d:4]
def map=[a:1,b:2,*:m1]
assert map==[a:1,b:2,c:3,d:4]

def map1 = [a:1, b:2, *:m1, d: 8]
assert map1==[a:1,b:2,c:3,d:8]

def map2 = [a:1, b:2, d: 8, *:m1]
assert map2==[a:1,b:2,d:4,c:3]
