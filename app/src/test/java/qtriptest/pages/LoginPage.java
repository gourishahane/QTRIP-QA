package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    RemoteWebDriver driver;

    // @FindBy(name = "email")
    // WebElement username_txt_box;

    // @FindBy(name ="password")
    // WebElement password_txt_box;

    // @FindBy(xpath ="//button[@class='btn btn-primary btn-login']")
    // WebElement login_button;


    // @FindBy(xpath ="//div[text()='Logout']")
    // WebElement logout_button;

   
   
    public LoginPage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 30);
        PageFactory.initElements(ajax, this);
    }

    public Boolean loginExistingUser(String username, String password) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        // wait.until(ExpectedConditions.visibilityOf(username_txt_box)).sendKeys(username);
        WebElement username_txt_box=SeleniumWrapper.findElementWithRetry(driver, By.name("email"), 3);
        SeleniumWrapper.sendKeys(username_txt_box, username);
        System.out.println("Username entered");

        // wait.until(ExpectedConditions.visibilityOf(password_txt_box)).sendKeys(password);
        WebElement password_txt_box=SeleniumWrapper.findElementWithRetry(driver, By.name("password"), 3);
        SeleniumWrapper.sendKeys(password_txt_box, password);
        System.out.println("Password entered");

        
        WebElement login_button=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//button[@class='btn btn-primary btn-login']"), 3);
        wait.until(ExpectedConditions.elementToBeClickable(login_button)).click();
        SeleniumWrapper.click(login_button, driver);
        System.out.println("Clicked on 'Login to QTrip' button");
        
        
        Thread.sleep(5000);
        WebElement logout_button=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//div[text()='Logout']"), 3);
        // wait.until(ExpectedConditions.visibilityOf(logout_button));
        if (!logout_button.isDisplayed() || !logout_button.isEnabled()) {
            throw new Exception("Logout is NOT displayed or NOT enabled.");
        }
        System.out.println("Logout button displayed");
        return true;
    }

   
}
