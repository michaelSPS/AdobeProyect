package com.dilato.adobe.support;

import com.dilato.adobe.drivers.DriverFactory;
import com.dilato.adobe.pages.HomePage;
import org.openqa.selenium.WebDriver;

/**
 * Permite instanciar páginas inyectando el mismo driver.
 */
public class PageManager {
    private WebDriver driver = DriverFactory.getDriver();

    private HomePage homePage;

    public PageManager() throws Exception {
    }
    // otros PageObjects…

    public HomePage homePage() {
        if (homePage == null) {
            homePage = new HomePage(driver);
        }
        return homePage;
    }

    // getters para otras páginas…
}
