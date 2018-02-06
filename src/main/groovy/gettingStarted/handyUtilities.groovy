package gettingStarted

import javafx.collections.ObservableList

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

/**
 * ConfigSluper
 */
def config = new ConfigSlurper().parse("""
    app.date = new Date()
    app.age = 42
    app {
        name = "Test:${42}"
    }
""")

assert config.app.name == 'Test:42'


config = new ConfigSlurper('development').parse('''
  def portNum = 8088
  environments {
       development {
           app.port = portNum
       }

       test {
           app.port = 8082
       }

       production {
           app.port = 80
       }
  }
''')

assert config.app.port == 8088

slurper = new ConfigSlurper()
slurper.registerConditionalBlock('myProject', 'developers')

config = slurper.parse("""
  sendMail = true

  myProject {
       developers {
           sendMail = false
       }
  }
""")

assert !config.sendMail

config = new ConfigSlurper().parse('''
    app.date = new Date()
    app.age  = 42
    app {
        name = "Test${42}"
    }
''')

def properties = config.toProperties()

assert properties."app.date" instanceof String
assert properties."app.age" == '42'
assert properties."app.name" == 'Test42'

/**
 * expando
 */
def expando = new Expando()
expando.toString = { -> 'John' }
expando.say = { String s -> "John say: $s" }

assert expando as String == 'John'
assert expando.say('hello') == 'John say: hello'

/**
 * Observable list,map and set
 */
def event
def listener = {
    if (it instanceof groovy.util.ObservableList.ElementEvent) {
        event = it
    }
} as PropertyChangeListener

def observable = [1, 2, 3] as groovy.util.ObservableList

observable.addPropertyChangeListener(listener)
observable.add 42
assert event instanceof groovy.util.ObservableList.ElementAddedEvent

assert event.changeType == groovy.util.ObservableList.ChangeType.ADDED
assert event.index == 3
assert event.oldValue == null
assert event.newValue == 42

observable.clear()

assert event instanceof groovy.util.ObservableList.ElementClearedEvent
assert event.values == [1,2,3,42]
assert observable.size() == 0