package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.apache.poi.ss.formula.ptg.ScalarConstantPtg;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AdventureDetailsPage {
    RemoteWebDriver driver;

    // @FindBy(xpath = "//input[@name='name']")
    // WebElement namElement;

    // @FindBy(xpath = "//input[@type='date']")
    // WebElement calenderElement;

    // @FindBy(xpath = "//input[@name='person']")
    // WebElement personCount;

    @FindBy(className ="reserve-button" )
    WebElement reserveButton;

    @FindBy(id = "reserved-banner" )
    WebElement reserveBanner;

    @FindBy(xpath ="//strong[text()='here']")
    WebElement reservationHistoryLink;

    @FindBy(xpath ="//tbody[@id='reservation-table']/tr/th")
    List<WebElement> transactionId;

    @FindBy(xpath = "//button[@class='cancel-button']")
    WebElement cancelButton;

    @FindBy(id = "no-reservation-banner")
    WebElement noReservationDisplayedBanner;

    @FindBy(xpath = "//a[text()='Home']")
    WebElement homeButton;
    
    
    
    public AdventureDetailsPage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(ajax, this);
    }
    
    
    public boolean performReservations(String GuestName, String Date,String count) throws InterruptedException{
        WebDriverWait wait = new WebDriverWait(driver, 30);
    
        // wait.until(ExpectedConditions.elementToBeClickable(namElement));
        // namElement.sendKeys(GuestName);
        WebElement namElement=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//input[@name='name']"),3);
        SeleniumWrapper.sendKeys(namElement, GuestName);
        System.out.println("Guest name entered: " + GuestName);
        Thread.sleep(3000);
    
        String[] dateParts = Date.split("-");
            String day= dateParts[0];
            String month = dateParts[1];
            String year = dateParts[2];
    
        // wait.until(ExpectedConditions.visibilityOf(calenderElement));
        WebElement calenderElement=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//input[@type='date']"), 3);
        // calenderElement.sendKeys(month);
        SeleniumWrapper.sendKeys(calenderElement, month);
        // calenderElement.sendKeys(day);
        SeleniumWrapper.sendKeys(calenderElement, day);
        // calenderElement.sendKeys(year);
        SeleniumWrapper.sendKeys(calenderElement, year);
        String enteredDate = calenderElement.getAttribute("value");
        System.out.println("Date entered correctly: "+enteredDate);
        Thread.sleep(3000);
        
    
        // wait.until(ExpectedConditions.elementToBeClickable(personCount));
        WebElement personCount=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//input[@name='person']"), 3);
        // personCount.clear();
        // personCount.sendKeys(count);
        SeleniumWrapper.sendKeys(personCount, count);
        String countEntered=personCount.getAttribute("value");
        if(countEntered.equals(count)){
            System.out.println("Person count populated properly: "+countEntered);
            Thread.sleep(3000);
        }else{
            System.out.println("Person count entered is incorrect: "+countEntered);
        }


        wait.until(ExpectedConditions.elementToBeClickable(reserveButton));
        // reserveButton.click();
        // WebElement reserveButton=SeleniumWrapper.findElementWithRetry(driver, By.className("reserve-button"), 3);
        SeleniumWrapper.click(reserveButton,driver);


        wait.until(ExpectedConditions.elementToBeClickable(reserveBanner));
        
        // WebElement reserveBanner=SeleniumWrapper.findElementWithRetry(driver, By.id("reserved-banner"), 3);
        String successMessage=reserveBanner.getText();
        System.out.println(successMessage);

        // wait.until(ExpectedConditions.elementToBeClickable(reservationHistoryLink));
        // reservationHistoryLink.click();
 
        WebElement reservationHistoryLink=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//strong[text()='here']"), 3);
        SeleniumWrapper.click(reservationHistoryLink, driver);

        try {
            wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/"));
            System.out.println("Navigated to reservations page");
           
        } catch (Exception e) {
            System.out.println("Timed out waiting for login page URL");
            return false; 
        }
        wait.until(ExpectedConditions.visibilityOfAllElements(transactionId));
        for(WebElement id:transactionId){
            String transactionIDText=id.getText();
        System.out.println("All bookings done are: "+transactionIDText);
       

        }
        return true;
    }
     

        
                 
    
    public boolean cancelReservations() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        

        wait.until(ExpectedConditions.visibilityOfAllElements(transactionId));
        for(WebElement id:transactionId){
            String transactionIDText=id.getText();
        System.out.println("All bookings done are: "+transactionIDText);
        }


        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        // cancelButton.click();
       
        // WebElement cancelButton=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//button[@class='cancel-button']"), 3);
        SeleniumWrapper.click(cancelButton, driver);
        System.out.println("Cancel button clicked.");

        Thread.sleep(3000);
        driver.navigate().refresh();
        Thread.sleep(5000);
        System.out.println("Page is refreshed");
     

        wait.until(ExpectedConditions.visibilityOf(noReservationDisplayedBanner));
        
        // WebElement noReservationDisplayedBanner=SeleniumWrapper.findElementWithRetry(driver, By.id("no-reservation-banner"), 3);
        String noReservationDisplayedBannerText=noReservationDisplayedBanner.getText();
        System.out.println(noReservationDisplayedBannerText);
        return true;
        

    }

    public boolean navigateBackToHomePAge(){
        WebDriverWait wait=new WebDriverWait(driver, 30);
        // wait.until(ExpectedConditions.elementToBeClickable(homeButton));
        // homeButton.click();
        
    
        // WebElement homeButton=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//a[text()='Home']"), 3);
        SeleniumWrapper.click(homeButton, driver);

       boolean status= wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/"));
       return status;


    }
}
