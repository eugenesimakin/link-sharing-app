package org.linksharing.server.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage {

    private final WebDriver driver;

    @FindBy(name = "username")
    private WebElement inputUsername;

    @FindBy(name = "email")
    private WebElement inputEmail;

    @FindBy(name = "password")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public RegistrationPage withDetails(WebElement field, String value) {
        field.sendKeys(value);
        return this;
    }

    public RegistrationPage withUsername(String username) {
        return withDetails(inputUsername, username);
    }

    public RegistrationPage withEmail(String email) {
        return withDetails(inputEmail, email);
    }

    public RegistrationPage withPassword(String password) {
        return withDetails(inputPassword, password);
    }

    public void submit() {
        submitButton.submit();
    }

}
