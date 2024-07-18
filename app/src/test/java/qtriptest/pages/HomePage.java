package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    RemoteWebDriver driver;
 
    String qTripUrl = "https://qtripdynamic-qa-frontend.vercel.app/";
    

    @FindBy(xpath = "//a[text()='Register']")
    WebElement registerButton;
    
    @FindBy(xpath ="//div[text()='Logout']")
    WebElement logout_button;

    @FindBy(xpath = "//a[text()='Login Here']")
    WebElement loginHere_button;
   

    public HomePage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(ajax, this);
    }

    public void navigateToHomePage() {
        
        if (!this.driver.getCurrentUrl().equals(qTripUrl)) {
            this.driver.get(qTripUrl);
        }
        System.out.println("Navigated to Home Page.");
    }
   

    public Boolean isRegisterButtonVisible() throws Exception {
        Thread.sleep(5000);
        Boolean status = registerButton.isDisplayed() && registerButton.isEnabled();
        System.out.println("Register button is displayed and enabled.");
        return status;
    }

    public void navigateToRegisterPage() throws InterruptedException{
       registerButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.urlContains("register"));
        System.out.println("Registeration page  is displayed correctly");
     }

    public Boolean verifyLogout() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        

        wait.until(ExpectedConditions.visibilityOf(logout_button));
        if (!logout_button.isDisplayed() || !logout_button.isEnabled()) {
            throw new Exception("Logout is NOT displayed or NOT enabled.");
        }
        System.out.println("Logout button displayed");

        wait.until(ExpectedConditions.elementToBeClickable(logout_button)).click();
        System.out.println("Logout button clicked");

        wait.until(ExpectedConditions.visibilityOf(loginHere_button));
        boolean status = loginHere_button.isDisplayed() && loginHere_button.isEnabled();
        System.out.println("Login Here button displayed after logout: " + status);
        return status;
    }
}
