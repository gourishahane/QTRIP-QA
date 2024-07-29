package qtriptest.tests;

import qtriptest.DriverSingleton;
import qtriptest.ExternalDataProvider;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_03 {
    static RemoteWebDriver driver;

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s | %s | %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws MalformedURLException {
        driver = DriverSingleton.getDriver("chrome");
    }

    @Test(description = "Verify that search and filters work fine",priority = 3,groups = {"Booking and Cancellation Flow"},dataProvider = "qTripDataForTestCase03", dataProviderClass = ExternalDataProvider.class,enabled = true)
    public void TestCase03(String NewUserName, String Password, String SearchCity, String AdventureName, String GuestName, String Date, String count) {
        SoftAssert softAssert = new SoftAssert();

        logStatus("AdventureDetailPage test", "Verify Adventure Booking and Cancellation is working fine", "Started");

        try {
            logStatus("HomePage test", "Navigation to HomePage", "Started");
            HomePage homePage = new HomePage(driver);
            homePage.navigateToHomePage();
            logStatus("HomePage test", "Navigation to HomePage", "Success");


            logStatus("HomePage test", "Register Button Visibility", "Started");
            Boolean status = homePage.isRegisterButtonVisible();
            softAssert.assertTrue(status, "Register button not visible or not enabled");
            logStatus("HomePage test", "Register button is visible and enabled", "Success");


            logStatus("HomePage test", "Navigation to RegisterPage", "Started");
            homePage.navigateToRegisterPage();
            logStatus("HomePage test", "Navigation to RegisterPage", "Success");


            logStatus("RegisterPage test", "Registration of new user", "Started");
            RegisterPage register = new RegisterPage(driver);
            status = register.registerUser(NewUserName, Password, true);
            softAssert.assertTrue(status, "New user registration failed");
            logStatus("RegisterPage test", "Registration of new user", "Success");


            logStatus("LoginPage test", "Login of existing user", "Started");
            String username = register.lastGeneratedUsername;
            LoginPage login = new LoginPage(driver);
            status = login.loginExistingUser(username, Password);
            softAssert.assertTrue(status, "Login of existing user failed");
            logStatus("LoginPage test", "Login of existing user", "Success");


            logStatus("HomePage test", "Verify search city functionality", "Started");
            boolean searchResult = homePage.verifySearchCity(SearchCity);
            softAssert.assertTrue(searchResult, "Search city functionality failed");
            logStatus("HomePage test", "Verify search city functionality", "Success");


            logStatus("AdventurePage test", "Verify search adventures", "Started");
            AdventurePage adventurePage = new AdventurePage(driver);
            status = adventurePage.verifySearchAdventures(AdventureName);
            softAssert.assertTrue(status, "Search Adventures results are not correct");
            logStatus("AdventurePage test", "Verify search adventures", "Success");


            logStatus("AdventureDetailsPage test", "Verify Reservations", "Started");
            AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
            status = adventureDetailsPage.performReservations(GuestName, Date, count);
            softAssert.assertTrue(status, "Perform reservations failed");
            logStatus("AdventureDetailsPage test", "Verify Reservations", "Success");


            logStatus("AdventureDetailsPage test", "Verify Reservation Cancellations", "Started");
            status = adventureDetailsPage.cancelReservations();
            softAssert.assertTrue(status, "Perform reservations cancellation failed");
            logStatus("AdventureDetailsPage test", "Verify Reservation Cancellations", "Success");


            logStatus("HomePage test", "Verify User logout", "Started");
            status = homePage.verifyLogout();
            softAssert.assertTrue(status, "Verify user logout failed");
            logStatus("HomePage test", "Verify User logout", "Success");


        } catch (Exception e) {
            logStatus("AdventureDetailsPage test", "Verify Adventure Booking and Cancellation is working fine", "Failed");
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @AfterSuite(alwaysRun = true)
    public void closeBrowser() {
        DriverSingleton.quitDriver();
    }
}
