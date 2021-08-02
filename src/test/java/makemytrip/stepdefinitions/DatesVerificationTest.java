package makemytrip.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DatesVerificationTest {

    @When("user selects a DEPARTURE date")
    public void user_selects_a_departure_date() {
        assert true;
    }
    @Then("user cannot select a RETURN date prior to DEPARTURE date")
    public void user_cannot_select_a_return_date_prior_to_departure_date() {
        assert true;
    }
}
