package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
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
    private By confirmDeleteBtn = By.id("deleteAddressModal-0-submit-btn");
    private By existingAddressDiv = By.cssSelector("div#ya-myab-display-address-block-0");
    private By pageHeader = By.xpath("//h1[contains(.,'Your Addresses')]");

    public boolean checkIfExistingAddress(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(pageHeader));
        System.out.println("pageHeader -> "+pageHeader);
        try{
            driver.findElement(existingAddressDiv);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }
    public void removeExistingAddress(){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(removeExistingAddress));
            driver.findElement(removeExistingAddress).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(confirmDeleteBtn));
            driver.findElement(removeExistingAddress).click();
    }

    public void clickAmazonLogo(){
        driver.findElement(amazonLogo).click();
    }
}
