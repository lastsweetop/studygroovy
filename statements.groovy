import groovy.transform.Immutable

/*
* multiple assignment
 */
def (a,b,c) =[1,2,'aa']
assert a==1 && b==2 && c=='aa'

def (int i,String j) = [1,'dd']
assert i==1 && j=='dd'

def nums = [1,3,5]
(a,b,c)=nums
assert a==1 && b==3 && c==5

def (_,month,year) = '18th June 2009'.split()
assert "In $month of $year" == 'In June of 2009'

/*
* overflow and underflow
 */
(a,b,c) = [1,2]
assert a==1 && b==2 && c==null

(a,b) = [1,2,3]
assert a==1 && b==2

/*
* object destructuring with multiple assignment
 */
@Immutable
class Coordinates {
    double latitude
    double longitude
    double getAt(int idx) {
        if(idx == 0) latitude
        else if(idx==1) longitude
        else throw new Exception("wrong coordinates index,use 0 or 1")
    }
}

def coordinates = new Coordinates(latitude: 42.1,longitude: 38.5)
def (la,lo) = coordinates
assert la==42.1 && lo==38.5
