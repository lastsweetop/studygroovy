/*
* left currying
 */
def nCopies = { int n,String str -> str*n}
def twice = nCopies.curry(2)
assert twice('anc') == nCopies(2,'anc')

/*
* right currying
 */
def anc = nCopies.rcurry('anc')
assert anc(3)== nCopies(3,'anc')

/*
* index based currying
 */
def volume = { double l ,double w,double h->l*w*h}
def fixedWidthVolume=volume.ncurry(1,2d)
assert fixedWidthVolume(3d,4d) == volume(3d,2d,4d)
def fixedWidthAndHeight = volume.ncurry(1,2d,4d)
assert fixedWidthAndHeight(3d) == volume(3d,2d,4d)

