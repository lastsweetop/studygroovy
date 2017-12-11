import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.StubFor
import groovy.transform.NotYetImplemented

class MyTestCase extends GroovyTestCase {
    void testAssertions() {
        assertTrue(1 == 1)
        assertEquals("test", "test")

        def x = "42"
        assertNotNull "x must not be null", x
        assertNull null

        assertSame x, x
    }

    void testMapCoercion(){
        def service = [
                convert: { String key ->
                    'some test' }
        ] as TranslationService
        assert service.convert('key.text') == 'some test'
    }

    void testClosureCoercion(){
        def service={String key ->
            'some test'
        } as TranslationService
        assert service.convert('key.text') == 'some test'
    }

    void testMockFor(){
        def mock = new MockFor(Person)
        mock.demand.getFirst {'last'}
        mock.demand.getLast {'sweetop'}
        mock.use {
            def mary = new Person(first: 'Mary', last: 'Smith')
            def f = new Family(mother: mary)
            assert f.nameOfMother() == 'last sweetop'
        }
    }
    
    void testStubFor(){
        def stub = new StubFor(Person)
        stub.demand.with {
            getFirst {'last'}
            getLast {'sweetop'}
        }
        stub.use {
            def john = new Person(first: 'Jhon',last: 'Smith')
            def f = new Family(father: john)
            assert f.father.first == 'last'
            assert f.father.last == 'sweetop'
        }
    }

    void testEMC(){
        Person.metaClass.getFirst = {'last'}
        Person.metaClass.getLast = {'sweetop'}
        def mary = new Person(first: 'Mary', last: 'Smith')
        def f = new Family(mother: mary)
        assert f.nameOfMother() == 'last sweetop'
    }

    void testCombinations() {
        def combinations = [[1, 2], [5, 6, 7], [3, 8, 9]].combinations()
        [[1, 2], [5, 6, 7], [3, 8, 9]].eachCombination { println it }
    }

    void testScriptAssertions() {
        assertScript '''
        def x = 1
        def y = 2

        assert x + y == 3
    '''
    }

    void testInvalidIndexAccess1() {
        def numbers = [1,2,3,4]
        shouldFail {
            numbers.get(4)
        }
    }

    void testInvalidIndexAccess2() {
        def numbers = [1,2,3,4]
        shouldFail IndexOutOfBoundsException, {
            numbers.get(4)
        }
    }

    void testInvalidIndexAccess3() {
        def numbers = [1,2,3,4]
        def msg = shouldFail IndexOutOfBoundsException, {
            numbers.get(4)
        }
        assert msg.contains('Index: 4, Size: 4')
    }

    void testNotYetImplemented1() {
        if (notYetImplemented()) return

        assert 1 == 2
    }

    @NotYetImplemented
    void testNotYetImplemented2() {
        assert 1 == 2
    }

}
