import com.google.common.base.CharMatcher
import com.google.common.base.Splitter
import spock.lang.Specification

class TestDSLSpec extends Specification {
    def "first using maps and Closures"() {
        given:
        def show = {
            println it
            it
        }
        def square_root = { Math.sqrt it }
        def please = { action ->
            [the: { what ->
                [of: { n ->
                    action what(n)
                }]
            }]
        }
        expect:
        please show the square_root of 100
    }

    def "DSL for simplifying one of your existing APIs"() {
        given:
        def list = ['a', 'b', 'c']
        def split = { string ->
            [on: { sep ->
                [trim: { trimChar ->
                    Splitter.on(sep as char).trimResults(CharMatcher.anyOf("$trimChar ")).split(string).toList()
                }]
            }]
        }
        expect:
        split '_a ,_b_ ,c__' on ',' trim '_'
    }
}
