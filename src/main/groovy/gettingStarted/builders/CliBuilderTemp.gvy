#!/usr/bin/env groovy
package gettingStarted.builders

def cli = new CliBuilder(usage:'groovy Greeter [option]')
cli.a(longOpt:'audience',args:1,'greeting audience')
cli.h(longOpt: 'help', 'display usage')

def options = cli.parse(args)
if(options.h){
    cli.usage()
}else {
    println "Hello ${options.a?options.a:'World'}"
}