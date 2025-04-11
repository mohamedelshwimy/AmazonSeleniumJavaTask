package Scenario;

import Base.BaseTests;
import Pages.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class E2EScenario extends BaseTests {
    LoginPage loginPage;
    VideoGamesPage videoGamesPage;
    VideoGamesFreeShippingFiltered videoGamesFreeShippingFiltered;
    VideoGamesNewFiltered videoGamesNewFiltered;
    SortedVideoGamesPage sortedVideoGamesPage;
    CartPage cartPage;
    SecureCheckOutPage secureCheckOutPage;
    AddressesPage addressesPage;
    CheckoutPage checkoutPage;
    static int totalPriceOfAllProducts = 0;
    //Tests
    @Test(priority = 1)
    public void loginToAmazon() {
        SoftAssert softAssert = new SoftAssert();
        loginPage = homePage.clickSignInBtn();
        homePage = loginPage.signIn();

        //Make sure we correctly signed in
        softAssert.assertEquals(homePage.getSignInName(), "Hello, Mohamed", "Not Signed In Correctly");
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = {"loginToAmazon"})
    public void checkCartIsEmpty() {
        SoftAssert softAssert = new SoftAssert();
        cartPage = homePage.goToCartPage();
        if (!cartPage.checkThatCartIsEmpty()) {
            cartPage.emptyCart();
            softAssert.assertTrue(cartPage.checkThatCartIsEmpty(), "Cart Is Not Empty");
            cartPage.clickAmazonLogo();
        }else{
            System.out.println("Cart is Empty");
            cartPage.clickAmazonLogo();
        }

        softAssert.assertAll();
    }
    @Test(priority = 3, dependsOnMethods = {"loginToAmazon"})
    public void removeExistingAddress() {
        SoftAssert softAssert = new SoftAssert();
        addressesPage = homePage.goToAddressesPage();
        if (!addressesPage.checkIfExistingAddress()) {
            addressesPage.removeExistingAddress();
            addressesPage.clickAmazonLogo();
        }else{
            System.out.println("No Addresses Found");
            addressesPage.clickAmazonLogo();
        }

        softAssert.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = {"checkCartIsEmpty"})
    public void navigateToVideoGames() {
        SoftAssert softAssert = new SoftAssert();
        //Make sure we landed in the correct page
        videoGamesPage = homePage.goToAllVideoGamesPage();
        softAssert.assertEquals(videoGamesPage.getVideoGamesBanner(), "Video Games", "Not Signed In Correctly");
        softAssert.assertAll();
    }

    //4. from the filter menu on the left side add filter “free shipping” & add the filter of condition “ new”
    @Test(priority = 5, dependsOnMethods = {"navigateToVideoGames"})
    public void filterVideoGames() {
        SoftAssert softAssert = new SoftAssert();

        videoGamesFreeShippingFiltered = videoGamesPage.clickFreeShippingFilter();

        //Check that free shipping checkbox is checked using isSelected method
        Boolean freeShippingCheck = videoGamesFreeShippingFiltered.getFreeShippingCheckBox();
        softAssert.assertTrue(freeShippingCheck, "FreeShipping filter not checked");

        softAssert.assertAll();
    }

    @Test(priority = 6, dependsOnMethods = {"filterVideoGames"})
    public void filterWithNewCondition() {
        SoftAssert softAssert = new SoftAssert();

        videoGamesNewFiltered = videoGamesFreeShippingFiltered.selectNewConditionFilter();

        //Check that clear is displayed after selecting New Condition filter
        Boolean clearFilter = videoGamesNewFiltered.checkClearFilterIsDisplayed();
        softAssert.assertTrue(clearFilter, "Clear Filters not displayed");

        softAssert.assertAll();
    }

    //5. open the sort menu then sort by price: high to low

    @Test(priority = 7, dependsOnMethods = {"filterWithNewCondition"})
    public void sortByPriceFromHighToLow() {
        SoftAssert softAssert = new SoftAssert();
        sortedVideoGamesPage = videoGamesNewFiltered.sortByPrice();

        //Check that Price is sorted after from High to Low
        String sortHighToLowText = sortedVideoGamesPage.sortHighToLowGetText();
        softAssert.assertEquals(sortHighToLowText, "Price: High to Low", "Wrong Sort");

        softAssert.assertAll();
    }

    //6. add all products below that its cost below 15k EGP, if no product below 15k EGP move to next page
    @Test(priority = 8, dependsOnMethods = {"sortByPriceFromHighToLow"})
    public void selectAllProductsBelow15K() {
        SoftAssert softAssert = new SoftAssert();
        //Not All Products have AddToCart btn
        sortedVideoGamesPage.AddAllProductsBelow15KToCart();
        softAssert.assertTrue(sortedVideoGamesPage.checkNumOfProductsAddedToCart(), "Incorrect NumOfProductsAddedToCart");

        softAssert.assertAll();
    }

    //7. make sure that all products is already added to carts
    @Test(priority = 9, dependsOnMethods = {"selectAllProductsBelow15K"})
    public void checkProductsInCart() {
        SoftAssert softAssert = new SoftAssert();
        cartPage = sortedVideoGamesPage.goToCartPage();
        softAssert.assertEquals(cartPage.numberOfItemsPresentInCart(), sortedVideoGamesPage.numOfProductsBelow15KAddedToCart(), "Check Items Present in Cart");
        totalPriceOfAllProducts = cartPage.getSumCartPrice();
        System.out.println("totalPriceOfAllProducts -> "+totalPriceOfAllProducts);
        softAssert.assertEquals(cartPage.getSumCartPrice(), cartPage.getTotalCartPrice(), "Diff between Sum and Total in Cart Page");
        softAssert.assertAll();
    }

    // 8. add address

    //country	fullName	countryCode	phoneNumber	streetName	buildingNum	city	district	addressType
    @Test(priority = 10,dataProvider = "ReadDataFromExcelSheet", dependsOnMethods = {"checkProductsInCart"})
    public void navigateToSecureCheckOutPage(String country,String fullName,String countryCode,
                                             String phoneNumber,String streetName,String buildingNum,
                                             String city,String district, String addressType) {


        secureCheckOutPage = cartPage.goToSecureCheckOutPage();
        secureCheckOutPage.addNewAddress(country,fullName, countryCode,phoneNumber,streetName,buildingNum,city,district,addressType);

    }

    //Cash On Delivery is not available if total open order value exceeds 25000 EGP.
    //make sure that the total amount of all items is correct with the shipping fees if exist
    //choose payment method
    @Test(priority = 11, dependsOnMethods = {"navigateToSecureCheckOutPage"})
    public void checkPaymentMethod() {

        checkoutPage = secureCheckOutPage.goToCheckOutPage();
        if(totalPriceOfAllProducts > 25000){
            //Choose payment method other than COD
        }else{
            //Choose COD payment method
        }
    }
}
