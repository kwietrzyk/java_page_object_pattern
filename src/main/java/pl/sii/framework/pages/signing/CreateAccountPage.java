package pl.sii.framework.pages.signing;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pl.sii.framework.base.component.Page;
import pl.sii.framework.pages.myaccount.MyAccountPage;
import pl.sii.framework.helpers.Title;

import java.util.EnumSet;
import java.util.Random;

@Slf4j
public class CreateAccountPage extends Page {
    public CreateAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#id_gender1")
    WebElement mrTitle;

    @FindBy(css = "#id_gender2")
    WebElement mrsTitle;
    @FindBy(css = "#customer_firstname")
    WebElement firstNameInput;

    @FindBy(css = "#customer_lastname")
    WebElement lastNameInput;

    @FindBy(css = "#email")
    WebElement emailInput;

    @FindBy(css = "#passwd")
    WebElement passwordInput;

    @FindBy(css = "#days")
    WebElement dayOfBirth;

    @FindBy(css = "#months")
    WebElement monthOfBirth;

    @FindBy(css = "#years")
    WebElement yearOfBirth;

    @FindBy(css = "#newsletter")
    WebElement newsletter;

    @FindBy(css = "#submitAccount")
    WebElement submitRegistration;

    @FindBy(css = ".alert-danger")
    WebElement missingFiledAlertMessage;


    @Step("User sets title")
    public CreateAccountPage setTitle(Title title) {
        log.info("Set title {}", title);
        (title.equals(Title.MR) ? mrTitle : mrsTitle).click();
        return this;
    }

    @Step("User set first name to input field")
    public CreateAccountPage withFirstName(String firstName) {
        log.info("Set first name {}", firstName);
        firstNameInput.sendKeys(firstName);
        return this;
    }

    @Step("User sets last name to input field")
    public CreateAccountPage withLastName(String lastName) {
        log.info("Set last name {}", lastName);
        lastNameInput.sendKeys(lastName);
        return this;
    }

    @Step("User sets email to input field")
    public CreateAccountPage withEmail(String email) {
        log.info("Set email {}", email);
        emailInput.sendKeys(email);
        return this;
    }

    @Step("User sets password to password input field")
    public CreateAccountPage withPassword(String password) {
        log.info("Set password {}", password);
        passwordInput.sendKeys(password);
        return this;
    }

    @Step("User sets day of birth")
    public CreateAccountPage withDayOfBirth(int day) {
        log.info("Set day of birth {}", dayOfBirth);
        Select select = new Select(dayOfBirth);
        select.selectByIndex(day-1);
        return this;
    }

    @Step("User sets month of birth")
    public CreateAccountPage withMonthOfBirth(int month) {
        log.info("Set month of birth {}", monthOfBirth);
        Select select = new Select(monthOfBirth);
        select.selectByIndex(month-1);
        return this;
    }

    @Step("User sets year of birth")
    public CreateAccountPage withYearOfBirth(int year) {
        log.info("Set year of birth {}", yearOfBirth);
        Select select = new Select(yearOfBirth);
        select.selectByValue(String.valueOf(year));
        return this;
    }

    @Step("User signs up for newsletter")
    public CreateAccountPage signUpForNewsletter() {
        log.info("Signing up for newsletter");
        newsletter.click();
        return this;
    }

    @Step("User clicks on Submit button")
    public MyAccountPage submit() {
        log.info("Submit sign up form");
        submitRegistration.click();
        return pageFactory.create(MyAccountPage.class);
    }

    @Step("User clicks on Submit button")
    public CreateAccountPage submitWithoutSuccess() {
        log.info("Submit login form");
        submitRegistration.click();
        return this;
    }

    @Step("User wait for page load")
    public CreateAccountPage waitForPageLoadWithProperPartUrl(String urlContains) {
        webDriverWait.until(ExpectedConditions.urlContains("account-creation"));
        return this;
    }

    @Step("User checks if missing field alert message is displayed")
    public boolean isMissingFieldAlertMessageDisplayed() {
        log.info("Check if missing field alert message is displayed");
        return webDriverWait.until(webDriver -> missingFiledAlertMessage.isDisplayed());
    }

}
