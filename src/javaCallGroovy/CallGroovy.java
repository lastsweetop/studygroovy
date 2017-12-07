package javaCallGroovy;
import gettingStarted.compileTime.Person6;

public class CallGroovy {
    public static void main(String[] args) {
        System.out.println(new Person6(Person6.createInitializer().firstName("a").lastName("b").age(14)));
    }
}
