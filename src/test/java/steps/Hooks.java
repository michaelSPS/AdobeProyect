package steps;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.github.bonigarcia.wdm.WebDriverManager;
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
            // 1) Perfil temporal
            Path tmpProfile = Files.createTempDirectory("chrome-profile-");

            // 2) ChromeOptions base
            ChromeOptions options = new ChromeOptions()
                    .addArguments("--headless=new")
                    .addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu")
                    .addArguments("--user-data-dir=" + tmpProfile.toString());

            // 3) Sólo setea el binary cuando CHROME_BIN existe (en Docker)
            String chromeBin = System.getenv("CHROME_BIN");
            if (chromeBin != null && !chromeBin.isEmpty()) {
                options.setBinary(chromeBin);
            }

            // 4) Gestiona automáticamente chromedriver
            WebDriverManager.chromedriver()
                    .clearDriverCache()
                    .setup();

            // 5) Inicializa WebDriver
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
