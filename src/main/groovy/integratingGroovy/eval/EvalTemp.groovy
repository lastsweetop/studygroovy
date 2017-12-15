package integratingGroovy.eval

assert Eval.me('33*3') == 99
assert Eval.me("""'foo'.toUpperCase()""") == 'FOO'

assert Eval.me('k', 2, '4*k') == 8
assert Eval.x(4,'2*x')==8
assert Eval.xy(4, 2, 'x*y') == 8