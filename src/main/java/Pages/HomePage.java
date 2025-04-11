package Pages;

import org.apache.commons.math3.analysis.function.Add;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;

    public HomePage (WebDriver driver){
        this.driver = driver;
    }

    //Locators
    private By signInBtn = By.tagName("a");
    private By signInDiv = By.id("nav-link-accountList");
    private By signedName = By.id("nav-link-accountList-nav-line-1");

    private By addressesBtn = By.cssSelector("a#nav-global-location-popover-link");
    private By manageAddressBook = By.xpath("//a[contains(.,'Manage address book')]");
    private By cartBtn = By.id("nav-cart-count");
    private By allCategories = By.cssSelector("a#nav-hamburger-menu");
    private By shopByCategoryDiv = By.xpath("//section[@aria-labelledby='Shop by Category']");
    private By shopByCategorySeeAll = By.xpath("//a[@aria-label='See All Categories']");

    private By videoGamesCategory = By.xpath("//a[contains(@data-menu-id,'16')]");
    private By allVideoGamesSection = By.xpath("//section[contains(.,'Video Games')]");
    private By allVideoGamesCategory = By.xpath("//a[@class='hmenu-item'][contains(.,'All Video Games')]");


    //Methods
    public LoginPage clickSignInBtn(){
        WebElement signInDiv = driver.findElement(this.signInDiv);
        signInDiv.findElement(signInBtn).click();

        return new LoginPage(driver);
    }

    public String getSignInName(){
        return driver.findElement(signedName).getText();
    }

    public VideoGamesPage goToAllVideoGamesPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        wait.until(ExpectedConditions.elementToBeClickable(allCategories));
        driver.findElement(allCategories).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(shopByCategoryDiv));
        WebElement shopByCategoryDiv = driver.findElement(this.shopByCategoryDiv);
        wait.until(ExpectedConditions.elementToBeClickable(shopByCategoryDiv.findElement(shopByCategorySeeAll)));

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", shopByCategoryDiv.findElement(shopByCategorySeeAll));
        shopByCategoryDiv.findElement(shopByCategorySeeAll).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(this.videoGamesCategory));

        WebElement videoGamesCategory = driver.findElement(this.videoGamesCategory);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", videoGamesCategory);
        videoGamesCategory.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(this.allVideoGamesSection));
        WebElement allVideoGamesSection = driver.findElement(this.allVideoGamesSection);

        try {
            WebElement allVideoGamesCategory = allVideoGamesSection.findElement(this.allVideoGamesCategory);
            wait.until(ExpectedConditions.elementToBeClickable(allVideoGamesCategory));
            allVideoGamesCategory.click();
        }catch (NoSuchElementException |ElementClickInterceptedException e) {
            WebElement allVideoGamesCategory = allVideoGamesSection.findElement(this.allVideoGamesCategory);
            js.executeScript("arguments[0].click();", allVideoGamesCategory);
        }
        return new VideoGamesPage(driver);
    }

    public CartPage goToCartPage(){
        driver.findElement(cartBtn).click();
        return new CartPage(driver);
    }
    public AddressesPage goToAddressesPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(addressesBtn).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(manageAddressBook));
        driver.findElement(manageAddressBook).click();
        return new AddressesPage(driver);
    }
}