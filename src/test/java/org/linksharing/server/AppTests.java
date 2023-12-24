package org.linksharing.server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.linksharing.server.pages.LoginPage;
import org.linksharing.server.pages.ProfileDetailsPage;
import org.linksharing.server.pages.RegistrationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppTests {

    private static final long DEFAULT_SLEEP_TIMEOUT = 500;

    private static SeleniumConfig sConfig;

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void beforeAll() {
        AppTests.sConfig = new SeleniumConfig();
    }

    @AfterAll
    public static void afterAll() {
        AppTests.sConfig.getDriver().close();
    }

    @Test
    void basicScenario() throws InterruptedException {
        WebDriver driver = sConfig.getDriver();

        driver.get(getUrl(port) + "/register");
        new RegistrationPage(driver)
                .withUsername("test")
                .withEmail("test@mail.com")
                .withPassword("123")
                .submit();

        // TODO: test is unstable unless...
        Thread.sleep(DEFAULT_SLEEP_TIMEOUT);

        driver.get(getUrl(port) + "/login");
        new LoginPage(driver)
                .withEmail("test@mail.com")
                .withPassword("123")
                .submit();

        Duration timeout = driver.manage().timeouts().getImplicitWaitTimeout();
        new WebDriverWait(driver, timeout).until(d -> d.findElement(By.tagName("nav")));

        driver.get(getUrl(port) + "/profile");
        assertThat(driver.getCurrentUrl()).isEqualTo(getUrl(port) + "/profile");

        ProfileDetailsPage profileDetailsPage = new ProfileDetailsPage(driver)
                .withFirstName("John")
                .withLastName("Doe")
                .withEmail("johndoe@mail.com");

        // without scrolling - error on save btn click
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(DEFAULT_SLEEP_TIMEOUT);

        profileDetailsPage.clickSave();

        Thread.sleep(DEFAULT_SLEEP_TIMEOUT);
        driver.navigate().refresh();

        profileDetailsPage.verifyFirstName("John");
        profileDetailsPage.verifyLastName("Doe");
        profileDetailsPage.verifyEmail("johndoe@mail.com");
    }

    private String getUrl(int port) {
        return "http://localhost:" + port;
    }
}
