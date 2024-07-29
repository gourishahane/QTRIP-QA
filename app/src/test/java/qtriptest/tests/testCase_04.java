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

public class testCase_04 {

    static RemoteWebDriver driver;

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s | %s | %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws MalformedURLException {
        driver = DriverSingleton.getDriver("chrome");
    }

    @Test(description = "Verify that all bookings are displayed on history page", priority = 4, groups = {"Reliability Flow"},dataProvider = "qTripDataForTestCase04", dataProviderClass = ExternalDataProvider.class, enabled = true)
    public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2, String dataset3) {
        SoftAssert softAssert = new SoftAssert();

        logStatus("AdventureDetailPage test", "Verify that all bookings are displayed on history page", "Started");

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

            String[][] datasets = {dataset1.split(";"), dataset2.split(";"), dataset3.split(";")};

            for (String[] dataset : datasets) {
                String SearchCity = dataset[0];
                String AdventureName = dataset[1];
                String GuestName = dataset[2];
                String Date = dataset[3];
                String count = dataset[4];

                logStatus("HomePage test", "Verify search city functionality", "Started");
                boolean searchResult = homePage.verifySearchCity(SearchCity);
                softAssert.assertTrue(searchResult, "Search city functionality failed");

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

                logStatus("AdventureDetailsPage test", "Navigate Back to Home Page", "Started");
                status = adventureDetailsPage.navigateBackToHomePAge();
                softAssert.assertTrue(status, "Navigation back to home page failed");
                logStatus("AdventureDetailsPage test", "Navigate Back to Home Page", "Success");
            }

            logStatus("HomePage test", "Verify User logout", "Started");
            status = homePage.verifyLogout();
            softAssert.assertTrue(status, "Verify user logout failed");
            logStatus("HomePage test", "Verify User logout", "Success");

        } catch (Exception e) {
            logStatus("AdventureDetailsPage test", "Verify that all bookings are displayed on history page", "Failed");
            e.printStackTrace();
        }

        
        softAssert.assertAll();
    }

    @AfterSuite(alwaysRun = true)
    public void closeBrowser() {
        DriverSingleton.quitDriver();
    }
}
