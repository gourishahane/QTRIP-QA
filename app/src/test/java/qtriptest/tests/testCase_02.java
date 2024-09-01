package qtriptest.tests;

import com.relevantcodes.extentreports.LogStatus;
import extentReports.utils.ReportSingleton;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import qtriptest.ExternalDataProvider;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;

public class testCase_02 {

    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws Exception {
        // Initialize the driver and report
        ReportSingleton.initialize("chrome", "TestCase_02: Verify Search and Filter Functionality");
    }

    @Test(description = "Verify that search and filters work fine", 
          priority = 2, 
          groups = {"Search and Filter flow"}, 
          dataProvider = "qTripDataForTestCase02", 
          dataProviderClass = ExternalDataProvider.class, 
          enabled = true)
    public void TestCase02(String CityName, String Category_Filter, String Duration_Filter, String ExpectedFilteredResults, String ExpectedUnFilteredResults) {
        SoftAssert softAssert = new SoftAssert();

        try {
            // Log the start of the test
            ReportSingleton.logWithScreenshot(LogStatus.INFO, "Started test with city: " + CityName, "StartTest");

            // Navigate to Home Page
            HomePage homePage = new HomePage(ReportSingleton.getDriver());
            homePage.navigateToHomePage();
            ReportSingleton.logWithScreenshot(LogStatus.INFO, "Navigated to Home Page", "HomePage");

            // Verify Search City functionality
            boolean searchResult = homePage.verifySearchCity(CityName);
            softAssert.assertTrue(searchResult, "Search city functionality failed");
            ReportSingleton.logWithScreenshot(searchResult ? LogStatus.PASS : LogStatus.FAIL, "Search City functionality", "SearchCity");

            // Navigate to Adventure Page and verify filters
            AdventurePage adventurePage = new AdventurePage(ReportSingleton.getDriver());
            boolean filterResult = adventurePage.verifyFilter(Category_Filter, Duration_Filter, ExpectedFilteredResults, ExpectedUnFilteredResults);
            softAssert.assertTrue(filterResult, "Filter results are not correct");
            ReportSingleton.logWithScreenshot(filterResult ? LogStatus.PASS : LogStatus.FAIL, "Filter functionality", "Filters");

        } catch (Exception e) {
            ReportSingleton.logWithScreenshot(LogStatus.ERROR, "Test execution failed: " + e.getMessage(), "TestError");
        }

        // Assert all soft assertions
        softAssert.assertAll();
    }

    @AfterSuite(alwaysRun = true)
    public void closeBrowser() {
        // Finalize the report and close the browser
        ReportSingleton.finalizeReport();
    }
}
