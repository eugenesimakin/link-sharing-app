package org.linksharing.server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.linksharing.server.pages.LoginPage;
import org.linksharing.server.pages.ProfileDetailsPage;
import org.linksharing.server.pages.RegistrationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppTests {

    static SeleniumConfig sConfig;
    @LocalServerPort
    int port;

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

        Thread.sleep(500); // TODO: test is unstable unless

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
        Thread.sleep(500);

        profileDetailsPage.clickSave();

        Thread.sleep(500);
        driver.navigate().refresh();

        profileDetailsPage.verifyFirstName("John");
        profileDetailsPage.withLastName("Doe");
        profileDetailsPage.verifyEmail("johndoe@mail.com");
    }

    private String getUrl(int port) {
        return "http://localhost:" + port;
    }
}