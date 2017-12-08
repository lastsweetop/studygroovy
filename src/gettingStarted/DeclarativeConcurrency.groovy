package gettingStarted.declarativeConcurrency

import groovy.transform.Synchronized
import groovy.transform.WithReadLock
import groovy.transform.WithWriteLock

/**
 * declarative concurrency
 */
class Counter {
    int cpt
    @Synchronized
    int incrementAndGet(){
        ++cpt
    }
    int get(){
        cpt
    }
}
Counter counter=new Counter()
println counter.incrementAndGet()


class Counters {
    public final Map<String,Integer> map=[:].withDefault {0}
    @WithReadLock
    int get(String id){
        map.get(id)
    }

    @WithWriteLock
    void add(String id,int num){
        Thread.sleep(200)
        map[id]+=num
    }
}

def counters = new Counters()
counters.add('a',10)
counters.add('a',10)
println counters['a']
