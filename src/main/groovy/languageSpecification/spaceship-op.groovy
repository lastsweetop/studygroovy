#!/usr/bin/env groovy
package languageSpecification

assert (1 <=> 1) == 0
assert (1 <=> 2) == -1
assert (2 <=> 1) == 1

assert ('a'..'d')==['a','b','c','d']
