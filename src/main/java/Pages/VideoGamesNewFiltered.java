package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VideoGamesNewFiltered {
    private WebDriver driver;

    public VideoGamesNewFiltered(WebDriver driver){
        this.driver = driver;
    }

    //Locators
    private By clearConditionFilters = By.xpath("//div[@id='p_n_condition-type']//span[contains(.,'Clear')]");
    private By sortBySelect = By.cssSelector("select#s-result-sort-select");

    //Methods
    public Boolean checkClearFilterIsDisplayed(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(clearConditionFilters));
            WebElement clearConditionFilterElement = driver.findElement(clearConditionFilters);
            js.executeScript("arguments[0].scrollIntoView(true);", clearConditionFilterElement);
            wait.until(ExpectedConditions.elementToBeClickable(clearConditionFilterElement));

            Boolean clearConditionFilter = clearConditionFilterElement.isDisplayed();
            System.out.println("clearConditionFilters isDisplayed -> " + clearConditionFilter);
            return clearConditionFilter;

        }catch (NoSuchElementException e){
            WebElement clearConditionFilterElement = driver.findElement(clearConditionFilters);
            js.executeScript("arguments[0].click();", clearConditionFilterElement);
            return clearConditionFilterElement.isDisplayed();
        }
    }

    public SortedVideoGamesPage sortByPrice(){
        WebElement sortByElement = driver.findElement(sortBySelect);
        Select select = new Select(sortByElement);
        select.selectByVisibleText("Price: High to Low");

        return new SortedVideoGamesPage(driver);
    }

}
