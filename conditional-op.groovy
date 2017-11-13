#!/usr/bin/env groovy

assert (!true)    == false
assert (!'foo')   == false
assert (!'')      == true

def str=1
def result = str ? 'Found' : 'Not found'
println result
