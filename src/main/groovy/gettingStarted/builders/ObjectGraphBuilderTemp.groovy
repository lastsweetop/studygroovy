package gettingStarted.builders

import groovy.transform.Immutable

def builder = new ObjectGraphBuilder()
builder.classLoader=this.class.classLoader
builder.classNameResolver='builders'
builder.newInstanceResolver={Class klass, Map attributes->
    if(klass.isAnnotationPresent(Immutable)){
        def o = klass.newInstance(attributes)
        attributes.clear()
        o
    }
    klass.newInstance()
}

def telecom=builder.company(name:'telecom'){
    address(id:'a1',line1:'Post street')
    3.times {
        employee(id:it.toString(),name: "Drone $it",address:a1) {
            address(refId:'a1')
        }
    }
}

assert telecom
assert telecom instanceof Company
assert telecom.name == 'telecom'
assert telecom.employees.size() == 3
def employee = telecom.employees[0]
assert employee.name == 'Drone 0'
assert employee.address instanceof Address
println telecom

