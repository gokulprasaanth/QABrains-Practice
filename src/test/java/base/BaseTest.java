package base;

import Utils.ExtentReportManager;
import Utils.Log;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;

import static Utils.ExtentReportManager.test;

public class BaseTest {

    protected WebDriver driver = null; // If protected, variable can only be accessed within package and by subclasses
    protected static ExtentReports extent;
    protected static String screenshotPath;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentReportManager.getReportInstance();
    }

    @AfterSuite
    public void teardownReport() {
        extent.flush();
    }

    @BeforeMethod // This annotated method will run before each test method.
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        Log.info("Launching chrome browser instance");
        driver.get("https://practice.qabrains.com/ecommerce/login");
    }


    @AfterMethod // This annotated method will run after each test method.
    public void teardown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String methodName = result.getName();
            screenshotPath = ExtentReportManager.captureScreenshot(driver, methodName + "_CrashState");
            if (screenshotPath != null) {
                test.fail("Test failed! Check the crash state screenshot",
                        MediaEntityBuilder.createScreenCaptureFromPath("../" + screenshotPath).build());
                test.fail(result.getThrowable().getMessage());
            }
        }

        if (driver != null) {
            Log.info("Closing the browser");
            driver.quit();
        }
//        if(extent!=null){
//            extent.flush();
//        }
    }

}
