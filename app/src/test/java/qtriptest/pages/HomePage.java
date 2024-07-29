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

    @FindBy(className ="hero-input")
    WebElement searchField;

    
    @FindBy(xpath = "//ul[@id='results']")
    WebElement resultElement;
   

    public HomePage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(ajax, this);
    }

    public void navigateToHomePage() {

        if (this.driver == null) {
            throw new NullPointerException("Driver is null!");
        }
        
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
        
;

        wait.until(ExpectedConditions.elementToBeClickable(logout_button)).click();
        System.out.println("Logout button clicked");

        wait.until(ExpectedConditions.visibilityOf(loginHere_button));
        boolean status = loginHere_button.isDisplayed() && loginHere_button.isEnabled();
        System.out.println("Login Here button displayed after logout: " + status);
        return status;
    }
    public boolean verifySearchCity(String cityname) throws InterruptedException{
        WebDriverWait wait= new WebDriverWait(driver, 30);
        
        wait.until(ExpectedConditions.elementToBeClickable(searchField));
        Thread.sleep(5000);
        searchField.sendKeys(cityname);
        System.out.println("City name entered: "+cityname);
       
        wait.until(ExpectedConditions.visibilityOf(resultElement));
        String resultTitle=resultElement.getText();
        if(resultTitle.equals(cityname)){
            System.out.println("City name is correctly displayed on autocomplete");
            resultElement.click();
            wait.until(ExpectedConditions.urlContains("/pages/adventures/"));    
            System.out.println("Navigated to adventure page");
            Thread.sleep(5000);
            return true;         
        }else if(resultTitle=="No City found"){
            System.out.println("No matches found message is displayed");
            return false;
        }
        return false;
        
     }
}
