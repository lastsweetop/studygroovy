package app

import groovy.time.TimeCategory
import groovy.transform.Synchronized
import groovy.transform.ToString

class TimeWindows {
    List<Date> list = []
    @Synchronized
    boolean request(int second,int count){
        use(TimeCategory) {
            list.each { Date date ->
                if (date + second.second <= new Date()) {
                    list -= date
                }
            }
            if (list.size() == 0 || (list[0] + second.second > new Date() && list.size() < count)) {
                list += new Date()
                return true
            }
            return false
        }
    }
}
def tw=new TimeWindows()
30.times {
    def i = it
    sleep(100)
    new Runnable(){
        @Override
        void run() {
            if(tw.request(10,20)){
                println "hello $i"
                sleep(150)
            }
        }
    }.run()
}
