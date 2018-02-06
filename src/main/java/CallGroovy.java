import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class CallGroovy {
    public static void main(String[] args) throws InterruptedException, ResourceException, ScriptException, MalformedURLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        File dir = new File("/Users/apple/Documents/mydream/groovy/studygroovy/src/main/groovy/integratingGroovy/groovyScriptEngine/");
        Binding bind = new Binding();
        URL[] urls = new URL[1];
        urls[0] = dir.toURI().toURL();
        GroovyScriptEngine engine = new GroovyScriptEngine(urls);

        while (true) {
            Object o = engine.run("ReloadingTest.groovy", bind);
            Method method=o.getClass().getDeclaredMethod("sayHello");
            System.out.println(method.invoke(o));
            Thread.sleep(1000);
        }
    }
}
