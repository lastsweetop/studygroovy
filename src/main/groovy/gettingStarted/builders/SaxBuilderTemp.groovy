package gettingStarted.builders

import groovy.transform.ToString

@ToString(includeSuper = true)
class LogHandler extends org.xml.sax.helpers.DefaultHandler {

    String log = ''

    void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) {
        log += "Start Element: $localName, "
    }

    void endElement(String uri, String localName, String qName) {
        log += "End Element: $localName, "
    }
}

def handler = new LogHandler()
def builder = new groovy.xml.SAXBuilder(handler)

builder.root() {
    helloWorld()
}

println handler.toString()