package org.linksharing.server.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver driver;

    @FindBy(name = "email")
    private WebElement inputEmail;

    @FindBy(name = "password")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage withDetails(WebElement field, String value) {
        field.sendKeys(value);
        return this;
    }

    public LoginPage withEmail(String email) {
        return withDetails(inputEmail, email);
    }

    public LoginPage withPassword(String password) {
        return withDetails(inputPassword, password);
    }

    public void submit() {
        submitButton.submit();
    }
}
