package base;

import Utils.ExtentReportManager;
import Utils.Log;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected WebDriver driver = null; // If protected, variable can only be accessed within package and by subclasses
    protected static ExtentReports extent;

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
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
