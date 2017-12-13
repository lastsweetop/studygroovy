import geb.Module
import geb.Page
import geb.spock.GebSpec

class ManualsMenuModule extends Module {
    static content = {
        toggle { $("div.menu a.manuals") }
        linksContainer { $("#manuals-menu") }
        links { linksContainer.find("a") }
    }

    void open() {
        toggle.click()
        waitFor { !linksContainer.hasClass("animating") }
    }
}

class GebHomePage extends Page {
    static url = "http://gebish.org"

    static at = { title == "Geb - Very Groovy Browser Automation" }

    static content = {
        manualsMenu { module(ManualsMenuModule) }
    }
}

class TheBookOfGebPage extends Page {
    static at = { title.startsWith("The Book Of Geb") }
}

class GebHomepageSpec extends GebSpec {

    def "can access The Book of Geb via homepage"() {
        given:
        to GebHomePage

        when:
        manualsMenu.open()
        manualsMenu.links[0].click()

        then:
        at TheBookOfGebPage
    }

}