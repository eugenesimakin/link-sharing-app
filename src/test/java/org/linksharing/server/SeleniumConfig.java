package org.linksharing.server;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class SeleniumConfig {
//    static {
//        System.setProperty("webdriver.gecko.driver", "geckodriver");
//    }

    private static final long WAIT_TIMEOUT_SEC = 10;
    private WebDriver driver;

    public SeleniumConfig() {
        var options = new FirefoxOptions();
        options.addArguments("-headless");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_TIMEOUT_SEC));
    }

    public WebDriver getDriver() {
        return driver;
    }
}
