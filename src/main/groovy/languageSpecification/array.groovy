#!/usr/bin/env groovy
package languageSpecification

def arrStr=['ananas','banana','kiwi'] as String[]

assert arrStr instanceof String[]

def matrix3 = new Integer[3][3]
assert matrix3.size() == 3

Integer[][] matrix2
matrix2 = [[1, 2], [3, 4]]
assert matrix2 instanceof Integer[][]

def names = ['Cédric', 'Guillaume', 'Jochen', 'Paul']
assert names[0] == 'Cédric'

names << 'lily'
// assert names[4] == 'lily'
println(names)

names[2] = 'Blackdrag'
assert names[2] == 'Blackdrag'
