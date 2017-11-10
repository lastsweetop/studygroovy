#!/usr/bin/env groovy

def numbers=[1,2,3]

assert numbers instanceof List
assert numbers.size() == 3

assert numbers instanceof ArrayList

def linkedList=[2,3,4] as LinkedList
assert linkedList instanceof LinkedList

def letters=['a','b','c','d']

assert letters[0]=='a'
assert letters[1]=='b'

assert letters[-1]=='d'
assert letters[-2]=='c'

letters[2]='C'
assert letters[2]=='C'

letters << 'e'
assert letters[4]=='e'
assert letters[-1]=='e'

assert letters[1,3]==['b','d']
assert letters[1..3]==['b','C','d']

def mutil=[[1,2],[3,4]]
assert mutil[0][1]==2
