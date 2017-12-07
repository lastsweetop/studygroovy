package gettingStarted.collections

/**
 * list literals
 */
def list = [5, 6, 7, 8]
assert list.set(2, 10) == 7

list.eachWithIndex { int entry, int i ->
    println "$i:$entry"
}

assert [1, 2, 3].collect { it * 2 } == [2, 4, 6]

assert [1, 2, 3]*.multiply(3) == [1, 2, 3].collect { it.multiply(3) }

list = [0]
assert [1, 2, 3].collect(list) { it * 2 } == [0, 2, 4, 6]
assert list == [0, 2, 4, 6]

/**
 * filtering and searching
 */
assert [1, 2, 3].find { it > 1 } == 2
assert [1, 2, 3].findAll { it > 1 } == [2, 3]

assert ['a', 'b', 'c', 'd', 'e'].findIndexOf { it in ['c', 'e', 'g'] } == 2

assert [1, 2, 3].every { it < 4 }
assert [1, 2, 3].any { it < 2 }
assert [1, 2, 3].sum() == 6
assert [1, 2, 3].sum { it + 1 } == 9
assert ['a', 'b', 'c', 'd', 'e'].sum() == 'abcde'
assert [['a', 'b'], ['c', 'd']].sum() == ['a', 'b', 'c', 'd']
assert ['a', 'b', 'c', 'd', 'e'].sum { (char) it - (char) 'a' } == 10

assert [1, 2, 3].sum(1000) == 1006
assert ['1', '2', '3'].join('-') == '1-2-3'
assert [1, 2, 3].inject('count:') { str, item ->
    str + item
} == 'count:123'

assert [1, 2, 3].inject(10) { seed, item -> seed + item
} == 16

list = [9, 5, 3, 1, 7]
assert list.min() == 1
assert list.max() == 9

assert ['x', 'y', 'a', 'z'].min() == 'a'
list = ['aa', 'bbbb', 'ccccccc', 'dddd', 'eee']
assert list.max { it.size() } == 'ccccccc'
assert list.min { it.size() } == 'aa'

list = [7, 4, 9, -6, -1, 11, 2, 3, -9, 5, -13]
def mc = {
    a, b -> a == b ? 0 : (Math.abs(a) < Math.abs(b)) ? -1 : 1
}
assert list.max(mc) == -13
assert list.min(mc) == -1

/**
 * adding and removing elements
 */
list = []
assert list.empty

list << 5
assert list.size() == 1
list << 7 << 'i' << 11
assert list == [5, 7, 'i', 11]
list << ['m', 'o']
assert list == [5, 7, 'i', 11, ['m', 'o']]

assert [1, 2] << 3 << [4, 5] << 6 == [1, 2, 3, [4, 5], 6]
assert [1, 2, 3] << 4 == [1, 2, 3].leftShift(4)

assert [1, 2] + 3 + [4, 5] + 6 == [1, 2, 3, 4, 5, 6]
assert [1, 2].plus(3).plus([4, 5]).plus(6) == [1, 2, 3, 4, 5, 6]

def a = [1, 2, 3]
a += 4
a += [5, 6]
assert a == [1, 2, 3, 4, 5, 6]

assert [1, *[333, 444], 222] == [1, 333, 444, 222]
assert [*[1, 2, 3]] == [1, 2, 3]

assert [1, [2, 3, [4, 5], 6], 7, [8, 9]].flatten() == [1, 2, 3, 4, 5, 6, 7, 8, 9]

list = [1, 2]
list.add(3)
list.addAll([4, 5])
assert list == [1, 2, 3, 4, 5]

list = [1, 2]
list.add(1, 3)
assert list == [1, 3, 2]
list.addAll(2, [5, 4])
assert list == [1, 3, 5, 4, 2]

list = ['a', 'b', 'z', 'e', 'u', 'v', 'g']
list[8] = 'x'
assert list == ['a', 'b', 'z', 'e', 'u', 'v', 'g', null, 'x']

assert ['a', 'b', 'c', 'b', 'b'] - 'c' == ['a', 'b', 'b', 'b']
assert ['a', 'b', 'c', 'b', 'b'] - 'b' == ['a', 'c']

assert ['a', 'b', 'c', 'b', 'b'] - ['b', 'c'] == ['a']

list = [1, 2, 3, 4, 3, 2, 1]
list -= 3
assert list == [1, 2, 4, 2, 1]
assert ((list -= [2, 4])) == [1, 1]

list = ['a', 'b', 'c', 'd', 'e', 'f', 'b', 'b', 'a']
list.remove(2)
assert list == ['a', 'b', 'd', 'e', 'f', 'b', 'b', 'a']

list = ['a', 'b', 'c', 'b', 'b']
list.remove('c')
list.remove('b')
assert !list.remove('z')
assert list == ['a', 'b', 'b']

list = [1, 2, 3, 4, 5, 6, 2, 2, 1]
assert list.remove(2) == 3
assert list == [1, 2, 4, 5, 6, 2, 2, 1]

assert list.removeElement(2)
assert list == [1, 4, 5, 6, 2, 2, 1]

assert !list.removeElement(8)
assert list == [1, 4, 5, 6, 2, 2, 1]

assert list.removeAt(1) == 4
assert list == [1, 5, 6, 2, 2, 1]

list.clear()
assert list == []

/**
 * set operations
 */
assert 'a' in ['a', 'b', 'c']
assert ['a', 'b', 'c'].contains('a')
assert [1, 3, 4].containsAll([1, 3])
assert [1, 2, 3, 3, 3, 3, 4, 5].count(3) == 4

assert [1, 2, 3, 3, 3, 3, 4, 5].count { it % 2 == 0 } == 2

assert [1, 2, 4, 6, 8, 10, 12].intersect([1, 3, 6, 9, 12]) == [1, 6, 12]

assert [1, 2, 3].disjoint([4, 5, 6])

/**
 * sorting
 */
assert [6, 3, 9, 2, 7, 1, 5].sort() == [1, 2, 3, 5, 6, 7, 9]

list = ['abc', 'z', 'xyzuvw', 'Hello', '321']
assert list.sort { it.size() } == ['z', 'abc', '321', 'Hello', 'xyzuvw']

list = [7, 4, -6, -1, 11, 2, 3, -9, 5, -13]
assert list.sort { a1, b1 ->
    a1 == b1 ? 0 : Math.abs(a1) < Math.abs(b1) ? -1 : 1
} == [-1, 2, 3, 4, 5, -6, 7, -9, 11, -13]

/**
 * duplicating elements
 */
assert [1, 2, 3] * 3 == [1, 2, 3, 1, 2, 3, 1, 2, 3]
assert [1, 2, 3].multiply(2) == [1, 2, 3, 1, 2, 3]
assert [1, 2, 3]*.multiply(2) == [2, 4, 6]

/**
 * map literals
 */
def map = [name: 'Gromit', likes: 'cheese', id: 1234]
assert map['name'] == 'Gromit'
assert map.likes == 'cheese'
assert map.get('id') == 1234
assert map instanceof Map

def emptyMap = [:]
assert emptyMap.size() == 0
emptyMap.put("foo", 5)
assert emptyMap.size() == 1
assert emptyMap.get("foo") == 5

a = 'Bob'
def ages = [a: 43]
assert ages['Bob'] == null
assert ages['a'] == 43

ages[(a)] = 22
assert ages['Bob'] == 22

map = [
        simple : 123,
        complex: [a: 1, b: 2]
]
def map2 = map.clone()
assert map2.simple == map.simple
assert map2.complex == map.complex

map2.complex.c = 3
assert map.complex.c == 3
map2.code = 4
assert map.code == null

assert map.class == null
assert map.getClass() == LinkedHashMap

/**
 * literals on maps
 */
map = [
        Bob  : 42,
        Alice: 54,
        Max  : 33
]

map.each { entry ->
    println "Name: $entry.key | Age: $entry.value"
}

map.eachWithIndex { entry, i ->
    println "$i - Name: $entry.key | Age: $entry.value"
}

map.each { key, value ->
    println "Name: $key | Age: $value"
}

map.eachWithIndex { key, value, int i ->
    println "$i - Name: $key | Age: $value"
}

def defaults = [1: 'a', 2: 'b', 3: 'c', 4: 'd']
def overrides = [2: 'z', 5: 'x', 13: 'x']

/**
 * adding and removing elements
 */
def result = new LinkedHashMap(defaults)
result.put(15, 't')
result[17] = 'u'
result.putAll(overrides)
assert result == [1: 'a', 2: 'z', 3: 'c', 4: 'd', 5: 'x', 13: 'x', 15: 't', 17: 'u']

def m = [1: 'a', 2: 'b']
assert m.get(2) == 'b'
m.clear()
assert m == [:]

def key = 'some key'
map = [:]
def gstringKey = "${key.toUpperCase()}"
map.put(gstringKey, 'value')
assert map.get('SOME KEY') == null

/**
 * entries
 */
map = [1: 'a', 2: 'b', 3: 'c']

def entries = map.entrySet()
entries.each { entry ->
    assert entry.key in [1, 2, 3]
    assert entry.value in ['a', 'b', 'c']
}

def keys = map.keySet()
assert keys == [1, 2, 3] as Set

/**
 * filtering and searching
 */
def people = [
        1: [name: 'Bob', age: 32, gender: 'M'],
        2: [name: 'Johnny', age: 36, gender: 'M'],
        3: [name: 'Claire', age: 21, gender: 'F'],
        4: [name: 'Amy', age: 54, gender: 'F']
]
def bob = people.find { it.value.name = 'Bob' }
def females = people.findAll { it.value.gender == 'F' }

def ageOfBob = bob.value.age
def agesOfFemales = females.collect { it.value.age }

assert ageOfBob == 32
assert agesOfFemales == [21, 54]

def agesOfMales = people.findAll { id, person ->
    person.gender == 'M'
}.collect { id, person ->
    person.age
}
assert agesOfMales == [32, 36]

assert people.every { id, person ->
    person.age > 18
}
assert people.any { id, person ->
    person.age == 54
}

/**
 * grouping
 */
assert ['a', 7, 'b', [2, 3]].groupBy {
    it.class
} == [(String)   : ['a', 'b'],
      (Integer)  : [7],
      (ArrayList): [[2, 3]]
]

assert [[name: 'Clark', city: 'London'], [name: 'Sharma', city: 'London'],
        [name: 'Maradona', city: 'LA'], [name: 'Zhang', city: 'HK'],
        [name: 'Ali', city: 'HK'], [name: 'Liu', city: 'HK']].groupBy { it.city } == [
        London: [[name: 'Clark', city: 'London'], [name: 'Sharma', city: 'London']],
        LA    : [[name: 'Maradona', city: 'LA']],
        HK    : [[name: 'Zhang', city: 'HK'], [name: 'Ali', city: 'HK'], [name: 'Liu', city: 'HK']]
]

/**
 * range
 */
for (i in 1..10) {
    println "Hello ${i}"
}
('a'..'e').each { i ->
    println "Hello ${i}"
}
def (years, interestRate) = [12, 0]
switch (years) {
    case 1..10: interestRate = 0.076; break;
    case 11..25: interestRate = 0.052; break;
    default: interestRate = 0.037;
}
assert interestRate == 0.052

/**
 * GPath Support
 */
def listOfMaps = [['a': 11, 'b': 12], ['a': 21, 'b': 22]]
assert listOfMaps.a == [11, 21]
assert listOfMaps*.a == [11, 21]

listOfMaps = [['a': 11, 'b': 12], ['a': 21, 'b': 22], null]
assert listOfMaps*.a == [11, 21, null]
assert listOfMaps.a == [11, 21]
assert listOfMaps*.a == listOfMaps.collect { it?.a }

/**
 * spread operator
 */
assert ['z': 900, *: ['a': 100, 'b': 200], 'a': 300] == ['a': 300, 'b': 200, 'z': 900]
assert [*: [3: 3, *: [5: 5]], 7: 7] == [3: 3, 5: 5, 7: 7]
def f = { [1: 'u', 2: 'v', 3: 'w'] }
assert [*: f(), 10: 'zz'] == [1: 'u', 10: 'zz', 2: 'v', 3: 'w']

f = { mp ->
    mp.c
}
assert f(*: ['a': 10, 'b': 20, 'c': 30], d: 40) == 30

f = { h, i, j, k ->
    [h, i, j, k]
}
assert f('e': 100, *[4, 5], *: ['a': 10, 'b': 20, 'c': 30], 6) ==
        [["e": 100, "b": 20, "c": 30, "a": 10], 4, 5, 6]

/**
 * '*.' operator
 */
assert [1, 3, 5] == ['a', 'few', 'words']*.size()

class Person {
    String name
    int age
}

def persons = [new Person(name: 'Hugo', age: 17), new Person(name: 'Sandra', age: 19)]
assert persons*.age == [17, 19]

/**
 * slicing with subscript operator
 */
def text = 'nice cheese gromit!'
def x = text[2]

assert x == 'c'
assert x instanceof String

assert text[5..10] == 'cheese'

list = [10, 11, 12, 13]
assert list[2, 3] == [12, 13]

list = 100..200
assert list[1, 3, 20..25, 30] == [101, 103, 120, 121, 122, 123, 124, 125, 130]

list = ['a', 'x', 'x', 'd']
list[1..2] = ['b', 'c']
assert list == ['a', 'b', 'c', 'd']

assert text[-1] == '!'
assert text[-7..-2] == 'gromit'

assert text[3..1] == 'eci'
assert text[-1..0] == '!timorg eseehc ecin'