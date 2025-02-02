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

public class HomePage {
    RemoteWebDriver driver;
 
    String qTripUrl = "https://qtripdynamic-qa-frontend.vercel.app/";
    

    @FindBy(xpath = "//a[text()='Register']")
    WebElement registerButton;
    
    // @FindBy(xpath ="//div[text()='Logout']")
    // WebElement logout_button;

    // @FindBy(xpath = "//a[text()='Login Here']")
    // WebElement loginHere_button;

    // @FindBy(className ="hero-input")
    // WebElement searchField;

    
    @FindBy(xpath = "//ul[@id='results']")
    WebElement resultElement;
   

    public HomePage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(ajax, this);
    }

    public void navigateToHomePage() {

        SeleniumWrapper.navigate(driver, qTripUrl);
    }

    
    public Boolean isRegisterButtonVisible() throws Exception {
        Thread.sleep(5000);
        Boolean status = registerButton.isDisplayed() && registerButton.isEnabled();
        System.out.println("Register button is displayed and enabled.");
        return status;
    }

    public void navigateToRegisterPage() throws InterruptedException{
    //    registerButton.click();
       SeleniumWrapper.click(registerButton, driver);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.urlContains("register"));
        System.out.println("Registeration page  is displayed correctly");
     }

    public Boolean verifyLogout() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        
;

        // wait.until(ExpectedConditions.elementToBeClickable(logout_button)).click();
        
    
    WebElement logout_button=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//div[text()='Logout']"), 3);
        SeleniumWrapper.click(logout_button, driver);
        System.out.println("Logout button clicked");

        // wait.until(ExpectedConditions.visibilityOf(loginHere_button));
        
        WebElement loginHere_button=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//a[text()='Login Here']"), 3);
        boolean status = loginHere_button.isDisplayed() && loginHere_button.isEnabled();
        System.out.println("Login Here button displayed after logout: " + status);
        return status;
    }
    public boolean verifySearchCity(String cityname) throws InterruptedException{
        WebDriverWait wait= new WebDriverWait(driver, 30);
        
        // wait.until(ExpectedConditions.elementToBeClickable(searchField));
        
        WebElement searchField=SeleniumWrapper.findElementWithRetry(driver, By.className("hero-input"), 3);
        Thread.sleep(5000);
        // searchField.sendKeys(cityname);
        SeleniumWrapper.sendKeys(searchField, cityname);
        System.out.println("City name entered: "+cityname);
       
        wait.until(ExpectedConditions.visibilityOf(resultElement));
       
        // WebElement resultElement=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//ul[@id='results']"), 3);
        String resultTitle=resultElement.getText();
        if(resultTitle.equals(cityname)){
            System.out.println("City name is correctly displayed on autocomplete");
            // resultElement.click();
            SeleniumWrapper.click(resultElement, driver);
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
