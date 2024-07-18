package qtriptest.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterPage {
    RemoteWebDriver driver;
    public String lastGeneratedUsername;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    @FindBy(name = "email")
    WebElement username_txt_box;

    @FindBy(name = "password")
    WebElement password_txt_box;

    @FindBy(name = "confirmpassword")
    WebElement confirm_password_txt_box;

    @FindBy(xpath = "//button[text()='Register Now']")
    WebElement register_now_button;

    public RegisterPage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);
    }

    public boolean registerUser(String baseUsername, String password, boolean makeUsernameDynamic) {
        String test_data_username = baseUsername;

        if (makeUsernameDynamic) {
            // Generate a unique username using timestamp
            test_data_username = generateUniqueUsername(baseUsername);
        }

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(username_txt_box));

        System.out.println("Filling in registration details");
        username_txt_box.sendKeys(test_data_username);
        System.out.println("Username entered: " + test_data_username);
        password_txt_box.sendKeys(password);
        System.out.println("Password entered");
        confirm_password_txt_box.sendKeys(password);
        System.out.println("Confirm password entered");
        register_now_button.click();
        System.out.println("Clicked on Register Now button");

        this.lastGeneratedUsername = test_data_username;

        try {
            wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/login"));
            System.out.println("Navigated to correct URL");
            return true; // Registration successful
        } catch (TimeoutException e) {
            System.out.println("Timed out waiting for login page URL");
            return false; // Registration failed
        }
    }

    private String generateUniqueUsername(String baseUsername) {
        // Extract the username part from the email
        String username = baseUsername.split("@")[0];
        
        // Generate timestamp
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        
        // Combine username and timestamp to create a unique username
        return timestamp + "_" + username + "@gmail.com";
    }
}
