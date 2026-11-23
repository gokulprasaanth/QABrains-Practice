package tests;

import utils.ExcelUtils;
import utils.ExtentReportManager;
import utils.Log;
import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class LoginTest extends BaseTest {

    protected ExtentTest test;
    private static WebDriverWait wait;

    //indices = {2} → Runs 3nd data row (Excel Row 4 where valid credentials are stored)
    @DataProvider(name = "loginData", indices = {2})
    public Object[][] loginData() throws Exception {
        String filePath = System.getProperty("user.dir") + "/testdata/QAPractice-Testdata.xlsx";
        ExcelUtils.loadExcel(filePath, "QAPractice-Testdata");
        int rowCount = ExcelUtils.getRowCount();
        int  columnCount = ExcelUtils.getColumnCount(); // Total columns in header row (row 0)

        Object[][] data = new Object[rowCount-1][1];

        for (int i = 1; i < rowCount; i++) {
            Map<String, String> rowMap = new HashMap<>();
            for  (int j = 0; j < columnCount; j++) {
                String header = ExcelUtils.getCellData(0, j); // Row 0 = Column Heade
                String value = ExcelUtils.getCellData(i, j); // Row i = Test Data
                rowMap.put(header, value);
            }
            //Packs the entire row's Map into 1 slot ([0]) for test run [i - 1], passing all columns as a single parameter to @Test
            //IOW, first bracket controls which test run and second bracket controls which parameter
            data[i-1][0] = rowMap;
        }

        // Not a good approach - column value is hardcoded
//        Object[][] data = new Object[rowCount - 1][2];
//        for (int i = 1; i < rowCount; i++) { //Index position 0 corresponds to Header row, so we start from i=1
//            data[i - 1][0] = ExcelUtils.getCellData(i, 0); //email.    Worksheet row 1 → data[0]
//            data[i - 1][1] = ExcelUtils.getCellData(i, 1); //password
//        }
        ExcelUtils.closeExcel();
        return data;
    }

//    @DataProvider(name = "approvedLoginData")
//    public Object[][] approvedLoginData() throws Exception {
//        return new Object[][]{
//                {"admin", "admin"},
//                {"dbaadmin", "dbaadmin"},
//                {"bizadmin", "bizadmin"}
//        };
//    }

    @Test(priority = 1, dataProvider = "loginData")
    //Marks a method as an executable test. TestNG runs the @BeforeMethod before this test, and the @AfterMethod right after this test.
    public void validLoginTest(Map<String, String> testData) throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Log.info("Starting login test");
        test = ExtentReportManager.createTest("Login Test with Valid Credentials");
        LoginPage loginPage = new LoginPage(driver);
        test.info("Navigated to login page");
        verifyCheckpoint("ValidLogin_Landing", "Initial login landing view captured.");
        Log.info("Entering credentials");
//        loginPage.setEmailTextBox("test@qabrains.com");
//        loginPage.setPassword("Password123");
        String email = testData.get("Email");
        String password = testData.get("Password");
        loginPage.setEmailTextBox(email);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
        test.info("Entered credentials and clicking on login button");
        wait.until(ExpectedConditions.urlToBe("https://practice.qabrains.com/ecommerce"));
        String expectedURL = "https://practice.qabrains.com/ecommerce";
        String actualURL = driver.getCurrentUrl();
        WebElement productHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.font-oswald")));
        String headerText = productHeader.getText();
        Log.info("Dashboard verification text captured: " + headerText);
        verifyCheckpoint("ValidLogin_SuccessDashboard", "Successfully landed on the dashboard view showing " + headerText);
        Assert.assertEquals(actualURL, expectedURL, "URL isn't matching!");
        test.pass("Logged in successfully");

        if (actualURL.equals(expectedURL)) {
            Log.info("URL verification passed successfully!");

        } else {
            Log.error("URL verification failed and URL didn't match!");
        }
    }

//    @Test(priority = 2)
//    public void loginTestWithInvalidCredentials() {
//        Log.info("Starting login test");
//        test = ExtentReportManager.createTest("Login Test with Invalid Credentials");
//        LoginPage loginPage = new LoginPage(driver);
//        test.info("Navigated to login page");
//        verifyCheckpoint("InvalidLogin_Landing", "Initial invalid login landing state captured.");
//        Log.info("Entering credentials");
//        loginPage.setEmailTextBox("test@qabrains.com");
//        loginPage.setPassword("P");
//        loginPage.clickLoginButton();
//        test.info("Entered credentials and clicking on login button");
//        String expectedURL = "https://practice.qabrains.com/ecommerce";
//        String actualURL = driver.getCurrentUrl();
//        verifyCheckpoint("InvalidLogin_AlertState", "Login validation system response view captured.");
//
//        Assert.assertEquals(actualURL, expectedURL, "URL isn't matching!");
//        test.fail("Login failed... Please try again with valid credentials or sign up if you're new to the website");
//    }

    private void verifyCheckpoint(String checkpointName, String logDetails) {
        String path = ExtentReportManager.captureScreenshot(driver, checkpointName);
        if (path != null) {
            test.info(logDetails,
                    MediaEntityBuilder.createScreenCaptureFromPath("../" + path).build());
        }
    }
}
