package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    private By cartProducts = By.xpath("//div[@role='listitem']");
    private By cartProductsPrices = By.xpath("//div[@role='listitem']//span[@class='a-price-whole']");
    private By totalPrice = By.xpath("//span[@id='sc-subtotal-amount-activecart']/span");

    //Methods
    public int numberOfItemsPresentInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartProducts));
        System.out.println("NumberOfItemsPresentInCart -> " + driver.findElements(cartProducts).size());
        return driver.findElements(cartProducts).size();
    }

    public int getSumCartPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartProductsPrices));
        int sum = 0;

        for (int i = 1; i <= driver.findElements(cartProductsPrices).size(); i++) {
            By cartProductsPrices = By.xpath("//div[@role='listitem'][" + i + "]//span[@class='a-price-whole']");
            String productPrice = driver.findElement(cartProductsPrices).getText().replace(",", "");
            int intProductPrice = Integer.parseInt(productPrice);
            sum += intProductPrice;
        }
        return sum;
    }

    public int getTotalCartPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(totalPrice));

        String totalPrice = driver.findElement(this.totalPrice).getText().replace(",", "");
        return Integer.parseInt(totalPrice);
    }


}
