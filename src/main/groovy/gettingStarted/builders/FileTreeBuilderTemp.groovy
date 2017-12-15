package gettingStarted.builders

def tmpDir = File.createTempDir()
def fileTreeBuilder = new FileTreeBuilder(tmpDir)
fileTreeBuilder.src {
    main {
        groovy {
            'Foo.groovy'('println "Hello"')
        }
    }
    test {
        groovy {
            'FooTest.groovy'('class FooTest extends GroovyTestCase {}')
        }
    }
}

assert new File(tmpDir, '/src/main/groovy/Foo.groovy').text == 'println "Hello"'
assert new File(tmpDir, '/src/test/groovy/FooTest.groovy').text == 'class FooTest extends GroovyTestCase {}'
println tmpDir.path