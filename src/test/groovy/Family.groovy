import groovy.transform.ToString

@ToString
class Family {
    Person father,mother
    def nameOfMother(){
        "${mother.first} ${mother.last}"
    }
}
