package gettingStarted.builders

import groovy.transform.ToString

@ToString(includeNames = true,excludes = ['company'],includePackage = false)
class Employee {
    String name
    int employeeId
    Address address
    Company company
}
