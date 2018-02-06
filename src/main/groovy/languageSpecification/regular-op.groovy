#!/usr/bin/env groovy
package languageSpecification

import java.util.regex.Matcher

def text = "some text to match"
if (!(text =~ /match1/)) {
    throw new RuntimeException("Oops, text not found!")
}

if (text ==~ /match/) {
    throw new RuntimeException("Should not reach that point!")
}
