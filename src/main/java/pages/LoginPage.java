package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

    @FindBy(id = "email")
    WebElement emailTextBox;

    @FindBy(id = "password")
    WebElement passwordTextBox;

    @FindBy(css = "button.btn-submit[type='submit']")
    WebElement loginButton;

//    private final By emailTextBox = By.id("email");
//    private final By passwordTextBox = By.id("password");
//    private final By loginButton = By.cssSelector("button.btn-submit[type='submit']");

    //Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait (driver, Duration.ofSeconds(10)); //Selenium will wait 10 seconds for the webpage's elements to catch-up before throwing an error.
                                                                        //It will try to locate element in question every 0.5 seconds until time runs out.
                                                                        //Once the element appeared and located, it will move to the next line of code.
    }

//    public WebElement waitForElement(By locator) {
//        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }

    //Page Actions (methods)
    public void setEmailTextBox(String username) {
        wait.until(ExpectedConditions.visibilityOf(emailTextBox));
        emailTextBox.clear();
        emailTextBox.sendKeys(username);
    }

    public void setPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordTextBox));
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

}
