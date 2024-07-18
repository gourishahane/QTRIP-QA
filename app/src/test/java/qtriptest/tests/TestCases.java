package qtriptest.tests;

import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCases {
    static RemoteWebDriver driver;

    public static void logStatus(String type, String message, String status) {
		System.out.println(String.format("%s |  %s  |  %s | %s",
				String.valueOf(java.time.LocalDateTime.now()), type, message, status));
	}

    @BeforeClass(description = "Creating RemoteWebDriver",enabled = false,alwaysRun = false)
    public static void createDriver() throws MalformedURLException{
        logStatus("driver","Initializing driver", "Started");
        final DesiredCapabilities capabilities=new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver=new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        logStatus("driver", "Initializing driver", "Success");
        driver.manage().window().maximize(); 
        logStatus("driver", "Maximize Window", "Success");
    }

    @Test(description = "Verify functionality of navigation to HomePage",enabled = false)
    public static void test_navigateToHomePage(){
        logStatus("HomePage test", "Navigation to HomePage", "Started");
        try{
        HomePage homePage=new HomePage(driver);
        homePage.navigateToHomePage();
        logStatus("HomePage test", "Navigation to HomePage", "Success");
    }catch(Exception e){
        logStatus("HomePage test", "Navigation to HomePage", "Failed");
        e.printStackTrace();
    }
    }
    @Test(description = "Verify if contents in navigation bar  visible", enabled = false)
    public static void test_openNavigationBar() {
        logStatus("HomePage test", "openNavigationBar functionality", "Started");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.navigateToHomePage(); 
           
            logStatus("HomePage test", "openNavigationBar functionality", "Success");
        } catch (Exception e) {
            logStatus("HomePage test", "openNavigationBar functionality", "Failed");
            e.printStackTrace();
        }
    }

    @Test(description = "Verify if Register button is visible", enabled = false)
public static void test_isRegisterButtonVisible() {
    logStatus("HomePage test", "Register button visibility test", "Started");
    try {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();
     
        Boolean status = homePage.isRegisterButtonVisible();
        if (!status) {
            throw new Exception("Register button not visible or not enabled");
        }
        logStatus("HomePage test", "Register button is visible and enabled", "Success");
    } catch (Exception e) {
        logStatus("HomePage test", "Register button visibility test", "Failed");
        e.printStackTrace();
    }
}
@Test(description = "Verify functionality of navigation to RegisterPage",enabled = false)
    public static void test_navigateToRegisterPage(){
        logStatus("RegisterPage test", "Navigation to RegisterPage", "Started");
        try{
        HomePage homePage=new HomePage(driver);
        homePage.navigateToHomePage();
        
        homePage.isRegisterButtonVisible();
        homePage.navigateToRegisterPage();
        logStatus("RegisterPage test", "Navigation to RegisterPage", "Success");
    }catch(Exception e){
        logStatus("RegisterPage test", "Navigation to RegisterPage", "Failed");
        e.printStackTrace();
    }
    }

    @Test(description = "Verify functionality of registration of new user",enabled = false)
    public static void test_registerUser(){
        logStatus("RegisterPage test", "Registration of new user", "Started");
        try{
        HomePage homePage=new HomePage(driver);
        homePage.navigateToHomePage();
        
        homePage.isRegisterButtonVisible();
        homePage.navigateToRegisterPage();
        RegisterPage register=new RegisterPage(driver);
        Boolean status=register.registerUser("username123", "password123", true);
        if(!status){
            throw new Exception("New user registration failed");
        }
        logStatus("RegisterPage test", "Registration of new user", "Success");
    }catch(Exception e){
        logStatus("RegisterPage test", "Registration of new user", "Failed");
        e.printStackTrace();
    }
    }

    @Test(description = "Verify functionality of login of existing user",enabled = false)
    public static void test_loginExistingUser(){
        logStatus("LoginPage test", "Login of existing user", "Started");
        try{
        HomePage homePage=new HomePage(driver);
        homePage.navigateToHomePage();
   
        homePage.isRegisterButtonVisible();
        homePage.navigateToRegisterPage();
        RegisterPage register=new RegisterPage(driver);
        
        Boolean status=register.registerUser("username123", "password123", true);
        if(!status){
            throw new Exception("New user registration failed");
        }
        logStatus("RegisterPage test", "Registration of new user", "Success");
        String username=register.lastGeneratedUsername;
        LoginPage login=new LoginPage(driver);
        status=login.loginExistingUser(username, "password123");
        if(!status){
            throw new Exception("Login of existing user failed");
        }
        logStatus("LoginPage test", "Login of existing user", "Success");
    }catch(Exception e){
        logStatus("LoginPage test", "Login of existing user", "Failed");
        e.printStackTrace();
    }
    }

    @Test(description = "Verify if user has logged out",enabled = false)
    public static void test_verifyUserLoggedOut(){
        logStatus("LoginPage test", "Verify user is logged out", "Started");
        try{
        HomePage homePage=new HomePage(driver);
        homePage.navigateToHomePage();
       
        homePage.isRegisterButtonVisible();
        homePage.navigateToRegisterPage();
        RegisterPage register=new RegisterPage(driver);
        register.registerUser("username123", "password123", true);
        logStatus("RegisterPage test", "Registration of new user", "Success");
        String username=register.lastGeneratedUsername;
        LoginPage login=new LoginPage(driver);
        
        boolean status=login.loginExistingUser(username, "password123");
        if(!status){
            throw new Exception("logout button not displayed");
        }
        logStatus("LoginPage test", "Login of existing user", "Success");
        status=homePage.verifyLogout();
        if(!status){
            throw new Exception("User logout failed");
        }
        logStatus("LoginPage test", "Verify user is logged out", "Success");
    }catch(Exception e){
        logStatus("LoginPage test", "Verify user is logged out", "Failed");
        e.printStackTrace();
    }
    }






    @AfterClass(enabled = false,alwaysRun = false)
    public static void quitDriver() throws MalformedURLException{
        driver.close();
        driver.quit();
        logStatus("driver", "Quitting Driver", "Success");
    }


}
