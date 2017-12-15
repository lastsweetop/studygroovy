package gettingStarted.builders

import groovy.xml.StaxBuilder
import org.codehaus.jettison.mapped.MappedNamespaceConvention
import org.codehaus.jettison.mapped.MappedXMLStreamWriter

import javax.xml.stream.XMLOutputFactory

//
def writer = new StringWriter()
def xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer)
def builder = new StaxBuilder(xmlWriter)

builder.root(attr:1) {
    elem1('hello')
    elem2('world')
}

assert writer.toString() == '<?xml version="1.0" ?><root attr="1"><elem1>hello</elem1><elem2>world</elem2></root>'

writer=new StringWriter()
def mappedWriter = new MappedXMLStreamWriter(new MappedNamespaceConvention(), writer)
builder=new StaxBuilder(mappedWriter)
builder.root(attr:1) {
    elem1('hello')
    elem2('world')
}

assert writer.toString() == '{"root":{"@attr":"1","elem1":"hello","elem2":"world"}}'