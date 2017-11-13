def fib(int n) {
  n<2?1:fib(n-1)+fib(n-2)
}

assert fib(10)==89

println 'hello'

int power(int n){2**n}
println "2^6=${power(6)}"
