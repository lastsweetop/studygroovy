package gettingStarted.builders

import groovy.transform.Immutable
import groovy.transform.ToString

@ToString(includePackage = false)
@Immutable
class Address {
    String line1
    String line2
    int zip
    String state
}
