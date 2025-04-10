package Scenario;

import Base.BaseTests;
import Pages.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class E2EScenario extends BaseTests {
    private static LoginPage loginPage;
    private static VideoGamesPage videoGamesPage;
    private static VideoGamesFreeShippingFiltered videoGamesFreeShippingFiltered;
    private static VideoGamesNewFiltered videoGamesNewFiltered;
    private static SortedVideoGamesPage sortedVideoGamesPage;

    /*
    @Test(priority = 1)
    public void loginToAmazon() {
        SoftAssert softAssert = new SoftAssert();
        loginPage = homePage.clickSignInBtn();
        loginPage.signIn();

        //Make sure we correctly signed in
        softAssert.assertEquals(homePage.getSignInName(),"Hello, Mohamed","Not Signed In Correctly");
        softAssert.assertAll();
    }
    */

    @Test(priority = 2)
    public void navigateToVideoGames() {
        SoftAssert softAssert = new SoftAssert();
        //Make sure we landed in the correct page
        videoGamesPage = homePage.goToAllVideoGamesPage();
        softAssert.assertEquals(videoGamesPage.getVideoGamesBanner(),"Video Games","Not Signed In Correctly");
        softAssert.assertAll();
    }
//4. from the filter menu on the left side add filter “free shipping” & add the filter of condition “ new”

    @Test(priority = 3,dependsOnMethods = {"navigateToVideoGames"})
    public void filterVideoGames() {
        SoftAssert softAssert = new SoftAssert();

        videoGamesFreeShippingFiltered = videoGamesPage.clickFreeShippingFilter();

        //Check that free shipping checkbox is checked using isSelected method
        Boolean freeShippingCheck = videoGamesFreeShippingFiltered.getFreeShippingCheckBox();
        softAssert.assertTrue(freeShippingCheck,"FreeShipping filter not checked");

        softAssert.assertAll();
    }

    @Test(priority = 4,dependsOnMethods = {"filterVideoGames"})
    public void filterWithNewCondition() {
        SoftAssert softAssert = new SoftAssert();
        videoGamesNewFiltered = videoGamesFreeShippingFiltered.selectNewConditionFilter();

        //Check that clear is displayed after selecting New Condition filter
        Boolean clearFilter = videoGamesNewFiltered.checkClearFilterIsDisplayed();

        softAssert.assertTrue(clearFilter,"Clear Filters not displayed");
        softAssert.assertAll();
    }

    // 5. open the sort menu then sort by price: high to low

    @Test(priority = 5,dependsOnMethods = {"filterWithNewCondition"})
    public void sortByPriceFromHighToLow() {
        SoftAssert softAssert = new SoftAssert();
        sortedVideoGamesPage = videoGamesNewFiltered.sortByPrice();

        //Check that Price is sorted after from High to Low
        String sortHighToLowText = sortedVideoGamesPage.sortHighToLowGetText();
        softAssert.assertEquals(sortHighToLowText,"Price: High to Low","Wrong Sort");

        softAssert.assertAll();
    }



    }
