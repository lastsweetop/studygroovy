package gettingStarted.compiler

import groovy.transform.Field
import groovy.transform.PackageScope
import groovy.transform.PackageScopeTarget

@Field def x

String line() {
    "="*x
}

x=3
assert "===" == line()
x=5
assert "=====" == line()

@PackageScope(PackageScopeTarget.FIELDS)
class Person {
     String name
    Date bob
}