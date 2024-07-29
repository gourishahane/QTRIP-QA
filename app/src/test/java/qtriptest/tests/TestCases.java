// package qtriptest.tests;

// import qtriptest.pages.AdventureDetailsPage;
// import qtriptest.pages.AdventurePage;
// import qtriptest.pages.HomePage;
// import qtriptest.pages.LoginPage;
// import qtriptest.pages.RegisterPage;
// import java.net.MalformedURLException;
// import java.net.URL;
// import org.openqa.selenium.remote.BrowserType;
// import org.openqa.selenium.remote.DesiredCapabilities;
// import org.openqa.selenium.remote.RemoteWebDriver;
// import org.testng.annotations.AfterClass;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;

// public class TestCases {
    // static RemoteWebDriver driver;

    // public static void logStatus(String type, String message, String status) {
	// 	System.out.println(String.format("%s |  %s  |  %s | %s",
	// 			String.valueOf(java.time.LocalDateTime.now()), type, message, status));
	// }

    // @BeforeClass(description = "Creating RemoteWebDriver",enabled = true,alwaysRun = true)
    // public static void createDriver() throws MalformedURLException{
    //     logStatus("driver","Initializing driver", "Started");
    //     final DesiredCapabilities capabilities=new DesiredCapabilities();
    //     capabilities.setBrowserName(BrowserType.CHROME);
    //     driver=new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
    //     logStatus("driver", "Initializing driver", "Success");
    //     driver.manage().window().maximize(); 
    //     logStatus("driver", "Maximize Window", "Success");
    // }

//     @Test(description = "Verify functionality of navigation to HomePage",enabled = false)
//     public static void test_navigateToHomePage(){
//         logStatus("HomePage test", "Navigation to HomePage", "Started");
//         try{
//         HomePage homePage=new HomePage(driver);
//         homePage.navigateToHomePage();
//         logStatus("HomePage test", "Navigation to HomePage", "Success");
//     }catch(Exception e){
//         logStatus("HomePage test", "Navigation to HomePage", "Failed");
//         e.printStackTrace();
//     }
//     }
//     @Test(description = "Verify if contents in navigation bar  visible", enabled = false)
//     public static void test_openNavigationBar() {
//         logStatus("HomePage test", "openNavigationBar functionality", "Started");
//         try {
//             HomePage homePage = new HomePage(driver);
//             homePage.navigateToHomePage(); 
           
//             logStatus("HomePage test", "openNavigationBar functionality", "Success");
//         } catch (Exception e) {
//             logStatus("HomePage test", "openNavigationBar functionality", "Failed");
//             e.printStackTrace();
//         }
//     }

//     @Test(description = "Verify if Register button is visible", enabled = false)
// public static void test_isRegisterButtonVisible() {
//     logStatus("HomePage test", "Register button visibility test", "Started");
//     try {
//         HomePage homePage = new HomePage(driver);
//         homePage.navigateToHomePage();
     
//         Boolean status = homePage.isRegisterButtonVisible();
//         if (!status) {
//             throw new Exception("Register button not visible or not enabled");
//         }
//         logStatus("HomePage test", "Register button is visible and enabled", "Success");
//     } catch (Exception e) {
//         logStatus("HomePage test", "Register button visibility test", "Failed");
//         e.printStackTrace();
//     }
// }
// @Test(description = "Verify functionality of navigation to RegisterPage",enabled = false)
//     public static void test_navigateToRegisterPage(){
//         logStatus("RegisterPage test", "Navigation to RegisterPage", "Started");
//         try{
//         HomePage homePage=new HomePage(driver);
//         homePage.navigateToHomePage();
        
//         homePage.isRegisterButtonVisible();
//         homePage.navigateToRegisterPage();
//         logStatus("RegisterPage test", "Navigation to RegisterPage", "Success");
//     }catch(Exception e){
//         logStatus("RegisterPage test", "Navigation to RegisterPage", "Failed");
//         e.printStackTrace();
//     }
//     }

//     @Test(description = "Verify functionality of registration of new user",enabled = false)
//     public static void test_registerUser(){
//         logStatus("RegisterPage test", "Registration of new user", "Started");
//         try{
//         HomePage homePage=new HomePage(driver);
//         homePage.navigateToHomePage();
        
//         homePage.isRegisterButtonVisible();
//         homePage.navigateToRegisterPage();
//         RegisterPage register=new RegisterPage(driver);
//         Boolean status=register.registerUser("username123", "password123", true);
//         if(!status){
//             throw new Exception("New user registration failed");
//         }
//         logStatus("RegisterPage test", "Registration of new user", "Success");
//     }catch(Exception e){
//         logStatus("RegisterPage test", "Registration of new user", "Failed");
//         e.printStackTrace();
//     }
//     }

//     @Test(description = "Verify functionality of login of existing user",enabled = false)
//     public static void test_loginExistingUser(){
//         logStatus("LoginPage test", "Login of existing user", "Started");
//         try{
//         HomePage homePage=new HomePage(driver);
//         homePage.navigateToHomePage();
   
//         homePage.isRegisterButtonVisible();
//         homePage.navigateToRegisterPage();
//         RegisterPage register=new RegisterPage(driver);
        
//         Boolean status=register.registerUser("username123", "password123", true);
//         if(!status){
//             throw new Exception("New user registration failed");
//         }
//         logStatus("RegisterPage test", "Registration of new user", "Success");
//         String username=register.lastGeneratedUsername;
//         LoginPage login=new LoginPage(driver);
//         status=login.loginExistingUser(username, "password123");
//         if(!status){
//             throw new Exception("Login of existing user failed");
//         }
//         logStatus("LoginPage test", "Login of existing user", "Success");
//     }catch(Exception e){
//         logStatus("LoginPage test", "Login of existing user", "Failed");
//         e.printStackTrace();
//     }
//     }

//     @Test(description = "Verify if user has logged out",enabled = false)
//     public static void test_verifyUserLoggedOut(){
//         logStatus("LoginPage test", "Verify user is logged out", "Started");
//         try{
//         HomePage homePage=new HomePage(driver);
//         homePage.navigateToHomePage();
       
//         homePage.isRegisterButtonVisible();
//         homePage.navigateToRegisterPage();
//         RegisterPage register=new RegisterPage(driver);
//         register.registerUser("username123", "password123", true);
//         logStatus("RegisterPage test", "Registration of new user", "Success");
//         String username=register.lastGeneratedUsername;
//         LoginPage login=new LoginPage(driver);
        
//         boolean status=login.loginExistingUser(username, "password123");
//         if(!status){
//             throw new Exception("logout button not displayed");
//         }
//         logStatus("LoginPage test", "Login of existing user", "Success");
//         status=homePage.verifyLogout();
//         if(!status){
//             throw new Exception("User logout failed");
//         }
//         logStatus("LoginPage test", "Verify user is logged out", "Success");
//     }catch(Exception e){
//         logStatus("LoginPage test", "Verify user is logged out", "Failed");
//         e.printStackTrace();
//     }
//     }

//     @Test(description = "Verify functionality of filters",enabled = false)
//     public static void test_verifyFilters(){
//         logStatus("HomePage test", "Navigation to HomePage", "Started");
//         try{
//         HomePage homePage=new HomePage(driver);
//         homePage.navigateToHomePage();
//         logStatus("HomePage test", "Navigation to HomePage", "Success");
        
//         logStatus("HomePage test", "Verify search city functionality", "Started");
//         boolean searchResult = homePage.verifySearchCity("Bengaluru");
//         if (!searchResult) {
//             throw new Exception("Search city functionality failed");
//         }
//         logStatus("HomePage test", "Verify search city functionality", "Success");
//         logStatus("AdventurePage test", "Verify Filter functionality", "Started");
//         AdventurePage adventurePage=new AdventurePage(driver);
//         boolean filterResult=adventurePage.verifyFilter("Cycling Routes","6-12 Hours","1","9");
//         if(!filterResult){
//             throw new Exception("Filter results are not correct");
//         }
//         logStatus("AdventurePage test", "Verify Filter functionality", "Succeed");
    
//     }catch(Exception e){
//         logStatus("AdventurePage test", "Verify Filter functionality", "Failed");
    
//         e.printStackTrace();
//     }
//     }


// @Test(description = "Verify all bookings are displayed correctly",enabled = true)
// public static void test_verifyAllBookingDisplayed(){

//     logStatus("AdventureDetailPage test", "Verify all bookings are displayed correctly", "Started");

//     try{

//     logStatus("HomePage test", "Navigation to HomePage", "Started");
//     HomePage homePage=new HomePage(driver);
//     homePage.navigateToHomePage();
//     logStatus("HomePage test", "Navigation to HomePage", "Success");


//     logStatus("HomePage test", "Register Button Visibility", "Started");
//     Boolean status = homePage.isRegisterButtonVisible();
//             if (!status) {
//                 throw new Exception("Register button not visible or not enabled");
//             }
//     logStatus("HomePage test", "Register button is visible and enabled", "Success");


//     logStatus("HomePage test", "Navigation to RegisterPage", "Started");
//     homePage.navigateToRegisterPage();
//     logStatus("HomePage test", "Navigation to RegisterPage", "Success");


//     logStatus("RegisterPage test", "Registration of new user", "Started");
//     RegisterPage register=new RegisterPage(driver);
//     status=register.registerUser("username123", "password123", true);
//             if(!status){
//                throw new Exception("New user registration failed");
//              }
//     logStatus("RegisterPage test", "Registration of new user", "Success");


//     logStatus("LoginPage test", "Login of existing user", "Started");
//     String username=register.lastGeneratedUsername;
//     LoginPage login=new LoginPage(driver);
//     status=login.loginExistingUser(username, "password123");
//     if(!status){
//         throw new Exception("Login of existing user failed");
//     }
//     logStatus("LoginPage test", "Login of existing user", "Success");


    
//     logStatus("HomePage test", "Verify search city functionality", "Started");
//     boolean searchResult = homePage.verifySearchCity("Bengaluru");
//     if (!searchResult) {
//         throw new Exception("Search city functionality failed");
//     }
    
    

//     logStatus("AdventurePage test", "Verify search adventures", "Started");
//     AdventurePage adventurePage=new AdventurePage(driver);
//     status=adventurePage.verifySearchAdventures("Niaboytown");
//     if(!status){
//         throw new Exception("Search Adventures results are not correct");
//     }
//     logStatus("AdventurePage test", "Verify search adventures", "Succeed");


//     logStatus("AdventureDetailsPage test", "Verify Reservations", "Started");
//     AdventureDetailsPage adventureDetailsPage=new AdventureDetailsPage(driver);
//     status=adventureDetailsPage.performReservations("palam","16-11-2025","2");
//     if(!status){
//         throw new Exception("Perform reservations failed");
//     }
//     logStatus("AdventureDetailsPage test", "Verify Reservations", "Succeed");

// }catch(Exception e){
//     logStatus("AdventureDetailsPage test", "Verify all bookings are displayed correctly.", "Failed");
//     e.printStackTrace();
// }
// }   

    








//     @AfterClass(enabled = true,alwaysRun = true)
//     public static void quitDriver() throws MalformedURLException{
//         driver.close();
//         driver.quit();
//         logStatus("driver", "Quitting Driver", "Success");
//     }


// }
