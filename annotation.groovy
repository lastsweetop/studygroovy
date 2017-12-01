import java.lang.annotation.ElementType
import java.lang.annotation.Target

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import java.lang.reflect.Modifier

import groovy.transform.AnnotationCollector

import groovy.transform.CompileStatic;
import groovy.transform.TypeCheckingMode


@interface SomeAnnotation {
    String value()
}
@interface SomeAnnotation1 {
    String value() default 'something'
}
@interface SomeAnnotation2 {
    int step()
}
@interface SomeAnnotation3 {
    Class appliesTo()
}
@interface SomeAnnotation4 {

}
@interface SomeAnnotations {
    SomeAnnotation[] value()
}
enum DayOfWeek { mon, tue, wed, thu, fri, sat, sun }
@interface Scheduled {
    DayOfWeek dayOfWeek()
}


@SomeAnnotation( 'aa')
void someMethod() {

}

@SomeAnnotation( 'bb')
class SomeClass {

}


@SomeAnnotation('cc') String var


@Target([ElementType.METHOD, ElementType.TYPE])
@interface SomeAnnotation5 {

}


@interface Page {
    String value()
    int statusCode() default 200
}

@Page(value='/home')
void home() {
    // ...
}

@Page('/users')
void userList() {
    // ...
}

@Page(value='error',statusCode=404)
void notFound() {
    // ...
}


@Retention(RetentionPolicy.SOURCE)
@interface SomeAnnotation6 {

}

@Retention(RetentionPolicy.RUNTIME)
@interface OnlyIf {
    Class value()
}

class Tasks {
    Set result = []
    void alwaysExecuted() {
        result << 1
    }
    @OnlyIf({ jdk>=6 })
    void supportedOnlyInJDK6() {
        result << 'JDK 6'
    }
    @OnlyIf({ jdk>=7 && windows })
    void requiresJDK7AndWindows() {
        result << 'JDK 7 Windows'
    }
}

class Runner {
    static <T> T run(Class<T> taskClass) {
        def tasks = taskClass.newInstance()
        def params = [jdk:6, windows: false]
        tasks.class.declaredMethods.each { m ->
            if (Modifier.isPublic(m.modifiers) && m.parameterTypes.length == 0) {
                def onlyIf = m.getAnnotation(OnlyIf)
                if (onlyIf) {
                    Closure cl = onlyIf.value().newInstance(tasks,tasks)
                    cl.delegate = params
                    if (cl()) {
                        m.invoke(tasks)
                    }
                } else {
                    m.invoke(tasks)
                }
            }
        }
        tasks
    }
}

def tasks = Runner.run(Tasks)
assert tasks.result == [1, 'JDK 6'] as Set

@Retention(RetentionPolicy.RUNTIME)
@interface Foo {
   String value()
}
@Retention(RetentionPolicy.RUNTIME)
@interface Bar {
    String value()
}

@Foo
@Bar
@AnnotationCollector
@interface FooBar {}

@Foo('a')
@Bar('b')
class Bob {}

println Bob.annotations

assert Bob.getAnnotation(Foo).value() == 'a'
println Bob.getAnnotation(Bar).value() == 'b'

@FooBar('a')
class Joe {}
println Joe.annotations

assert Joe.getAnnotation(Foo).value() == 'a'
println Joe.getAnnotation(Bar).value() == 'a'

@CompileStatic(TypeCheckingMode.SKIP)
@AnnotationCollector
public @interface CompileDynamic {

}

@AnnotationCollector(processor = "org.codehaus.groovy.transform.CompileDynamicProcessor")
public @interface CompileDynamic2 {
}

@CompileStatic
class CompileDynamicProcessor extends AnnotationCollectorTransform {
    private static final ClassNode CS_NODE = ClassHelper.make(CompileStatic)
    private static final ClassNode TC_NODE = ClassHelper.make(TypeCheckingMode)

    List<AnnotationNode> visit(AnnotationNode collector,
                               AnnotationNode aliasAnnotationUsage,
                               AnnotatedNode aliasAnnotated,
                               SourceUnit source) {
        def node = new AnnotationNode(CS_NODE)
        def enumRef = new PropertyExpression(
            new ClassExpression(TC_NODE), "SKIP")
        node.addMember("value", enumRef)
        Collections.singletonList(node)
    }
}

println 'hello'
