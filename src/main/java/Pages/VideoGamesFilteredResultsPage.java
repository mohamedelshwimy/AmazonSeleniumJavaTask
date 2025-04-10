package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VideoGamesFilteredResultsPage {
    private WebDriver driver;

    public VideoGamesFilteredResultsPage(WebDriver driver){
        this.driver = driver;
    }

    //Locators
    private By freeShippingUlTag = By.id("filter-p_n_free_shipping_eligible");
    private By freeShippingCheckBox = By.tagName("input");
    //Methods
    public Boolean getFreeShippingCheckBox(){
        WebElement freeShippingUlTag = driver.findElement(this.freeShippingUlTag);
        Boolean freeShippingCheckBox = freeShippingUlTag.findElement(this.freeShippingCheckBox).isSelected();
        System.out.println("freeShippingCheckBox ->" + freeShippingCheckBox);
        return freeShippingCheckBox;
    }



}
