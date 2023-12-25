package org.linksharing.server.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileLinksPage {
    private final WebDriver driver;

    @FindBy(xpath = "//button[@name='addLink']")
    private WebElement addEmptyLinkBtn;

    @FindBy(xpath = "//button[@name='saveLinks']")
    private WebElement saveLinksBtn;

    public ProfileLinksPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProfileLinksPage addLink(String title, String url) {
        addEmptyLinkBtn.click();

        List<WebElement> titleInputs = driver.findElements(By.name("linkTitle"));
        assertThat(titleInputs).isNotEmpty();

        List<WebElement> urlInputs = driver.findElements(By.name("linkUrl"));
        assertThat(urlInputs).isNotEmpty();

        titleInputs.get(titleInputs.size() - 1).sendKeys(title);
        urlInputs.get(urlInputs.size() - 1).sendKeys(url);

        return this;
    }

    public void saveLinks() {
        saveLinksBtn.click();
    }

    public void verifyLinks() {
        List<WebElement> titleInputs = driver.findElements(By.name("linkTitle"));
        assertThat(titleInputs).isNotEmpty();
        for (WebElement titleInput : titleInputs) {
            assertThat(titleInput.getAttribute("value")).isNotBlank();
        }

        List<WebElement> urlInputs = driver.findElements(By.name("linkUrl"));
        assertThat(urlInputs).isNotEmpty();
        for (WebElement urlInput : urlInputs) {
            assertThat(urlInput.getAttribute("value")).isNotBlank();
        }
    }
}
