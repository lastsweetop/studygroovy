import groovy.lang.Binding;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import java.util.ArrayList;
import java.util.Arrays;

public class array extends Script {
    public static void main(String[] args) {
        new array(new Binding(args)).run();
    }

    public Object run() {

        String[] arrStr = new String[]{"ananas", "banana", "kiwi"};

        assert arrStr instanceof String[];

        Integer[][] matrix3 = new Integer[3][3];
        assert DefaultGroovyMethods.invokeMethod(matrix3, "size", new Object[0]) == 3;

        Integer[][] matrix2;
        matrix2 = new ArrayList<ArrayList<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(1, 2)), new ArrayList<Integer>(Arrays.asList(3, 4))));
        assert matrix2 instanceof Integer[][];

        ArrayList<String> names = new ArrayList<String>(Arrays.asList("Cédric", "Guillaume", "Jochen", "Paul"));
        assert names.get(0).equals("Cédric");

        DefaultGroovyMethods.leftShift(names, "lily");
// assert names[4] == 'lily'
        println(names);

        names.set(2, "Blackdrag");
        assert names.get(2).equals("Blackdrag");
        return null;

    }

    public array(Binding binding) {
        super(binding);
    }

    public array() {
        super();
    }
}
