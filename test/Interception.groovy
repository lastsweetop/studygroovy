/**
 * GroovyInterceptable
 */
class Interception implements GroovyInterceptable {
    def defineMethod(){}
    def invokeMethod(String name,Object args){
        return 'invokeMethod'
    }
}

class InterceptionTest extends GroovyTestCase {
    void testCheckIntercetion(){
        def interception = new Interception()
        assert interception.defineMethod() == 'invokeMethod'
        assert interception.someMethod() == 'invokeMethod'
    }
}