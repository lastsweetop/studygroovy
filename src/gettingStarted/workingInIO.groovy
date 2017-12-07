package gettingStarted.io

import groovy.io.FileType

import java.nio.file.FileVisitResult

/**
 * reading files
 */
def baseDir = "/Users/apple/Documents/mydream/groovy/studygroovy/out/production/studygroovy/"
new File(baseDir,"data.txt").eachLine {line, nb->
    println "Line $nb: $line"
}

def list = new File(baseDir, "data.txt").collect {it}
println list

def array = new File(baseDir, "data.txt") as String[]
println array

def bytes =  new File(baseDir, "data.txt").bytes
println bytes

/**
 * writing files
 */
new File(baseDir,"data1.txt") << """Into the ancient pond
A frog jumps
Water’s sound!
"""

new File(baseDir,"data2.bin").bytes = [97, 97, 97, 10, 98, 98, 98, 10, 99, 99, 99]

/**
 * traversing file trees
 */
baseDir = "/Users/apple/Documents/mydream/groovy/studygroovy/src"
def dir = new File(baseDir)
dir.eachFile { println it.name}
dir.eachFileRecurse { println it.name }
dir.eachFileRecurse(FileType.FILES) { println "$it.name : ${it.length()}"}
dir.eachFileMatch(~/.*/) { println it.name}
println '====================='
dir.traverse { file ->
    if ( file.directory && file.name == 'languageSpecification') {
        FileVisitResult.SKIP_SUBTREE
    }else {
        println file.name
        FileVisitResult.CONTINUE
    }
}

/**
 * datas and objects
 */
baseDir = "/Users/apple/Documents/mydream/groovy/studygroovy/out/production/studygroovy/"
def file = new File(baseDir, 'data.file')
def b = true
def message = 'hello from Groovy'
file.withDataOutputStream {out ->
    out.writeBoolean(b)
    out.writeUTF(message)
}

file.withDataInputStream {inputStream ->
    assert inputStream.readBoolean() == b
    assert inputStream.readUTF() == message
}

class Person implements Serializable{
    String name
    int age
}

def p = new Person(name: 'lucy',age: 18)
file.withObjectOutputStream {it.writeObject(p)}
file.withObjectInputStream {
    def p2 = it.readObject()
    assert p2.name == 'lucy'
    assert p2.age == 18
}

/**
 * executing external processes
 */
def process = "ls -l".execute()
println "Found text ${process.text}"
process = "ls -l".execute()
println '---'
process.in.eachLine {
    println it
}

println '---'
def sout = new StringBuilder()
def serr = new StringBuilder()

process1 = 'ls'.execute()
process2 = 'tr -d o'.execute()
process3 = 'tr -d e'.execute()
process4 = 'tr -d w'.execute()
process2 | process3 | process4
process4.consumeProcessOutput(sout,serr)
[process1,process2,process3].each {it.consumeProcessErrorStream(serr)}

process2.withWriter {it<<'testfile.groovy'}
process4.waitForOrKill(10000)
if(process4.exitValue()){
    println process4.err.text
}else {
    println process4.text
}
println "$sout ------ $serr"

