package gettingStarted.delegatesTo

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked


//Delegation strategy
@CompileStatic
void email(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = EmailSpec) Closure cl) {
    def email = new EmailSpec()
    def code = cl.rehydrate(email, this, this)
    code.resolveStrategy = Closure.DELEGATE_ONLY
    code()
}

@TypeChecked
void sendEmail() {
    email {
        from 'dsl-guru@mycompany.com'
        to 'john.doe@waitaminute.com'
        subject 'The pope has resigned!'
        body {
            p 'Really, the pope has resigned!'
        }
    }
}

sendEmail()

//Delegate to parameter
def exec(@DelegatesTo.Target Object target, @DelegatesTo Closure code) {
    def clone = code.rehydrate(target, this, this)
    clone()
}

def email = new EmailSpec()

exec(email) {
    from 'dsl-guru@mycompany.com'
    to 'john.doe@waitaminute.com'
}

def greeter = new Greeter()
exec(greeter) {
    sayHello()
}
greeter.with {
    sayHello()
}

//Multiple closures
@TypeChecked
void fooBarBaz(
        @DelegatesTo.Target('foo') foo,
        @DelegatesTo.Target('bar') bar,
        @DelegatesTo.Target('baz') baz,
        @DelegatesTo(target = 'foo') Closure cl1,
        @DelegatesTo(target = 'bar') Closure cl2, @DelegatesTo(target = 'baz') Closure cl3) {
    cl1.rehydrate(foo, this, this).call()
    cl2.rehydrate(bar, this, this).call()
    cl3.rehydrate(baz, this, this).call()
}

def a = new Foo()
def b = new Bar()
def c = new Baz()


fooBarBaz(a, b, c, {
    foo('hello')
}, {
    bar(123)
}, {
    baz(new Date())
})

// Delegating to a generic type
public <T> void configure(@DelegatesTo.Target List<T> elements,@DelegatesTo(strategy = Closure.DELEGATE_FIRST,genericTypeIndex = 0) Closure configuration) {
    elements.each { e ->
        def clone = configuration.rehydrate(e, this, this)
        clone.resolveStrategy = Closure.DELEGATE_FIRST
        clone()
    }
}

List<Realm> list = []
3.times { list.add(new Realm()) }
configure(list) {
    name = 'sweetop'
}
list.every {
    it.name == 'sweetop'
}

//Delegating to an arbitrary type
def mapper = new Mapper<String, Integer>('hello')
assert mapper.map {
    length()
} == 5
