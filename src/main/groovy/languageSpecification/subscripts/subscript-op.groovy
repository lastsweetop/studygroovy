#!/usr/bin/env groovy
package languageSpecification.subscripts

def list=[0,1,2,3,4]
assert list[2]==2
list[2]=4
assert list[0..2]==[0,1,4]

list[0..2]=[6,6,6]
assert list==[6,6,6,3,4]

class User {
    Long id
    String name
    def getAt(int i) {
        switch (i) {
            case 0: return id
            case 1: return name
        }
        throw new IllegalArgumentException("No such element $i")
    }
    void putAt(int i, def value) {
        switch (i) {
            case 0: id = value; return
            case 1: name = value; return
        }
        throw new IllegalArgumentException("No such element $i")
    }
}
def user = new languageSpecification.objects.User(id: 1, name: 'Alex')
assert user[0] == 1
assert user[1] == 'Alex'
user[1] = 'Bob'
assert user.name == 'Bob'
