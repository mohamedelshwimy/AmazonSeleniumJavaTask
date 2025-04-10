package Scenario;

import Base.BaseTests;
import Pages.LoginPage;
import Pages.VideoGamesFilteredResultsPage;
import Pages.VideoGamesPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class E2EScenario extends BaseTests {
    LoginPage loginPage;
    VideoGamesPage videoGamesPage;
    VideoGamesFilteredResultsPage videoGamesFilteredResultsPage;

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
        videoGamesFilteredResultsPage = videoGamesPage.clickFreeShippingFilter();
        softAssert.assertTrue(videoGamesFilteredResultsPage.getFreeShippingCheckBox(),"FreeShipping filter not checked");
        softAssert.assertAll();
    }


    }
