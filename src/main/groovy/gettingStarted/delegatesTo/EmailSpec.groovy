package gettingStarted.delegatesTo

class EmailSpec {
    void from(String from){
        println "From : $from"
    }
    void to(String... to){
        println "To : $to"
    }

    void subject(String subject){
        println "Subject : $subject"
    }

    void body(@DelegatesTo(strategy = Closure.DELEGATE_ONLY,value = BodySpec) Closure closure){
        def body = new BodySpec()
        def code = closure.rehydrate(body, this, this)
        code.resolveStrategy=Closure.DELEGATE_ONLY
        code()
    }
}

