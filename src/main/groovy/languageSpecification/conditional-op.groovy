#!/usr/bin/env groovy
package languageSpecification

assert (!true)    == false
assert (!'foo')   == false
assert (!'')      == true

def str=1
def result = str ? 'Found' : 'Not found'
println result
