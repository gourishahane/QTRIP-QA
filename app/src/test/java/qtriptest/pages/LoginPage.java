package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    RemoteWebDriver driver;

    @FindBy(name = "email")
    WebElement username_txt_box;

    @FindBy(name ="password")
    WebElement password_txt_box;

    @FindBy(xpath ="//button[@class='btn btn-primary btn-login']")
    WebElement login_button;

    @FindBy(xpath = "//button[@class='navbar-toggler']")
    WebElement navigationBar;

    @FindBy(xpath ="//div[text()='Logout']")
    WebElement logout_button;

    @FindBy(xpath = "//a[text()='Login Here']")
    WebElement loginHere_button;
   
    public LoginPage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 30);
        PageFactory.initElements(ajax, this);
    }

    public Boolean loginExistingUser(String username, String password) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(ExpectedConditions.visibilityOf(username_txt_box)).sendKeys(username);
        System.out.println("Username entered");

        wait.until(ExpectedConditions.visibilityOf(password_txt_box)).sendKeys(password);
        System.out.println("Password entered");

        wait.until(ExpectedConditions.elementToBeClickable(login_button)).click();
        System.out.println("Clicked on 'Login to QTrip' button");
        return true;
    }

   
}
