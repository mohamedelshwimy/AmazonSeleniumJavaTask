package Scenario;

import Base.BaseTests;
import Pages.LoginPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class E2EScenario extends BaseTests {
    LoginPage loginPage;

    /*
    @Test(priority = 1)
    public void loginToAmazon() {
        SoftAssert softAssert = new SoftAssert();
        loginPage = homePage.clickSignInBtn();
        loginPage.signIn();

        softAssert.assertEquals(homePage.getSignInName(),"Hello, Mohamed","Not Signed In Correctly");
        softAssert.assertAll();
    }
    */

    @Test(priority = 2)
    public void navigateToVideoGames() {
        homePage.goToAllVideoGamesPage();
    }

    }
