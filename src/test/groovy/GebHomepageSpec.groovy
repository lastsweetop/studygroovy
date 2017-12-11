import geb.junit4.GebTest
import org.junit.Test

class SearchTests extends GebTest {

    @Test
    void executeSeach() {
        System.setProperty("webdriver.chrome.driver","/Users/apple/Documents/mydream/tools/driver/chromedriver")
        go 'http://somehost/mayapp/search'
        $('#searchField').text = 'John Doe'
        $('#searchButton').click()

        assert $('.searchResult a').first().text() == 'Mr. John Doe'
    }
}