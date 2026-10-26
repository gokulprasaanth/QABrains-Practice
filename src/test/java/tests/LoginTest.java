package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.util.Objects;

public class LoginTest extends BaseTest {

    @Test //Marks a method as an executable test. TestNG runs the @BeforeMethod before this test, and the @AfterMethod right after this test.
    public void validLoginTest() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.setUsernameTextBox("test@qabrains.com");
        loginPage.setPassword("Password123");
        loginPage.clickLoginButton();
        String expectedTitle = "QA Practice Site";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Title is incorrect!");
    }
}
