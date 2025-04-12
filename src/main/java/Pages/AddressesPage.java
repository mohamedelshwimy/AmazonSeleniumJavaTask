package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddressesPage {
    private WebDriver driver;

    public AddressesPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    private By amazonLogo = By.id("nav-logo-sprites");
    private By removeExistingAddress = By.id("ya-myab-address-delete-btn-0");
    private By confirmDeleteBtn = By.xpath("//span[@id='deleteAddressModal-0-submit-btn']//input");
    private By existingAddressDiv = By.cssSelector("div#ya-myab-display-address-block-0");
    private By pageHeader = By.xpath("//h1[contains(.,'Your Addresses')]");
    private By addressDeleted = By.xpath("//h4[contains(.,'Address deleted')]");

    public boolean checkIfExistingAddress() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.presenceOfElementLocated(pageHeader));
        System.out.println("pageHeader -> " + driver.findElement(pageHeader).getText());
        try {
            driver.findElement(existingAddressDiv);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void removeExistingAddress() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
            wait.until(ExpectedConditions.visibilityOfElementLocated(removeExistingAddress));
            driver.findElement(removeExistingAddress).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDeleteBtn));
            driver.findElement(confirmDeleteBtn).click();

    }

    public String getRemovedAddressMsg() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(addressDeleted));
        System.out.println("After delete address: " + driver.findElement(addressDeleted).getText());
        return driver.findElement(addressDeleted).getText();
    }

    public void clickAmazonLogo() {
        driver.findElement(amazonLogo).click();
    }
}
