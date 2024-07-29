package qtriptest.tests;

import qtriptest.DriverSingleton;
import qtriptest.ExternalDataProvider;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_02 {
    static RemoteWebDriver driver;

    
    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s | %s | %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws MalformedURLException {
        
        driver = DriverSingleton.getDriver("chrome");
    }

    
    @Test(description = "Verify that search and filters work fine",priority = 2,groups = {"Search and Filter flow"},enabled = true,dataProvider = "qTripDataForTestCase02", dataProviderClass = ExternalDataProvider.class)
    public void TestCase02(String CityName,String Category_Filter,String Duration_Filter,String ExpectedFilteredResults,String ExpectedUnFilteredResults) {
        SoftAssert softAssert = new SoftAssert();

        try {

        logStatus("TestCase02", "Verify search and Filters", "Started");
        HomePage homePage = new HomePage(driver);  
        homePage.navigateToHomePage();
        logStatus("HomePage test", "Navigation to HomePage", "Success");
        
        logStatus("HomePage test", "Verify search city functionality", "Started");
        boolean searchResult = homePage.verifySearchCity(CityName);
        softAssert.assertTrue(searchResult, "Search city functionality failed");
       

        logStatus("AdventurePage test", "Verify Filter functionality", "Started");
        AdventurePage adventurePage=new AdventurePage(driver);
        boolean filterResult=adventurePage.verifyFilter(Category_Filter,Duration_Filter,ExpectedFilteredResults,ExpectedUnFilteredResults);
        softAssert.assertTrue(filterResult, "Filter results are not correct");
       
        logStatus("AdventurePage test", "Verify search and Filters", "Succeed");

        } catch (Exception e) {
            logStatus("TestCase02", "Test execution failed", "Failed");
            e.printStackTrace();
        }

        softAssert.assertAll();
    }

    
    @AfterSuite(alwaysRun = true)
    public void closeBrowser() {
        DriverSingleton.quitDriver();
    }


}
