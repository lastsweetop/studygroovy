package slurper

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def jsonslurper = new JsonSlurper()

def object = jsonslurper.parseText '''
    { "simple": 123,
      "fraction": 123.66,
      "exponential": 123e12
    }'''

def json = JsonOutput.toJson(object)
println JsonOutput.prettyPrint(json)