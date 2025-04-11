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
    private List<String> listItemPrices = new ArrayList<>();
    private static int counter = 1;
    private boolean continuePages = true; //Will Stop If the page contains prices lower than 15K

    public SortedVideoGamesPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    private By sortByText = By.cssSelector("span.a-dropdown-prompt");
    private By listItemDiv = By.xpath("//div[@role='listitem']");
    private By listItemPrice = By.xpath("//span[@class='a-price-whole']");
    private By moveToNextPage = By.xpath("//a[contains(.,'Next')]");

    //Methods
    public String sortHighToLowGetText() {
        WebElement sortByElement = driver.findElement(sortByText);
        System.out.println("sortBy -> " + sortByElement.getText());
        return sortByElement.getText();
    }

    public void AddAllListItemsBelow15KToArrayList(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        while (continuePages) {
            System.out.println("While Loop Count -> " + counter);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(listItemDiv));
            List<WebElement> products = driver.findElements(listItemDiv);
            System.out.println("List Items Size = " + products.size());

            //Start from the bottom so if any product in the list is below 15K we will not move to next page
            for (int i = products.size(); i >= 1; i--) {
                System.out.println("For Loop Count -> " + i);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listitem'][" + i + "]")));
                //handle divs without prices
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listitem'][" + i + "]//span[@class='a-price-whole']")));
                    String listItemPriceTag = driver.findElement(By.xpath("//div[@role='listitem'][" + i + "]//span[@class='a-price-whole']"))
                            .getText().replace(",", "");
                    listItemPrices.add(listItemPriceTag);
                    System.out.println("list Item Price -> " + listItemPriceTag);

                    int listItemPriceValue = Integer.parseInt(listItemPriceTag);
                    System.out.println("listItemPriceValue -> " + listItemPriceValue + '\n');
                    if (listItemPriceValue < 15000) {
                        listItemPricesBelow15K.add(listItemPriceValue);
                        System.out.println("listItemPricesBelow15K -> " + listItemPricesBelow15K + '\n');
                        continuePages = false;
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
            System.out.println("listItemPricesBelow15K -> " + listItemPricesBelow15K + '\n');
            counter++;
        }
    }

}