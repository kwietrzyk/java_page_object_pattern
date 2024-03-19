package pl.sii.framework.pages.signing;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.sii.framework.base.component.Page;

@Slf4j
public class ForgottenPasswordPage extends Page {
    public ForgottenPasswordPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#email")
    WebElement emailInput;

    @FindBy(css = ".submit > button[type=submit]")
    WebElement submitButton;

    @FindBy(css = ".alert-success")
    WebElement alertSuccessMessage;

    @Step("User sets email address to email input field")
    public ForgottenPasswordPage withEmail(String email) {
        log.info("Set email {}", email);
        emailInput.sendKeys(email);
        return this;
    }

    @Step("User submits email address")
    public ForgottenPasswordPage submit() {
        log.info("Submit action");
        submitButton.click();
        return this;
    }

    @Step("User checks if alert message is displayed")
    public boolean isAlertSuccessMessageDisplayed() {
        log.info("Check if alert message is displayed");
        return webDriverWait.until(webDriver -> alertSuccessMessage.isDisplayed());
    }
}
