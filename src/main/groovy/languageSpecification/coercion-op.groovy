package languageSpecification

Integer x = 123
String s = (String) x

s=x as String


class Identifiable {
    String name
}
class User {
    Long id
    String name
    def asType(Class target) {
        if (target==Identifiable) {
            return new Identifiable(name: name)
        }
        throw new ClassCastException("User cannot be coerced into $target")
    }
}
def u = new languageSpecification.objects.User(name: 'Xavier')
def p = u as Identifiable
assert p instanceof Identifiable
assert !(p instanceof languageSpecification.objects.User)
println p.name
