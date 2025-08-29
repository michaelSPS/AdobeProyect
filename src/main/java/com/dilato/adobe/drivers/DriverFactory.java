package com.dilato.adobe.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;
import java.util.UUID;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() throws Exception {
        if (driver.get() == null) {
            String browser = System.getProperty("browser", "chrome").toLowerCase();

            switch (browser) {
                case "chrome":
                    ChromeOptions chromeOptions = getChromeOptions();
                    Object argsObj = chromeOptions.asMap().get("args");

                    if (argsObj instanceof List) {
                        List<String> argsList = (List<String>) argsObj;
                        System.out.println("🚀 Lanzando Chrome con opciones: " + String.join(" ", argsList));
                    } else {
                        System.out.println("🚀 Lanzando Chrome sin argumentos personalizados.");
                    }

                    driver.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    driver.set(new FirefoxDriver(getFirefoxOptions()));
                    break;

                default:
                    throw new IllegalArgumentException("Navegador no soportado: " + browser);
            }

            driver.get().manage().window().maximize();
        }

        return driver.get();
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        String uniqueDir = "chrome-profile-" + UUID.randomUUID();
        System.out.println("🧪 user-data-dir usado: " + uniqueDir);

        options.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--remote-debugging-port=9222",
                "--user-data-dir=" + uniqueDir
        );

        String chromeBin = System.getenv("CHROME_BIN");
        if (chromeBin != null && !chromeBin.isEmpty()) {
            options.setBinary(chromeBin);
        }

        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        return new FirefoxOptions().addArguments("-headless");
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
