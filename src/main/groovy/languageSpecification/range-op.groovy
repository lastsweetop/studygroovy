#!/usr/bin/env groovy
package languageSpecification

def range=0..5

assert range.collect()==[0,1,2,3,4,5]
assert range instanceof List
assert range.size()==6
assert (0..<5)==[0,1,2,3,4]

assert ('a'..'d').collect() == ['a','b','c','d']
