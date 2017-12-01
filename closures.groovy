/**
 * closures in GStrings
 */
def x=1
def gs = "x=${x}"
assert gs == 'x=1'
x=2
assert gs != 'x=2'

def gs1 = "x=${->x}"
x=1
assert gs1 == 'x=1'
x=2
assert gs1 == 'x=2'

class Person21 {
    String name
    String toString() {
        name
    }
}
def sam = new Person21(name:'Sam')
def lucy = new Person21(name:'Lucy')

def p = sam
def gs2 = "Name : ${p}"
assert gs2 == 'Name : Sam'
p == lucy
assert gs2 == 'Name : Sam'
sam.name = 'Lily'
assert gs2 == 'Name : Lily'

def gs3= "Name : ${->p}"
p = sam
assert gs3 == 'Name : Lily'
p = lucy
assert gs3 == 'Name : Lucy'
