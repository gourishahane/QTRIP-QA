package qtriptest.pages;


import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventurePage {
    RemoteWebDriver driver;
    
    // @FindBy(xpath = "//select[@id='duration-select']")
    // WebElement filtersDropdown;
    
    @FindBy(xpath = "//select[@id='duration-select']/option")
    List<WebElement> filtersOptions;
    
    // @FindBy(xpath = "//select[@id='category-select'] ")
    // WebElement addCategoryDropdown;

    @FindBy(xpath = "//select[@id='category-select']/option")
    List<WebElement> categoryOptions;

    @FindBy(xpath = "//div[@class='activity-card']")
    List<WebElement> activityCard;
    
    // @FindBy(xpath = "//div[@onclick='clearCategory(event)']")
    // WebElement clearCategory;

    // @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
    // WebElement clearDurationFilter;

    // @FindBy(xpath = "//input[@name='search-adventures']")
    // WebElement searchAdventures;
    
    // @FindBy(xpath = "//div[@class='activity-card']")
    // WebElement adventureActivity;

  


    
   

    public AdventurePage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(ajax, this);
    }

public boolean verifyFilter(String category_filter_option,String duration_filter_option,String ExpectedFilteredResults,String ExpectedUnFilteredResults) throws InterruptedException{
        WebDriverWait wait= new WebDriverWait(driver, 30);
        
        // wait.until(ExpectedConditions.elementToBeClickable(filtersDropdown));
        Thread.sleep(3000);
        // filtersDropdown.click();
        // @FindBy(xpath = "//select[@id='duration-select']")
        WebElement filtersDropdown=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//select[@id='duration-select']"), 3);
        SeleniumWrapper.click(filtersDropdown, driver);
        Thread.sleep(3000);

        for(WebElement option:filtersOptions){
            String optionTitle=option.getText();
          
            if(optionTitle.equals(duration_filter_option)){
                // option.click();
                SeleniumWrapper.click(option, driver);
                Thread.sleep(3000);
                System.out.println("Filter option found and selected.");
                break;
            }
        }

        // wait.until(ExpectedConditions.elementToBeClickable(addCategoryDropdown));
        Thread.sleep(3000);
        // addCategoryDropdown.click();
        WebElement addCategoryDropdown=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//select[@id='category-select']"),3);
        SeleniumWrapper.click(addCategoryDropdown, driver);
        Thread.sleep(3000);
        

        for(WebElement option:categoryOptions){
            String optionText=option.getText();
            if(optionText.equals(category_filter_option)){
                // option.click();
                SeleniumWrapper.click(option, driver);
                Thread.sleep(5000);
                System.out.println("category option found and selected");
                break;
            }

        }
        wait.until(ExpectedConditions.visibilityOfAllElements(activityCard));
        int count = activityCard.size();
        int expectedFilteredCount = Integer.parseInt(ExpectedFilteredResults);
        if (count == expectedFilteredCount) {
            System.out.println("Appropriate filtered data is displayed which is: " + count);
        } else {
            System.out.println("Filtered data count mismatch. Expected: " + expectedFilteredCount + ", Found: " + count);
        }

        // clearCategory.click();
        WebElement clearCategory=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//div[@onclick='clearCategory(event)']"), 3);
        SeleniumWrapper.click(clearCategory, driver);
        Thread.sleep(2000);

        // clearDurationFilter.click();
        // @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
        WebElement clearDurationFilter=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//div[@onclick='clearDuration(event)']"), 3);

        SeleniumWrapper.click(clearDurationFilter, driver);
        Thread.sleep(2000);
        
        int expectedUnFilteredCount = Integer.parseInt(ExpectedUnFilteredResults);
        count=activityCard.size();
        if(count==expectedUnFilteredCount){
            System.out.println("Appropriate unfiltered data is displayed which is: "+count);
        }
        return true;
        
    }
    public boolean verifySearchAdventures(String AdventureName) throws InterruptedException{
        WebDriverWait wait= new WebDriverWait(driver, 30);
        
        WebElement searchAdventures=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//input[@name='search-adventures']"), 3);
        wait.until(ExpectedConditions.elementToBeClickable(searchAdventures));
        // searchAdventures.sendKeys(AdventureName);
        SeleniumWrapper.sendKeys(searchAdventures, AdventureName);
        System.out.println("AdventureName entered :"+ AdventureName);

        // wait.until(ExpectedConditions.elementToBeClickable(adventureActivity));
        Thread.sleep(3000);
        // adventureActivity.click();
        
        WebElement adventureActivity=SeleniumWrapper.findElementWithRetry(driver, By.xpath("//div[@class='activity-card']"), 3);
        SeleniumWrapper.click(adventureActivity, driver);
        System.out.println("Adventure Activity clicked");
        
        Thread.sleep(3000);
        wait.until(ExpectedConditions.urlContains("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/detail/?adventure="));    
        System.out.println("Navigated to adventure reservation page");
        Thread.sleep(5000);
        return true;
        
    }
    
}



