package qtriptest.tests;

import com.relevantcodes.extentreports.LogStatus;
import extentReports.utils.ReportSingleton;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import qtriptest.ExternalDataProvider;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;

public class testCase_04 {

    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws Exception {
        // Initialize the driver and report
        ReportSingleton.initialize("chrome", "TestCase_04: Verify Adventure Bookings on History Page");
    }

    @Test(description = "Verify that all bookings are displayed on history page", 
          priority = 4, 
          groups = {"Reliability Flow"}, 
          dataProvider = "qTripDataForTestCase04", 
          dataProviderClass = ExternalDataProvider.class, 
          enabled = true)
    public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2, String dataset3) {
        SoftAssert softAssert = new SoftAssert();

        try {
            // Log the start of the test
            ReportSingleton.logWithScreenshot(LogStatus.INFO, "Started test with new user: " + NewUserName, "StartTest");

            // Navigate to Home Page
            HomePage homePage = new HomePage(ReportSingleton.getDriver());
            homePage.navigateToHomePage();
            ReportSingleton.logWithScreenshot(LogStatus.INFO, "Navigated to Home Page", "HomePage");

            // Verify Register Button Visibility
            boolean isRegisterButtonVisible = homePage.isRegisterButtonVisible();
            softAssert.assertTrue(isRegisterButtonVisible, "Register button not visible or not enabled");
            ReportSingleton.logWithScreenshot(isRegisterButtonVisible ? LogStatus.PASS : LogStatus.FAIL, "Register button visibility", "RegisterButton");

            // Navigate to Register Page
            homePage.navigateToRegisterPage();
            ReportSingleton.logWithScreenshot(LogStatus.INFO, "Navigated to Register Page", "RegisterPage");

            // Register New User
            RegisterPage register = new RegisterPage(ReportSingleton.getDriver());
            boolean registrationStatus = register.registerUser(NewUserName, Password, true);
            softAssert.assertTrue(registrationStatus, "New user registration failed");
            ReportSingleton.logWithScreenshot(registrationStatus ? LogStatus.PASS : LogStatus.FAIL, "New user registration", "RegisterUser");

            // Login Existing User
            String generatedUsername = register.lastGeneratedUsername;
            LoginPage login = new LoginPage(ReportSingleton.getDriver());
            boolean loginStatus = login.loginExistingUser(generatedUsername, Password);
            softAssert.assertTrue(loginStatus, "Login of existing user failed");
            ReportSingleton.logWithScreenshot(loginStatus ? LogStatus.PASS : LogStatus.FAIL, "Login existing user", "LoginUser");

            // Handling Multiple Datasets
            String[][] datasets = {dataset1.split(";"), dataset2.split(";"), dataset3.split(";")};

            for (String[] dataset : datasets) {
                String SearchCity = dataset[0];
                String AdventureName = dataset[1];
                String GuestName = dataset[2];
                String Date = dataset[3];
                String count = dataset[4];

                // Search for City
                boolean searchCityResult = homePage.verifySearchCity(SearchCity);
                softAssert.assertTrue(searchCityResult, "Search city functionality failed");
                ReportSingleton.logWithScreenshot(searchCityResult ? LogStatus.PASS : LogStatus.FAIL, "Search City functionality", "SearchCity");

                // Search for Adventures
                AdventurePage adventurePage = new AdventurePage(ReportSingleton.getDriver());
                boolean searchAdventureResult = adventurePage.verifySearchAdventures(AdventureName);
                softAssert.assertTrue(searchAdventureResult, "Search adventures results are not correct");
                ReportSingleton.logWithScreenshot(searchAdventureResult ? LogStatus.PASS : LogStatus.FAIL, "Search adventures functionality", "SearchAdventure");

                // Perform Reservations
                AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(ReportSingleton.getDriver());
                boolean reservationStatus = adventureDetailsPage.performReservations(GuestName, Date, count);
                softAssert.assertTrue(reservationStatus, "Perform reservations failed");
                ReportSingleton.logWithScreenshot(reservationStatus ? LogStatus.PASS : LogStatus.FAIL, "Perform reservations", "Reservations");

                // Navigate Back to Home Page
                boolean navigateBackStatus = adventureDetailsPage.navigateBackToHomePAge();
                softAssert.assertTrue(navigateBackStatus, "Navigation back to home page failed");
                ReportSingleton.logWithScreenshot(navigateBackStatus ? LogStatus.PASS : LogStatus.FAIL, "Navigate Back to Home Page", "NavigateHome");
            }

            // Verify User Logout
            boolean logoutStatus = homePage.verifyLogout();
            softAssert.assertTrue(logoutStatus, "Verify user logout failed");
            ReportSingleton.logWithScreenshot(logoutStatus ? LogStatus.PASS : LogStatus.FAIL, "Verify user logout", "LogoutUser");

        } catch (Exception e) {
            ReportSingleton.logWithScreenshot(LogStatus.ERROR, "Test execution failed: " + e.getMessage(), "TestError");
            e.printStackTrace();
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
