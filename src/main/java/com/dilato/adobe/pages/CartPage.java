package com.dilato.adobe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.dilato.adobe.utils.ConfigManager;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyProductInCart(String expectedProductName) {
        String locatorKey = "verifyResults";
        try {
            waitUtils.verifyElementIsVisible(locatorKey);
            String locator = ConfigManager.getLocator(locatorKey);
            List<WebElement> products = driver.findElements(By.xpath(locator));

            if (products.isEmpty()) {
                System.out.println("⚠️ No se encontraron productos en el carrito.");
                return false;
            }

            for (WebElement product : products) {
                String actualProductName = product.getText().trim();
                System.out.println("✅ Producto encontrado: " + actualProductName);
                if (actualProductName.toLowerCase().contains(expectedProductName.toLowerCase())) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al verificar el producto en el carrito: " + e.getMessage());
        }

        return false;
    }

    public void clickProceedToCheckout(String locatorKey) throws IOException {
        clickElement(locatorKey);
    }

    public boolean isLoginPageDisplayed() throws IOException {
        String loginLocator = ConfigManager.getLocator("loginPageTitle");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement loginElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginLocator)));
            return loginElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

