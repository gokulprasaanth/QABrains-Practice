package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver = null; // If protected, variable can only be accessed within package and by subclasses

    @BeforeMethod // This annotated method will run before each test method.
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://practice.qabrains.com/ecommerce/login");
    }

    @AfterMethod // This annotated method will run after each test method.
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
