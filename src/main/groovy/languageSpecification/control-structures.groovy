package languageSpecification
/*
* conditional structures
 */
def x = 9.3
def result=""

switch (x){
    case "foo":
        result='foo'
    case 'bar':
        result+='bar'
    case [4,5,6,"list"]:
        result = "in list"
        break
    case 12..30:
        result = 'in range'
        break
    case Integer:
        result = 'Integer'
        break
    case {x<10}:
        result = "less than 10"
        break
    case Number:
        result = "Number"
        break
    case ~/fo*/: // toString() representation of x matches the pattern?
        result = "foo regex"
        break
    default:
        result = "default"
}
println(result)
