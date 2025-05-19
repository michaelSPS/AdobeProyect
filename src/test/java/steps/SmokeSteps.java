package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.HomePage;

import java.io.IOException;

import static steps.Hooks.driver;

public class SmokeSteps {

    HomePage homePage;

    public SmokeSteps() {
        homePage = new HomePage(driver);
    }

    @Given("^(?:I|The user) navigates to the (.+)$")
    public void NavigatesToHomePage(String configKey) throws IOException {
        homePage.navigateToWebPage(configKey);
        System.out.println("✅ PASÓ");
    }

    @Then("^The home page should be displayed$")
    public void theHomepageShouldBeDisplayed() {
        Assert.assertTrue("Page title should contain 'Adobe'", homePage.getPageTitle().toLowerCase().contains("adobe"));
    }
}
