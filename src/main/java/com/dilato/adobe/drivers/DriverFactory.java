package com.dilato.adobe.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
        // utility
    }

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            final String browser = System.getProperty("browser", "chrome")
                    .trim()
                    .toLowerCase(Locale.ROOT);

            switch (browser) {
                case "chrome": {
                    ChromeOptions chromeOptions = getChromeOptions();

                    // Obtener los argumentos de manera segura desde asMap()
                    Object argsObj = chromeOptions.asMap().get("args");
                    if (argsObj instanceof List<?>) {
                        @SuppressWarnings("unchecked") // cast seguro porque Selenium siempre devuelve List<String>
                        List<String> args = (List<String>) argsObj;

                        if (!args.isEmpty()) {
                            System.out.println("🚀 Lanzando Chrome con opciones: " + String.join(" ", args));
                        } else {
                            System.out.println("🚀 Lanzando Chrome sin argumentos personalizados.");
                        }
                    } else {
                        System.out.println("🚀 Lanzando Chrome sin argumentos personalizados.");
                    }

                    DRIVER.set(new ChromeDriver(chromeOptions));
                    break;
                }

                case "firefox": {
                    DRIVER.set(new FirefoxDriver(getFirefoxOptions()));
                    break;
                }
                default:
                    throw new IllegalArgumentException("Navegador no soportado: " + browser);
            }

            DRIVER.get().manage().window().maximize();
        }
        return DRIVER.get();
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        // Usa un directorio temporal para el perfil (más seguro en diferentes entornos)
        String uniqueDir = "chrome-profile-" + UUID.randomUUID();
        String userDataDir = Paths.get(System.getProperty("java.io.tmpdir"), uniqueDir).toString();
        System.out.println("🧪 user-data-dir usado: " + userDataDir);

        options.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--remote-debugging-port=9222",
                "--user-data-dir=" + userDataDir
        );

        // Respeta CHROME_BIN si está seteado (Docker/CI)
        String chromeBin = System.getenv("CHROME_BIN");
        if (chromeBin != null && !chromeBin.isEmpty()) {
            options.setBinary(chromeBin);
        }

        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        // equivalente a -headless, tipado
        return options;
    }

    public static void quitDriver() {
        WebDriver d = DRIVER.get();
        if (d != null) {
            try {
                d.quit();
            } finally {
                DRIVER.remove();
            }
        }
    }
}

