package qtriptest.tests;

import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.ExternalDataProvider;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_01 {

    static RemoteWebDriver driver;

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s | %s | %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        logStatus("driver", "Initializing driver", "Started");
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        logStatus("driver", "Initializing driver", "Success");
        driver.manage().window().maximize(); 
        logStatus("driver", "Maximize Window", "Success");
    }

    @Test(description = "Verify functionality of - Register new user, login user, and logout user", dataProvider = "qTripData", dataProviderClass = ExternalDataProvider.class, enabled = true)
    public void TestCase01(String username, String password) {
        SoftAssert softAssert = new SoftAssert();

        try {
            logStatus("TestCase_01", "Testing with username: " + username + " and password: " + password, "Started");

            HomePage homePage = new HomePage(driver);
            homePage.navigateToHomePage();
            logStatus("HomePage test", "Navigating to Home Page", "Success");

            // homePage.openNavigationBar();
            softAssert.assertTrue(homePage.isRegisterButtonVisible(), "Register button should be visible");
            logStatus("HomePage test", "Register button visibility", "Success");

            homePage.navigateToRegisterPage();
            logStatus("HomePage test", "Navigating to Register Page", "Success");

            RegisterPage register = new RegisterPage(driver);
            boolean registrationStatus = register.registerUser(username, password,true);
            logStatus("RegisterPage test", "Registration of new user", registrationStatus ? "Success" : "Failed");

            String generatedUsername = register.lastGeneratedUsername;
            softAssert.assertNotNull(generatedUsername, "Username should be generated during registration");

            LoginPage login = new LoginPage(driver);
            boolean loginStatus = login.loginExistingUser(generatedUsername, password);
            logStatus("LoginPage test", "Login of existing user", loginStatus ? "Success" : "Failed");
            softAssert.assertTrue(loginStatus, "Login should be successful");

            boolean logoutStatus = homePage.verifyLogout();
            softAssert.assertTrue(logoutStatus, "User should be logged out");
            logStatus("LoginPage test", "Verify user is logged out", logoutStatus ? "Success" : "Failed");

        } catch (Exception e) {
            logStatus("TestCase_01", "Test execution failed", "Failed");
            e.printStackTrace();
        }

        softAssert.assertAll();
    }

    @AfterSuite(alwaysRun = true)
    public static void quitDriver() {
        if (driver != null) {
            driver.close();
            driver.quit();
            logStatus("driver", "Quitting driver", "Success");
        }
    }
}
