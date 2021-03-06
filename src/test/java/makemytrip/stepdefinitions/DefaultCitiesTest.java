package makemytrip.stepdefinitions;

import com.zemoso.MakeMyTripLandingPO;
import config.InitialConfig;
import testrail.TestRailConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class DefaultCitiesTest {
    private static WebDriver driver;
    private static boolean result;
    private MakeMyTripLandingPO landingPO;

    @Given("User is on MakeMyTrip landing page")
    public void user_is_on_make_my_trip_landing_page() {
        driver = InitialConfig.getDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertNotNull(driver);
        driver.get(InitialConfig.getBaseURL());
        Assert.assertTrue(driver.getTitle()
                .equals("MakeMyTrip - #1 Travel Website 50% OFF on Hotels, Flights & Holiday"));
    }
    @Given("Round Trip option is selected")
    public void option_is_selected() {
        landingPO = new MakeMyTripLandingPO(driver);
        landingPO.selectRoundTrip();
        Assert.assertTrue(landingPO.isRoundtripSelected());
    }
    @Given("User is on landing page")
    public void user_is_on_landing_page() {
        user_is_on_make_my_trip_landing_page();
    }
    @When("user is from India")
    public void user_is_from_india() {
        // Write code here that turns the phrase above into concrete actions
        assert true;
    }
    @Then("user verifies FROM city to be {string}")
    public void user_verifies_from_city_to_be(String string) {
        final String fromCityCLickSelectorStr = landingPO.getFromCityCLickSelectorStr();
        result = fromCityCLickSelectorStr.equals(string);
        Assert.assertEquals(fromCityCLickSelectorStr,string);
    }
    @Then("TO city to be {string}")
    public void to_city_to_be(String string) {
        final String toCityStr = landingPO.getToCityStr();
        result = result && toCityStr.equals(string);
        TestRailConfig.addTestResult(InitialConfig.getDefaultCities(), result);
        Assert.assertEquals(toCityStr,string);
    }

}
