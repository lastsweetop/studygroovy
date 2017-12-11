import org.junit.Test

import static groovy.test.GroovyAssert.shouldFail

class JUnit4ExampleTests {
    @Test
    void indexOutOfBoundsAccess() {
        def numbers = [1,2,3,4]
        shouldFail {
            numbers.get(4)
        }
    }

    @Test
    void shouldFailReturn() {
        def e = shouldFail {
            throw new RuntimeException('foo',
                    new RuntimeException('bar'))
        }
        assert e instanceof RuntimeException
        assert e.message == 'foo'
        assert e.cause.message == 'bar'
    }
}
