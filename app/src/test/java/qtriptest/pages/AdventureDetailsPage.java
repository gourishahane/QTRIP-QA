package qtriptest.pages;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AdventureDetailsPage {
    RemoteWebDriver driver;

    @FindBy(xpath = "//input[@name='name']")
    WebElement namElement;

    @FindBy(xpath = "//input[@type='date']")
    WebElement calenderElement;

    @FindBy(xpath = "//input[@name='person']")
    WebElement personCount;

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
    
        wait.until(ExpectedConditions.elementToBeClickable(namElement));
        namElement.sendKeys(GuestName);
        System.out.println("Guest name entered: " + GuestName);
        Thread.sleep(3000);
    
        String[] dateParts = Date.split("-");
            String day= dateParts[0];
            String month = dateParts[1];
            String year = dateParts[2];
    
        wait.until(ExpectedConditions.visibilityOf(calenderElement));
        calenderElement.sendKeys(month);
        calenderElement.sendKeys(day);
        calenderElement.sendKeys(year);
        String enteredDate = calenderElement.getAttribute("value");
        System.out.println("Date entered correctly: "+enteredDate);
        Thread.sleep(3000);
        
    
        wait.until(ExpectedConditions.elementToBeClickable(personCount));
        personCount.clear();
        personCount.sendKeys(count);
        String countEntered=personCount.getAttribute("value");
        if(countEntered.equals(count)){
            System.out.println("Person count populated properly: "+countEntered);
            Thread.sleep(3000);
        }else{
            System.out.println("Person count entered is incorrect: "+countEntered);
        }


        wait.until(ExpectedConditions.elementToBeClickable(reserveButton));
        reserveButton.click();


        wait.until(ExpectedConditions.elementToBeClickable(reserveBanner));
        String successMessage=reserveBanner.getText();
        System.out.println(successMessage);

        wait.until(ExpectedConditions.elementToBeClickable(reservationHistoryLink));
        reservationHistoryLink.click();

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
        cancelButton.click();
        System.out.println("Cancel button clicked.");

        Thread.sleep(3000);
        driver.navigate().refresh();
        Thread.sleep(5000);
        System.out.println("Page is refreshed");
     

        wait.until(ExpectedConditions.visibilityOf(noReservationDisplayedBanner));
        String noReservationDisplayedBannerText=noReservationDisplayedBanner.getText();
        System.out.println(noReservationDisplayedBannerText);
        return true;
        

    }

    public boolean navigateBackToHomePAge(){
        WebDriverWait wait=new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(homeButton));
        homeButton.click();

       boolean status= wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/"));
       return status;


    }
}
