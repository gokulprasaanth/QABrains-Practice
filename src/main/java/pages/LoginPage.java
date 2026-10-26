package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LoginPage {

    //Variable Declaration
    private final WebDriver driver; /*Each webpage is an independent component.
                                      By keeping the driver 'private', you protect it from being
                                      altered outside of that specific page object.
                                      'Final' - Once the constructor sets the driver, it shouldn't be changed.*/
    private final WebDriverWait wait;

    private final By usernameTextBox = By.id("email");
    private final By passwordTextBox = By.id("password");
    private final By loginButton = By.xpath("/html/body/div[2]/div/div/div/form/button");

    //Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait (driver, Duration.ofSeconds(10)); //Selenium will wait 10 seconds for the webpage's elements to catch-up before throwing an error.
                                                                        //It will try to locate element in question every 0.5 seconds until time runs out.
                                                                        //Once the element appeared and located, it will move to the next line of code.
    }

    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    //Page Actions (methods)
    public void setUsernameTextBox(String username) {
        WebElement element = waitForElement(usernameTextBox);
        element.clear();
        element.sendKeys(username);
    }

    public void setPassword(String password) {
        WebElement element = waitForElement(passwordTextBox);
        element.clear();
        element.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
    }

}
