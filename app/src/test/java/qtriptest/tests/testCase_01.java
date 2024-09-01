package qtriptest.tests;

import extentReports.utils.ReportSingleton;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.ExternalDataProvider;

public class testCase_01 {

    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws Exception {
        ReportSingleton.initialize("chrome", "TestCase_01: Verify functionality of Register, Login, and Logout");
    }

    @Test(description = "Verify functionality of - Register new user, login user, and logout user", priority = 1, groups = {"Login Flow"}, dataProvider = "qTripDataForTestCase01", dataProviderClass = ExternalDataProvider.class, enabled = true)
    public void TestCase01(String username, String password) {
        SoftAssert softAssert = new SoftAssert();

        try {
            ReportSingleton.logWithScreenshot(LogStatus.INFO, "Started test with username: " + username + " and password: " + password, "StartTest");

            HomePage homePage = new HomePage(ReportSingleton.getDriver());
            homePage.navigateToHomePage();
            ReportSingleton.logWithScreenshot(LogStatus.INFO, "Navigated to Home Page", "HomePage");

            boolean isRegisterButtonVisible = homePage.isRegisterButtonVisible();
            softAssert.assertTrue(isRegisterButtonVisible, "Register button should be visible");
            ReportSingleton.logWithScreenshot(isRegisterButtonVisible ? LogStatus.PASS : LogStatus.FAIL, "Register button visibility", "RegisterButton");

            homePage.navigateToRegisterPage();
            ReportSingleton.logWithScreenshot(LogStatus.INFO, "Navigated to Register Page", "RegisterPage");

            RegisterPage register = new RegisterPage(ReportSingleton.getDriver());
            boolean registrationStatus = register.registerUser(username, password, true);
            ReportSingleton.logWithScreenshot(registrationStatus ? LogStatus.PASS : LogStatus.FAIL, "Registration of new user", "RegisterUser");

            String generatedUsername = register.lastGeneratedUsername;
            softAssert.assertNotNull(generatedUsername, "Username should be generated during registration");

            LoginPage login = new LoginPage(ReportSingleton.getDriver());
            boolean loginStatus = login.loginExistingUser(generatedUsername, password);
            ReportSingleton.logWithScreenshot(loginStatus ? LogStatus.PASS : LogStatus.FAIL, "Login of existing user", "LoginUser");
            softAssert.assertTrue(loginStatus, "Login should be successful");

            boolean logoutStatus = homePage.verifyLogout();
            softAssert.assertTrue(logoutStatus, "User should be logged out");
            ReportSingleton.logWithScreenshot(logoutStatus ? LogStatus.PASS : LogStatus.FAIL, "Verify user is logged out", "LogoutUser");

        } catch (Exception e) {
            ReportSingleton.logWithScreenshot(LogStatus.ERROR, "Test execution failed: " + e.getMessage(), "TestError");
        }

        softAssert.assertAll();
    }

    @AfterSuite(alwaysRun = true)
    public void closeDriver() {
        ReportSingleton.finalizeReport();
    }
}
