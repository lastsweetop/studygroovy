package gettingStarted.cloningAndExternaling

import groovy.transform.AutoClone
import groovy.transform.AutoExternalize

/**
 * easier cloning and externaling
 */
@AutoExternalize
@AutoClone
class Book {
    String isbn
    String title
    List<String> authors
    Date publicationDate
}