package languageSpecification

import groovy.xml.MarkupBuilder
import static Boolean.FALSE
import static java.lang.String.format
import static Calendar.getInstance as now
import static java.lang.Math.*
import java.sql.Date as SQLDate

Date utilDate = new Date(1000L)
SQLDate sqlDate = new SQLDate(1000L)

assert utilDate instanceof java.util.Date
assert sqlDate instanceof java.sql.Date

def xml=new MarkupBuilder()

assert xml!=null

new Date()

assert !FALSE

class SomeClass{
  String  format(Integer i) {
    i.toString()
  }

  static main(args) {
    assert format('String')=='String'
    assert new languageSpecification.typings.SomeClass().format(Integer.valueof(1))=='1'
  }

}

assert now().class==Calendar.getInstance().class

assert cos(0)==1.0
assert sin(0)==0.0
