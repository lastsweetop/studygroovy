given:
    def x=1
    def y=2
when:
    def z=x+y
then:
    assert z==3

for (int i = 0; i < 10; i++) {
    for (int j = 0; j < i; j++) {
        println "j=$j"
        if(j==5){
            break exit
        }
    }
    exit: println "i=$i"
}
