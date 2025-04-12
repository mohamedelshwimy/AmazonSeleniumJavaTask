package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SecureCheckOutPage {
    private WebDriver driver;

    public SecureCheckOutPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    private By addNewAddressBtn = By.xpath("//span[@id='add-new-address-desktop-sasp-tango-link']//a");
    //In Case Address exists
    private By changeAddressBtn = By.xpath("//a[@data-args='redirectReason=shipaddressselectChangeClicked']");
    private By addNewAddress = By.cssSelector("a#add-new-address-desktop-sasp-tango-link");
    private By addNewAddressForm = By.cssSelector("div#a-popover-content-8");
    //New Address Form Locators
    private By formDiv = By.id("main-continue-form"); //Will use this div to narrow the search scope to improve performance
    private By countryDropDown = By.cssSelector("select#address-ui-widgets-countryCode-dropdown-nativeId");
    private By fullNameInput = By.cssSelector("input#address-ui-widgets-enterAddressFullName");
    private By countryCodeDropDown = By.cssSelector("select#address-ui-widgets-enterAddressISDDropdown"); //Pass by visible option
    private By countryCode = By.xpath("//span[@id='address-ui-widgets-enterAddressISDPhoneNumber']//span[@class='a-dropdown-prompt']"); // Get text to return +20
    private By phoneNumberInput = By.cssSelector("input#address-ui-widgets-enterAddressPhoneNumber");
    private By streetNameInput = By.cssSelector("input#address-ui-widgets-enterAddressLine1");
    private By buildingNumInput = By.cssSelector("input#address-ui-widgets-enter-building-name-or-number");
    private By cityInput = By.cssSelector("input#address-ui-widgets-enterAddressCity"); // 6th of October City
    private By cityAutoComplete = By.xpath("//ul[@id='address-ui-widgets-autocompleteResultsContainer']/li[1]");
    private By districtInput = By.cssSelector("input#address-ui-widgets-enterAddressDistrictOrCounty"); // 6th of October City
    private By districtAutoComplete = By.xpath("//ul[@id='address-ui-widgets-autocompleteResultsContainer']/li[1]");
    private By resAddressTypeRadioBtn = By.cssSelector("input#address-ui-widgets-addr-details-res-radio-input"); // res for residential - com for commercial
    private By comAddressTypeRadioBtn = By.cssSelector("input#address-ui-widgets-addr-details-com-radio-input");
    private By countinueBtn = By.xpath("//span[@id='checkout-primary-continue-button-id']//input");
    private By useThisAddressBtn = By.xpath("//input[@data-csa-c-slot-id='address-ui-widgets-continue-address-btn-bottom']");

    //Methods
    public boolean checkIfAddressAlreadyExists() {
        try {
            WebElement changeAddress = driver.findElement(changeAddressBtn);
            return changeAddress.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void changeAddress() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(changeAddressBtn));
        WebElement changeAddress = driver.findElement(changeAddressBtn);
        changeAddress.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(addNewAddress));
        driver.findElement(addNewAddress).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(addNewAddressForm));
    }

    public void addNewAddress(String country, String fullName, String countryCode,
                              String phoneNumber, String streetName, String buildingNum,
                              String city, String district, String addressType) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //If No address added
        wait.until(ExpectedConditions.elementToBeClickable(addNewAddressBtn));
        driver.findElement(addNewAddressBtn).click();
        //New Address Form
        wait.until(ExpectedConditions.visibilityOfElementLocated(formDiv));

        //Fill Form with data
        setCountry(country);
        enterFullName(fullName);
        if (getCountryCode().equalsIgnoreCase(countryCode)) {
            System.out.println("countryCode -> " + getCountryCode());
        } else {
            System.out.println(getCountryCode());
            System.out.println(countryCode);
            System.out.println("Incorrect Country Code");
        }
        enterPhoneNumber(phoneNumber);
        enterStreetName(streetName);
        enterBuildingNum(buildingNum);
        enterCity(city);
        enterDistrict(district);
        setAddressType(addressType);
    }

    public CheckoutPage goToCheckOutPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(useThisAddressBtn));
        driver.findElement(useThisAddressBtn).click();
        return new CheckoutPage(driver);
    }

    //country	fullName	countryCode
    public void setCountry(String country) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(countryDropDown));

        System.out.println("country -> " + country);
        WebElement countryDropDownElement = driver.findElement(countryDropDown);
        Select select = new Select(countryDropDownElement);
        select.selectByVisibleText(country);
    }

    public void enterFullName(String fullName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("fullName -> " + fullName);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(fullNameInput)));
        driver.findElement(fullNameInput).sendKeys(fullName);
    }

    public String getCountryCode() {
        WebElement countryCodeElement = driver.findElement(countryCode);

        return countryCodeElement.getText();
    }

    //phoneNumber	streetName	buildingNum
    public void enterPhoneNumber(String phoneNumber) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(phoneNumberInput)));
        driver.findElement(phoneNumberInput).sendKeys(phoneNumber);
    }

    public void enterStreetName(String streetName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("streetName -> " + streetName);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(streetNameInput)));
        driver.findElement(streetNameInput).sendKeys(streetName);
    }

    public void enterBuildingNum(String buildingNum) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("buildingNum -> " + buildingNum);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(buildingNumInput)));
        driver.findElement(buildingNumInput).sendKeys(buildingNum);
    }

    //	city	district	addressType
    public void enterCity(String city) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        System.out.println("city -> " + city);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(cityInput)));
            driver.findElement(cityInput).sendKeys(city);
            wait.until(ExpectedConditions.visibilityOfElementLocated(cityAutoComplete));
            driver.findElement(cityAutoComplete).click();
        } catch (TimeoutException e) {
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(cityInput));
            driver.findElement(cityInput).clear();
            driver.findElement(cityInput).sendKeys(city);
            wait.until(ExpectedConditions.presenceOfElementLocated(cityAutoComplete));
            driver.findElement(cityAutoComplete).click();
        }
    }

    public void enterDistrict(String district) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            System.out.println("district -> " + district);

            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(districtInput)));
            driver.findElement(districtInput).sendKeys(district);
            wait.until(ExpectedConditions.visibilityOfElementLocated(districtAutoComplete));
            driver.findElement(districtAutoComplete).click();
        } catch (TimeoutException e) {
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(districtInput));
            driver.findElement(districtInput).sendKeys(district);
            wait.until(ExpectedConditions.presenceOfElementLocated(districtAutoComplete));
            driver.findElement(districtAutoComplete).click();
        }
    }

    public void setAddressType(String addressType) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(resAddressTypeRadioBtn));
        WebElement resAddressTypeRadio = driver.findElement(resAddressTypeRadioBtn);
        WebElement comAddressTypeRadio = driver.findElement(comAddressTypeRadioBtn);
        if (addressType.equalsIgnoreCase("res")) {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(resAddressTypeRadioBtn)));
            if (!resAddressTypeRadio.isSelected()) {
                resAddressTypeRadio.click();
            }
        } else if (addressType.equalsIgnoreCase("com")) {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(comAddressTypeRadioBtn)));
            if (!comAddressTypeRadio.isSelected()) {
                comAddressTypeRadio.click();
            }
        } else {
            System.out.println("Invalid Input");
        }
    }
}
