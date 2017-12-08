package gettingStarted.customAST

@gettingStarted.myast.Shout
void greeting() {
    println "Hello World"
}

void start(){
    println "start"
}

greeting()
start()


class Greeter {
    void greet(){
        println "greet"
    }

}

new  Greeter().greet()