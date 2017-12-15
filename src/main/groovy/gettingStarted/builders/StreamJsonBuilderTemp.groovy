package gettingStarted.builders

import groovy.json.JsonOutput
import groovy.json.StreamingJsonBuilder
import net.javacrumbs.jsonunit.JsonAssert

String carRecords = '''
    {
        "records": {
        "car": {
            "name": "HSV Maloo",
            "make": "Holden",
            "year": 2006,
            "country": "Australia",
            "record": {
              "type": "speed",
              "description": "production pickup truck with speed of 271kph"
            }
          }
      }
    }
'''

def writer = new StringWriter()
def sjBuilder = new StreamingJsonBuilder(writer)
sjBuilder.records {
    car {
        name 'HSV Maloo'
        make 'Holden'
        year 2006
        country 'Australia'
        record {
            type 'speed'
            description 'production pickup truck with speed of 271kph'
        }
    }
}
def json = JsonOutput.prettyPrint(writer.toString())
JsonAssert.assertJsonEquals(json,carRecords)