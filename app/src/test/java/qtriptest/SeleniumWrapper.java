package qtriptest;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumWrapper {
    public static boolean click(WebElement elementToClick, WebDriver driver) {
        try {
            if (elementToClick.isDisplayed()) {
        // JavascriptExecutor js=new JavascriptExecutor(driver);
        //     js.executeScript("argument[0].scrollIntoView(true);", elementToClick);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementToClick);
            elementToClick.click();
                System.out.println("Element is displayed, scrolled into view, and clicked successfully.");
                return true;

            
            } else {
                System.out.println("Element does not exist or is not displayed.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while clicking an element: " + e.getMessage());
            return false;
        }
    }

    public static boolean sendKeys(WebElement inputBox,String keysToSend){
        try{
            if(inputBox.isEnabled()&&inputBox.isDisplayed()){
                inputBox.clear();
                inputBox.sendKeys(keysToSend);
                System.out.println("Text cleared and new text entered: " + keysToSend);
                return true;

            }else{
                System.out.println("Input box is either not displayed or not enabled.");
                return false;
            }
        }catch(Exception e){
            System.out.println("Exception occurred while sending keys: " + e.getMessage());
            return false;


        }
        
    }
    public static boolean navigate(WebDriver driver,String url){
        try{

            if (driver == null) {
                throw new NullPointerException("Driver is null!");
            }
    
            if (!driver.getCurrentUrl().equals(url)) {
                driver.get(url);
                System.out.println("Navigated to required url.");
                return true;
            }else{
            System.out.println("Failed to navigate to required url.");
                return false;
            }
    }catch(Exception e){
        System.out.println("Exception occurred while navigating to url: " + e.getMessage());
        return false;
    }
}

public static WebElement findElementWithRetry(WebDriver driver, By by, int retryCount) {
    WebElement element = null;
    int attempts = 0;

    while (attempts < retryCount) {
        try {
            element = driver.findElement(by); 
            return element; 
        } catch (Exception e) {
            System.out.println("Attempt " + (attempts + 1) + ": Element not found. Retrying...");
            attempts++;
        }
    }

    System.out.println("Element not found after " + retryCount + " attempts.");
    return null; 
}
}