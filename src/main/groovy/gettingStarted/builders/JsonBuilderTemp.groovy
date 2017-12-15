package gettingStarted.builders

import groovy.json.JsonBuilder

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

def jsonBuilder = new JsonBuilder()
jsonBuilder.records {
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
