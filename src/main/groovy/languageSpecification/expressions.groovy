package languageSpecification

import java.lang.reflect.Method

/**
 * Object navigation
 */
void aMethodFoo() {
    println("this is aMethodFoo")
}
assert ["aMethodFoo"] == this.class.methods.name.grep(~/.*Foo/)

void aMethodBar() { println "This is aMethodBar." }
void anotherFooMethod() { println "This is anotherFooMethod." }
void aSecondMethodBar() { println "This is aSecondMethodBar." }

assert ['aMethodBar', 'aSecondMethodBar'] as Set == this.class.methods.name.grep(~/.*Bar/) as Set

assert 'aSecondMethodBar' == this.class.methods.name.grep(~/.*Bar/).sort()[1]

/**
 * GPath for XML navigation
 */
def xmlText = """
              | <root>
              |   <level>
              |      <sublevel id='1'>
              |        <keyVal>
              |          <key>mykey</key>
              |          <value>value 123</value>
              |        </keyVal>
              |      </sublevel>
              |      <sublevel id='2'>
              |        <keyVal>
              |          <key>anotherKey</key>
              |          <value>42</value>
              |        </keyVal>
              |        <keyVal>
              |          <key>mykey</key>
              |          <value>fizzbuzz</value>
              |        </keyVal>
              |      </sublevel>
              |   </level>
              | </root>
              """
def root = new XmlSlurper().parseText(xmlText.stripMargin())
assert root.level.size() == 1
assert root.level.sublevel.size() == 2
assert root.level.sublevel.findAll {it.@id == 1}.size() == 1
assert root.level.sublevel[1].keyVal[0].key.text() == 'anotherKey'