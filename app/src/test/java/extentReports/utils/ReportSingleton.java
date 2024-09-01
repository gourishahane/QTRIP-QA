package extentReports.utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import qtriptest.DriverSingleton;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportSingleton {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static RemoteWebDriver driver;

    private static final String SCREENSHOT_DIR = "screenshots/"; // Directory for screenshots

    public static void initialize(String browser, String testName) throws Exception {
        driver = DriverSingleton.getDriver(browser);
        extent = new ExtentReports("extent-reports/TestReport.html", true);
        test = extent.startTest(testName);
        createScreenshotDirectory(); // Ensure the screenshot directory exists
    }

    private static void createScreenshotDirectory() {
        File directory = new File(SCREENSHOT_DIR);
        if (!directory.exists()) {
            directory.mkdirs(); // Creates the directory if it doesn't exist
        }
    }

    public static void logWithScreenshot(LogStatus status, String message, String screenshotName) {
        String screenshotPath = captureScreenshot(screenshotName);
        test.log(status, message + test.addScreenCapture(screenshotPath));
    }

    private static String captureScreenshot(String screenshotName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filePath = SCREENSHOT_DIR + screenshotName + "_" + timestamp + ".png";
        File destFile = new File(filePath);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destFile.getAbsolutePath();
    }

    public static RemoteWebDriver getDriver() {
        return driver;
    }

    public static void finalizeReport() {
        extent.endTest(test);
        extent.flush();
        DriverSingleton.quitDriver();
    }
}
