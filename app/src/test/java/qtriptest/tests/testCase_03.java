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

public class testCase_03 {

    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws Exception {
        // Initialize the driver and report
        ReportSingleton.initialize("chrome", "TestCase_03: Verify Adventure Booking and Cancellation Flow");
    }

    @Test(description = "Verify that Adventure Booking and Cancellation flow works fine", 
          priority = 3, 
          groups = {"Booking and Cancellation Flow"}, 
          dataProvider = "qTripDataForTestCase03", 
          dataProviderClass = ExternalDataProvider.class, 
          enabled = true)
    public void TestCase03(String NewUserName, String Password, String SearchCity, String AdventureName, String GuestName, String Date, String count) {
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

            // Cancel Reservations
            boolean cancelReservationStatus = adventureDetailsPage.cancelReservations();
            softAssert.assertTrue(cancelReservationStatus, "Perform reservations cancellation failed");
            ReportSingleton.logWithScreenshot(cancelReservationStatus ? LogStatus.PASS : LogStatus.FAIL, "Cancel reservations", "CancelReservations");

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
