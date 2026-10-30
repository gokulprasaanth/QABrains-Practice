package tests;

import Utils.ExtentReportManager;
import Utils.Log;
import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.util.Objects;

public class LoginTest extends BaseTest {

    protected static ExtentTest test;

    @Test
    //Marks a method as an executable test. TestNG runs the @BeforeMethod before this test, and the @AfterMethod right after this test.
    public void validLoginTest() {
        Log.info("Starting login test");
        test = ExtentReportManager.createTest("LoginTest");
        LoginPage loginPage = new LoginPage(driver);
        test.info("Navigated to login page");
        Log.info("Entering credentials");
        loginPage.setUsernameTextBox("test@qabrains.com");
        loginPage.setPassword("Password123");
        loginPage.clickLoginButton();
        test.info("Entered credentials and clicking on login button");
        String expectedTitle = "QA Practice Site";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Title is incorrect!");
        test.pass("Logged in successfully");

        if (actualTitle.equals(expectedTitle)) {
            Log.info("Title verification passed successfully!");

        } else {
            Log.error("Title verification failed and title didn't match!");
        }
    }
}
