package com.dilato.adobe.steps;

import io.cucumber.java.en.Given;
import com.dilato.adobe.pages.HomePage;

import java.io.IOException;

import static com.dilato.adobe.steps.Hooks.driver;

public class RegressionSteps {

    HomePage homePage;


    public RegressionSteps() {
        homePage = new HomePage(driver);
    }

    @Given("^(?:I|The Client) navigate to (.+)$")
    public void NavigateToHomePage(String configKey) throws IOException {
        homePage.navigateToWebPage(configKey);
        System.out.println("✅ PASÓ");
    }

}