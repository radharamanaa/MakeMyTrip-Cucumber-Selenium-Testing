package makemytrip.stepdefinitions;

import com.zemoso.MakeMyTripLandingPO;
import config.InitialConfig;
import testrail.TestRailConfig;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class WorkflowRoundTripFlight{
    public static boolean result;
    private WebDriver driver;
    private MakeMyTripLandingPO landingPO;
    private String fromCity;
    private String toCity;
    private LocalDate tomorrow;
    private LocalDate dayAfterTom;

    @Given("User visits landing page")
    public void user_visits_landing_page() {
        driver = InitialConfig.getDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertNotNull(driver);
        driver.get(InitialConfig.getBaseURL());
        driver.getTitle().equals(InitialConfig.getLandingPageTitle());
        assert true;
    }
    @When("user enters Goa in FROM field")
    public void user_enters_goa_in_from_field() {
        landingPO = new MakeMyTripLandingPO(driver);
        fromCity = "Goa";
        landingPO.removeModals();
        landingPO.enterFromCity(fromCity);
        assert true;
    }
    @When("selects Goa from dropdown options")
    public void selects_goa_from_dropdown_options() {
        Assert.assertTrue(landingPO.isSelectFromCityInDropDownSuccessful("Goa"));
    }
    @When("user enters Miami in TO field")
    public void user_enters_miami_in_to_field() {
        toCity = "Miami";
        landingPO.enterToCity(toCity);
    }
    @When("selects Miami from dropdown options")
    public void selects_miami_from_dropdown_options() {
        Assert.assertTrue(landingPO.isSelectToCityFromDropDownSuccessful(toCity));
    }
    @When("user selects tomorrows date as DEPARTURE date")
    public void user_selects_tomorrows_date_as_departure_date() {
        tomorrow = LocalDate.now().plusDays(1);
        landingPO.selectADepartureDate(tomorrow);
    }
    @When("selects day after tomorrows date as RETURN date")
    public void selects_day_after_tomorrows_date_as_return_date() {
        dayAfterTom = tomorrow.plusDays(1);
        landingPO.selectReturnDate(dayAfterTom);
    }
    @Then("user verifies Goa in FROM field")
    public void user_verifies_goa_in_from_field() {
        result = landingPO.getFromCityCLickSelectorStr().equals(fromCity);
        Assert.assertTrue(result);
    }
    @Then("user verifies Miami in TO field")
    public void user_verifies_miami_in_to_field() {
        result = result && landingPO.getToCityStr().equals(toCity);
        Assert.assertTrue(result);
    }
    @Then("user verifies tomorrows date in DEPARTURE date")
    public void user_verifies_tomorrows_date_in_departure_date() {
        result = result && landingPO.isDepartureDateValue(tomorrow);
        Assert.assertTrue(result);
    }
    @Then("user verifies day after tomorrows date in RETURN field")
    public void user_verifies_day_after_tomorrows_date_in_return_field() {
        result = result && landingPO.isReturnDateValue(dayAfterTom);
        TestRailConfig.addTestResult(InitialConfig.getRoundTripWorkFlow(), result);
        Assert.assertTrue(result);
    }
}
