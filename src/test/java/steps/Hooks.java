package steps;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Files;
import java.nio.file.Path;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.out.println("Inicializando WebDriver desde Hooks...");

        if (driver == null) {
            // 1. Creamos un directorio temporal único para este test
            Path tmpProfile = Files.createTempDirectory("chrome-profile-");

            // 2. Configuramos las opciones de Chrome
            ChromeOptions options = new ChromeOptions()
                    // 2.1 Modo headless (si lo quieres visible, quita esta línea)
                    .addArguments("--headless=new")
                    // 2.2 Opciones necesarias en Docker
                    .addArguments("--no-sandbox",
                            "--disable-dev-shm-usage",
                            "--disable-gpu",
                            // 2.3 Perfil único
                            "--user-data-dir=" + tmpProfile.toString());

            // 3. Indicarle dónde está el binario (ya lo tienes en el ENV)
            options.setBinary(System.getenv("CHROME_BIN"));

            // 4. Arrancar Chrome
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
