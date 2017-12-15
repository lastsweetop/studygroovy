package gettingStarted.delegatesTo

import groovy.transform.TypeChecked

class Mapper<T,U> {
    final T value
    Mapper(T value){
        this.value=value
    }
    @TypeChecked
    U map(@DelegatesTo(type = 'T') Closure<U> closure){
        closure.delegate = value
        closure()
    }
}
