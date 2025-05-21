package steps;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Files;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.out.println("Inicializando WebDriver desde Hooks...");
        if (driver == null) {
            // Crea un directorio temporal único para el perfil de Chrome
            String userDataDir = Files.createTempDirectory("chrome-user-data-").toString();

            ChromeOptions options = new ChromeOptions();
            // Indica dónde está el binario de Chromium en el contenedor
            options.setBinary(System.getenv("CHROME_BIN"));
            // Flags necesarios para correr dentro de Docker
            options.addArguments(
                    "--headless=new",                // Modo headless
                    "--no-sandbox",                  // Sin sandbox (necesario en contenedor)
                    "--disable-dev-shm-usage",       // Usa /tmp en lugar de /dev/shm
                    "--user-data-dir=" + userDataDir // Perfil único por sesión
            );

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
