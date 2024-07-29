package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {
    private static RemoteWebDriver driver;

    private DriverSingleton() {}

    public static RemoteWebDriver getDriver(String browserType) throws MalformedURLException {
        if (driver == null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            switch (browserType.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName(BrowserType.CHROME);
                    break;
                case "firefox":
                    capabilities.setBrowserName(BrowserType.FIREFOX);
                    break;
                case "edge":
                    capabilities.setBrowserName(BrowserType.EDGE);
                    break;
                default:
                    throw new IllegalArgumentException("Browser " + browserType + " not supported.");
            }
            driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}