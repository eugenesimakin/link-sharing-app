package org.linksharing.server.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileDetailsPage {

    private final WebDriver driver;

    @FindBy(xpath = "//input[@name='firstname']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='lastname']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    private WebElement saveButton;

    public ProfileDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProfileDetailsPage withFirstName(String firstName) {
        firstNameInput.sendKeys(firstName);
        return this;
    }

    public ProfileDetailsPage withLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
        return this;
    }

    public ProfileDetailsPage withEmail(String email) {
        emailInput.sendKeys(email);
        return this;
    }

    public void clickSave() {
        saveButton.click();
    }

    public void verifyFirstName(String firstName) {
        assertThat(firstNameInput.getAttribute("value")).isEqualTo(firstName);
    }

    public void verifyLastName(String lastName) {
        assertThat(lastNameInput.getAttribute("value")).isEqualTo(lastName);
    }

    public void verifyEmail(String email) {
        assertThat(emailInput.getAttribute("value")).isEqualTo(email);
    }
}