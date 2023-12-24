package org.linksharing.server;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class SeleniumConfig {
//    static {
//        System.setProperty("webdriver.gecko.driver", "geckodriver");
//    }

    private WebDriver driver;

    public SeleniumConfig() {
        var options = new FirefoxOptions();
        options.addArguments("-headless");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public WebDriver getDriver() {
        return driver;
    }
}
