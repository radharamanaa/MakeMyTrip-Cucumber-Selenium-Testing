package makemytrip;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/makemytrip"},
        glue = {"makemytrip/stepdefinitions","config"},
        plugin = {"pretty"})
public class RunCucumberTest {

}
