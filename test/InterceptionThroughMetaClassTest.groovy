import groovy.xml.Entity

class InterceptionThroughMetaClassTest extends GroovyTestCase{
    void testPOJOMetaClassInterception(){
        String invoking = 'aaa'
        invoking.metaClass.invokeMethod = { String name,Object args ->
            'invoked'
        }

        assert invoking.length() == 'invoked'
        assert invoking.someMethod() == 'invoked'
    }
    
    void testPOGOMetaClassInterception(){
        Entity entity=new Entity('hello')
        entity.metaClass.invokeMethod = {String name,Object args ->
            'invoked'
        }

        assert entity.each { it } == 'invoked'
        assert entity.someMehod() == 'invoked'
    }
}
