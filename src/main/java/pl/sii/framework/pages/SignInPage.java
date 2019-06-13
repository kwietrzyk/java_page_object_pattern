package pl.sii.framework.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.sii.framework.base.component.Page;

@Slf4j
public class SignInPage extends Page {

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input[name='email']")
    private WebElement emailInput;

    @FindBy(css = "input[name='password']")
    WebElement passwordInput;

    @FindBy(css = "button[id='submit-login']")
    WebElement submitButton;

    @FindBy(css = ".alert-danger")
    WebElement alertMessage;

    public SignInPage withEmail(String email) {
        log.info("Set email {}", email);
        emailInput.sendKeys(email);
        return this;
    }

    public SignInPage withPassword(String password) {
        log.info("Set password {}", password);
        passwordInput.sendKeys(password);
        return this;
    }

    public MainPage submit() {
        log.info("Submit login form");
        submitButton.click();
        return pageFactory.create(MainPage.class);
    }

    public SignInPage submitWithoutSuccess() {
        log.info("Submit login form");
        submitButton.click();
        return this;
    }

    public boolean isAlertMessageDisplayed() {
        log.info("Check if alert message displayed");
        return webDriverWait.until(webDriver -> alertMessage.isDisplayed())
                .booleanValue();
    }
}