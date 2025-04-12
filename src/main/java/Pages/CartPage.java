package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    private By cartCount = By.id("nav-cart-count");
    private By cartProducts = By.xpath("//div[@role='listitem']");
    private By cartProductsPrices = By.xpath("//div[@role='listitem']//span[@class='a-price-whole']");
    private By totalPrice = By.xpath("//span[@id='sc-subtotal-amount-activecart']/span");
    private By proceedToBuyBtn = By.xpath("//input[@name='proceedToRetailCheckout']");
    private By amazonLogo = By.id("nav-logo-sprites");

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

            int intProductPrice = (int) Double.parseDouble(productPrice);
            sum += intProductPrice;
        }
        return sum;
    }

    public int getTotalCartPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(totalPrice));

        String totalPriceEGP = driver.findElement(this.totalPrice).getText().split(" ")[1];
        String totalPrice = totalPriceEGP.replace(",", "");
        return (int) Double.parseDouble(totalPrice);
    }

    public SecureCheckOutPage goToSecureCheckOutPage() {
        driver.findElement(proceedToBuyBtn).click();
        return new SecureCheckOutPage(driver);
    }

    public void emptyCart() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartProducts));
        int numberOfProductsInCart = driver.findElements(cartProducts).size();
        System.out.println("Products in Cart -> " + numberOfProductsInCart);

        if (numberOfProductsInCart > 0) {
            for (int i = numberOfProductsInCart; i >= 1; i--) {
                if (!driver.findElements(cartProducts).isEmpty()) {
                    By cartProductDeleteBtn = By.xpath("//div[@role='listitem'][" + i + "]//input[@data-feature-id='item-delete-button']");
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(cartProductDeleteBtn));
                    wait.until(ExpectedConditions.elementToBeClickable(cartProductDeleteBtn));
                    try {
                        driver.findElement(cartProductDeleteBtn).click();
                    } catch (StaleElementReferenceException e) {
                        if (checkThatCartIsEmpty()) {
                            break;
                        } else {
                            driver.findElement(cartProductDeleteBtn).click();
                        }
                    }
                } else {
                    break;
                }
            }
            System.out.println("Items in Cart: " + driver.findElement(cartCount).getText());
        } else {
            System.out.println("Cart is Empty");
        }
    }

    public boolean checkThatCartIsEmpty() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartCount));

        if (driver.findElement(cartCount).getText().equals("0")) {
            System.out.println("Items in Cart: " + driver.findElement(cartCount).getText());
            System.out.println("Cart is Empty");
            return true;
        } else {
            System.out.println("Cart is Not Empty");
            return false;
        }
    }
    public void clickAmazonLogo(){
        driver.findElement(amazonLogo).click();
    }

}
