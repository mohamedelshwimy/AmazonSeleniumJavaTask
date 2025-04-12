package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private String tempEmail = "foodics.testing.task.me@gmail.com";
    private String tempPassword = "Mm@12345678";

    public LoginPage (WebDriver driver){
        this.driver = driver;
    }

    //Locators
    private By email = By.id("ap_email_login");
    private By continueBtn = By.xpath("//input[@type='submit']");
    private By password = By.id("ap_password");
    private By signInBtn = By.id("signInSubmit");
    //Methods
    public HomePage signIn(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(email).sendKeys(tempEmail);
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
        driver.findElement(continueBtn).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(password));
        driver.findElement(password).sendKeys(tempPassword);
        driver.findElement(signInBtn).click();
        return new HomePage(driver);
    }
}
