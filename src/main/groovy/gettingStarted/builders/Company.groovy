package gettingStarted.builders

import groovy.transform.ToString

@ToString(includeNames = true,includePackage = false)
class Company {
    String name
    Address address
    List employees = []
}