package com.dilato.adobe.steps;

import com.dilato.adobe.drivers.DriverFactory;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.out.println("Inicializando WebDriver desde Hooks...");

        if (driver == null) {
            driver = DriverFactory.getDriver();
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}
