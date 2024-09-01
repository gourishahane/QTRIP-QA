package extentReports.utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import qtriptest.DriverSingleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ReportSingleton {

    private static ExtentReports report;
    private static ExtentTest test;
    private static RemoteWebDriver driver;

    private ReportSingleton() {}

    // Initialize WebDriver, ExtentReports, and ExtentTest in one method
    public static void initialize(String browserType, String testName) throws IOException {
       
       if (report == null) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            report = new ExtentReports(System.getProperty("user.dir") + "/Report_" + timestamp + ".html");
            report.loadConfig(new File(System.getProperty("user.dir") + "/extent_customization_configs.xml"));
        }
        driver = DriverSingleton.getDriver(browserType);
        if (driver == null) {
            throw new NullPointerException("Driver initialization failed!");
        }
        test = report.startTest(testName);
      
    }

    // Capture a screenshot and log details in one method
    public static void logWithScreenshot(LogStatus status, String details, String screenshotName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String destPath = System.getProperty("user.dir") + "/screenshots" + screenshotName + "_" + System.currentTimeMillis() + ".png";
            Files.copy(srcFile.toPath(), new File(destPath).toPath());
            test.log(status, details, test.addScreenCapture(destPath));
        } catch (IOException e) {
            test.log(LogStatus.ERROR, "Failed to capture screenshot: " + e.getMessage());
        }
    }

    // Finalize the report and close WebDriver
    public static void finalizeReport() {
        if (report != null) {
            report.endTest(test);
            report.flush();
        }
        DriverSingleton.quitDriver();
    }

    public static RemoteWebDriver getDriver() {
        return driver;
    }
}
