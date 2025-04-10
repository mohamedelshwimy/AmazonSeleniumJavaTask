package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SortedVideoGamesPage {

    private WebDriver driver;

    public SortedVideoGamesPage(WebDriver driver){
        this.driver = driver;
    }

    private By sortByText = By.cssSelector("span.a-dropdown-prompt");

    public String sortHighToLowGetText() {
        WebElement sortByElement = driver.findElement(sortByText);
        System.out.println("sortBy -> " + sortByElement.getText());
        return sortByElement.getText();
    }
}