package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    //Cash On Delivery is not available if total open order value exceeds 25000 EGP.
    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    private By useAddress = By.cssSelector("input#shipToThisAddressButton-announce");
}
