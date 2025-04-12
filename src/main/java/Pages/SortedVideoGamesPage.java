package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SortedVideoGamesPage {

    private WebDriver driver;
    private List<Integer> listItemPricesBelow15K = new ArrayList<>();
    private List<Integer> productsBelow15KAddedToCart = new ArrayList<>();
    private int numOfProductsInCart = 0;
    private List<String> listItemPrices = new ArrayList<>();
    private static int counter = 1;
    private static int j = 1;
    private boolean continuePages = true; //Will Stop If the page contains prices lower than 15K

    public SortedVideoGamesPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    private By sortByText = By.cssSelector("span.a-dropdown-prompt");
    private By listItemDiv = By.xpath("//div[@role='listitem']");
    private By listItemPrice = By.xpath("//span[@class='a-price-whole']");
    private By moveToNextPage = By.xpath("//a[contains(.,'Next')]");
    private By cartCount = By.id("nav-cart-count");
    private By cartDiv = By.cssSelector("div#ewc-content");
    //Methods
    public String sortHighToLowGetText() {
        WebElement sortByElement = driver.findElement(sortByText);
        System.out.println("sortBy -> " + sortByElement.getText());
        return sortByElement.getText();
    }

    public void AddAllProductsBelow15KToCart(){
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        while (continuePages) {
            System.out.println("While Loop Count -> " + counter);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(listItemDiv));
            List<WebElement> products = driver.findElements(listItemDiv);
            System.out.println("List Items Size = " + products.size());

            //Start from the bottom so if any product in the list is below 15K we will not move to next page
            for (int i = products.size(); i >= 1; i--) {
                System.out.println("For Loop Count -> " + j);
                j++;
                By productDiv = By.xpath("//div[@role='listitem'][" + i + "]");
                By productPrice = By.xpath("//div[@role='listitem'][" + i + "]//span[@class='a-price-whole']");
                By addToCartBtn = By.xpath("//div[@role='listitem'][" + i + "]//button[@name='submit.addToCart']");

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listitem'][" + i + "]")));
                //handle divs without prices
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(productPrice));

                    String listItemPriceTag = driver.findElement(productPrice)
                            .getText().replace(",", "");
                    listItemPrices.add(listItemPriceTag);
                    System.out.println("list Item Price -> " + listItemPriceTag);

                    int listItemPriceValue = Integer.parseInt(listItemPriceTag);
                    System.out.println("listItemPriceValue -> " + listItemPriceValue + '\n');
                    if (listItemPriceValue < 15000) {
                        continuePages = false; //Don't go to next page

                        js.executeScript("arguments[0].scrollIntoView({block: 'center'});",driver.findElement(productDiv));
                        listItemPricesBelow15K.add(listItemPriceValue);
                        System.out.println("listItemPricesBelow15K -> " + listItemPricesBelow15K + '\n');

                        if(driver.findElement(addToCartBtn).isDisplayed()){
                            js.executeScript("arguments[0].scrollIntoView({block: 'center'});",driver.findElement(addToCartBtn));
                            wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
                            productsBelow15KAddedToCart.add(listItemPriceValue);
                            driver.findElement(addToCartBtn).click();
                        }
                    }else{
                        if (continuePages) {
                            wait.until(ExpectedConditions.presenceOfElementLocated(moveToNextPage));
                            WebElement moveToNext = driver.findElement(moveToNextPage);
                            moveToNext.click();
                        }
                    }
                }catch (NoSuchElementException | TimeoutException e){
                    continue;
                }

            }
            System.out.println("listItemPrices -> " + listItemPrices);
            System.out.println("productsBelow15KAddedToCart -> " + productsBelow15KAddedToCart);
            System.out.println("listItemPricesBelow15K -> " + listItemPricesBelow15K + '\n');
            System.out.println("listItemPrices size -> " + listItemPrices.size());
            System.out.println("productsBelow15KAddedToCart size -> " + productsBelow15KAddedToCart.size());
            System.out.println("listItemPricesBelow15K size -> " + listItemPricesBelow15K.size() + '\n');
            counter++;
        }
    }

    public boolean checkNumOfProductsAddedToCart(){
        numOfProductsInCart = Integer.parseInt(driver.findElement(cartCount).getText());
        return numOfProductsInCart == productsBelow15KAddedToCart.size();
    }

    public int numOfProductsBelow15KAddedToCart(){
        return productsBelow15KAddedToCart.size();
    }

    public int sumOfProductsBelow15KAddedToCart(){
        int sum = 0;

        for (int num : productsBelow15KAddedToCart) {
            sum += num;
        }
        System.out.println("sumOfProductsBelow15KAddedToCart = " + sum);
        return sum;
    }

    public CartPage goToCartPage(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartCount));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(cartCount));
            driver.findElement(cartCount).click();
        }catch (ElementClickInterceptedException e){
            js.executeScript("arguments[0].click();",  driver.findElement(cartCount));
        }
        return new CartPage(driver);
    }

}