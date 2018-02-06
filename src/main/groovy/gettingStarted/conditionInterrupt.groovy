package gettingStarted

import groovy.transform.ConditionalInterrupt

@ConditionalInterrupt(value = {Quotas.disallow('user')})
class UserCode {
    void doSomething(){
        int i=0
        while (true) {
            println "Consuming resources ${i++}"
        }
    }
}
class Quotas {
    static def quotas = [:].withDefault { 10 }

    static boolean disallow(String username) {
        println "Checking quota for $username"
        (quotas[username]--)<0
    }
}

assert Quotas.quotas['user'] == 10
def t2 = Thread.start {
    new UserCode().doSomething()
}
t2.join(5000)
assert !t2.alive
assert  Quotas.quotas['user'] < 0




