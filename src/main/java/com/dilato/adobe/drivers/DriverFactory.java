package com.dilato.adobe.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Factory para crear y gestionar instancias de WebDriver.
 */
public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {}

    /**
     * Inicializa el WebDriver según configuración.
     */
    public static WebDriver getDriver() throws Exception {
        if (driver.get() == null) {
            String browser = System.getProperty("browser", "chrome").toLowerCase();
            switch (browser) {
                case "chrome":
                    setupChrome();
                    driver.set(new ChromeDriver(getChromeOptions()));
                    break;
                case "firefox":
                    setupFirefox();
                    driver.set(new FirefoxDriver(getFirefoxOptions()));
                    break;
                default:
                    throw new IllegalArgumentException("Navegador no soportado: " + browser);
            }
            driver.get().manage().window().maximize();
        }
        return driver.get();
    }

    private static void setupChrome() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
    }

    private static void setupFirefox() {
        WebDriverManager.firefoxdriver().clearDriverCache().setup();
    }

    private static ChromeOptions getChromeOptions() throws Exception {
        Path tmpProfile = Files.createTempDirectory("chrome-profile-");
        ChromeOptions options = new ChromeOptions()
                .addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu")
                .addArguments("--user-data-dir=" + tmpProfile.toString());
        String chromeBin = System.getenv("CHROME_BIN");
        if (chromeBin != null && !chromeBin.isEmpty()) {
            options.setBinary(chromeBin);
        }
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() throws Exception {
        // Similar perfil temporal si se desea
        FirefoxOptions options = new FirefoxOptions()
                .addArguments("-headless");
        return options;
    }

    /**
     * Cierra y elimina la instancia del driver.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
