package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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


        WebElement clearConditionFilters = driver.findElement(this.clearConditionFilters);
        js.executeScript("arguments[0].scrollIntoView(true);", clearConditionFilters);
        wait.until(ExpectedConditions.elementToBeClickable(clearConditionFilters));

        Boolean clearConditionFilter = clearConditionFilters.isDisplayed();
        System.out.println("clearConditionFilters isDisplayed -> " + clearConditionFilter);
        return clearConditionFilter;
    }

    public SortedVideoGamesPage sortByPrice(){
        WebElement sortByElement = driver.findElement(sortBySelect);
        Select select = new Select(sortByElement);
        select.selectByVisibleText("Price: High to Low");

        return new SortedVideoGamesPage(driver);
    }

}
