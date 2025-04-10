package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.management.InvalidAttributeValueException;
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

    private By allCategories = By.id("nav-hamburger-menu");
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

        driver.findElement(allCategories).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(shopByCategoryDiv));
        WebElement shopByCategoryDiv = driver.findElement(this.shopByCategoryDiv);

        wait.until(ExpectedConditions.elementToBeClickable(this.shopByCategorySeeAll));
        shopByCategoryDiv.findElement(shopByCategorySeeAll).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(this.videoGamesCategory));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement videoGamesCategory = driver.findElement(this.videoGamesCategory);
        js.executeScript("arguments[0].scrollIntoView(true);", videoGamesCategory);
        videoGamesCategory.click();

        WebElement allVideoGamesSection = driver.findElement(this.allVideoGamesSection);
        wait.until(ExpectedConditions.presenceOfElementLocated(this.allVideoGamesSection));

        WebElement allVideoGamesCategory = allVideoGamesSection.findElement(this.allVideoGamesCategory);
        js.executeScript("arguments[0].scrollIntoView(true);", allVideoGamesCategory);
        js.executeScript("arguments[0].click();", allVideoGamesCategory);

        return new VideoGamesPage(driver);
    }


}