package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    //Cash On Delivery is not available if total open order value exceeds 25000 EGP.
    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    //    private By useAddress = By.cssSelector("input#shipToThisAddressButton-announce");
    private By orderSummaryDiv = By.xpath("//div[@id='subtotals-marketplace-table']");
    private By shippingPrice = By.xpath("//div[@id='subtotals-marketplace-table']//tr[2]//td[contains(@class,'a-text-right')]");
    private By itemsPrice = By.xpath("//div[@id='subtotals-marketplace-table']//tr[1]//td[contains(@class,'a-text-right')]");
    private By orderTotalPrice = By.xpath("//div[@id='subtotals-marketplace-table']//tr[4]//td[contains(@class,' a-text-right')]");
    private By cashOnDeliveryRadio = By.cssSelector("//label[contains(.,'Cash on Delivery (COD)')]//input");
    private By useThisPaymentMethod = By.xpath("//input[@name='ppw-widgetEvent:SetPaymentPlanSelectContinueEvent']");

    //Methods
    public boolean checkCashOnDeliveryRadioEnabled(){
        return driver.findElement(cashOnDeliveryRadio).isEnabled();
    }
    public int getItemsPrice(){
        String itemsPriceEGP = driver.findElement(this.itemsPrice).getText().split(" ")[1];
        String itemsPrice = itemsPriceEGP.replace(",", "");
        return (int) Double.parseDouble(itemsPrice);
    }
    public int getShippingFees(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(shippingPrice));
        String shippingFees = driver.findElement(shippingPrice).getText().split(" ")[1];
        return (int) Double.parseDouble(shippingFees);
    }
    public int getOrderTotalPrice(){
        //EGP 64,898.00
        String totalPriceEGP = driver.findElement(orderTotalPrice).getText().split(" ")[1];
        System.out.println(totalPriceEGP);
        String totalPrice = totalPriceEGP.replace(",", "");
        return (int) Double.parseDouble(totalPrice);
    }
    public void chooseCashOnDelivery(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(cashOnDeliveryRadio));
        driver.findElement(cashOnDeliveryRadio).click();
        wait.until(ExpectedConditions.elementToBeClickable(useThisPaymentMethod));
        driver.findElement(useThisPaymentMethod).click();
    }

}
