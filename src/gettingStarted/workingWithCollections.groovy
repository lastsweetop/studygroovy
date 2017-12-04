package gettingStarted

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

list = ['a','b','c','d','e','f','b','b','a']
list.remove(2)
assert list == ['a','b','d','e','f','b','b','a']

list= ['a','b','c','b','b']
list.remove('c')
list.remove('b')
assert !list.remove('z')
assert list == ['a','b','b']

list = [1,2,3,4,5,6,2,2,1]
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
assert 'a' in ['a','b','c']
assert ['a','b','c'].contains('a')
assert [1,3,4].containsAll([1,3])
assert [1, 2, 3, 3, 3, 3, 4, 5].count(3) == 4

assert [1, 2, 3, 3, 3, 3, 4, 5].count {it%2==0} == 2

assert [1,2,4,6,8,10,12].intersect([1,3,6,9,12]) == [1,6,12]

assert [1, 2, 3].disjoint([4,5,6])

/**
 * sorting
 */
assert [6, 3, 9, 2, 7, 1, 5].sort() == [1, 2, 3, 5, 6, 7, 9]

list=['abc', 'z', 'xyzuvw', 'Hello', '321']
assert list.sort {it.size()}==['z', 'abc', '321', 'Hello', 'xyzuvw']

list=[7, 4, -6, -1, 11, 2, 3, -9, 5, -13]
assert list.sort { a1, b1 ->
    a1 == b1 ? 0 : Math.abs(a1) < Math.abs(b1) ? -1 : 1
} == [-1, 2, 3, 4, 5, -6, 7, -9, 11, -13]

/**
 * duplicating elements
 */
assert [1, 2, 3]*3 == [1,2,3,1,2,3,1,2,3]
assert [1,2,3].multiply(2) == [1,2,3,1,2,3]
assert [1,2,3]*.multiply(2) == [2,4,6]



