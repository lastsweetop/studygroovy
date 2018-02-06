package languageSpecification

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

class Computer {
    int compute(String str){
        str.length()
    }
    String compute(int n){
        String.valueOf(n)
    }
}

@TypeChecked
@CompileStatic
void test(){
    def computer = new Computer()
    computer.with {
        assert compute(compute('foobar')) == '6'
    }
}
Computer.metaClass.compute={
    String str -> new Date().toString()
}
test()