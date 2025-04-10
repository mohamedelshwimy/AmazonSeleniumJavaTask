package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VideoGamesFreeShippingFiltered {
    private WebDriver driver;

    public VideoGamesFreeShippingFiltered(WebDriver driver){
        this.driver = driver;
    }

    //Locators
    private By freeShippingCheckBox = By.xpath("//ul[@id='filter-p_n_free_shipping_eligible']//input");
    private By newConditionFilter = By.xpath("//div[@id='p_n_condition-type']//span[contains(.,'New')][@class='a-list-item']");

    //Methods
    public Boolean getFreeShippingCheckBox(){
        WebElement freeShippingCheckBox = driver.findElement(this.freeShippingCheckBox);
        Boolean freeShippingCheckBoxIsSelected = freeShippingCheckBox.isSelected();
        System.out.println("freeShippingCheckBoxIsSelected -> " + freeShippingCheckBoxIsSelected);

        return freeShippingCheckBoxIsSelected;
    }

    public VideoGamesNewFiltered selectNewConditionFilter(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;


        WebElement newConditionFilter = driver.findElement(this.newConditionFilter);
        js.executeScript("arguments[0].scrollIntoView(true);", newConditionFilter);
        wait.until(ExpectedConditions.elementToBeClickable(newConditionFilter));

        newConditionFilter.click();

        return new VideoGamesNewFiltered(driver);
    }

}
