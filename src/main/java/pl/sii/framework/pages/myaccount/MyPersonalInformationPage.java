package pl.sii.framework.pages.myaccount;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import pl.sii.framework.base.component.Page;
import pl.sii.framework.helpers.Title;

@Slf4j
public class MyPersonalInformationPage extends Page {
    public MyPersonalInformationPage(WebDriver driver) {
        super(driver);
    }

    // insert locators and methods here

    @FindBy(css = "#id_gender1")
    WebElement mrTitle;

    @FindBy(css = "#id_gender2")
    WebElement mrsTitle;
    @FindBy(css = "#firstname")
    WebElement firstNameInput;

    @FindBy(css = "#lastname")
    WebElement lastNameInput;

    @FindBy(css = "#email")
    WebElement emailInput;

    @FindBy(css = "#days")
    WebElement dayOfBirth;

    @FindBy(css = "#months")
    WebElement monthOfBirth;

    @FindBy(css = "#years")
    WebElement yearOfBirth;

    @FindBy(css = "#old_passwd")
    WebElement currentPasswordInput;

    @FindBy(css = "#passwd")
    WebElement newPasswordInput;

    @FindBy(css = "#newsletter")
    WebElement newsletter;

    @FindBy(css = "button[name=submitIdentity]")
    WebElement submitButton;

    // Mandatory:
    @FindBy(css = "#confirmation")
    WebElement newPasswordConfirmationInput;

    // Others
    @FindBy(css = ".alert-danger")
    WebElement alertErrorMessage;

    @FindBy(css = ".alert-success")
    WebElement alertSuccessMessage;

    @FindBy(xpath = "//*[@id=\"center_column\"]/ul/li[1]/a")
    WebElement backToYourAccount;

    // Default - automatically filled:

    @Step("User sets title")
    public MyPersonalInformationPage setTitle(Title title) {
        log.info("Set title {}", title);
        (title.equals(Title.MR) ? mrTitle : mrsTitle).click();
        return this;
    }

    @Step("User set first name to input field")
    public MyPersonalInformationPage withFirstName(String firstName) {
        log.info("Set first name {}", firstName);
        firstNameInput.sendKeys(firstName);
        return this;
    }

    @Step("User sets last name to input field")
    public MyPersonalInformationPage withLastName(String lastName) {
        log.info("Set last name {}", lastName);
        lastNameInput.sendKeys(lastName);
        return this;
    }

    @Step("User sets email to input field")
    public MyPersonalInformationPage withEmail(String email) {
        log.info("Set email {}", email);
        emailInput.sendKeys(email);
        return this;
    }

    @Step("User sets day of birth")
    public MyPersonalInformationPage withDayOfBirth(int day) {
        log.info("Set day of birth {}", dayOfBirth);
        Select select = new Select(dayOfBirth);
        select.selectByIndex(day-1);
        return this;
    }

    @Step("User sets month of birth")
    public MyPersonalInformationPage withMonthOfBirth(int month) {
        log.info("Set month of birth {}", monthOfBirth);
        Select select = new Select(monthOfBirth);
        select.selectByIndex(month-1);
        return this;
    }

    @Step("User sets year of birth")
    public MyPersonalInformationPage withYearOfBirth(int year) {
        log.info("Set year of birth {}", yearOfBirth);
        Select select = new Select(yearOfBirth);
        select.selectByValue(String.valueOf(year));
        return this;
    }


    @Step("User sets new password to input field")
    public MyPersonalInformationPage withNewPassword(String newPassword) {
        log.info("Insert new password {}", newPassword);
        newPasswordInput.sendKeys(newPassword);
        return this;
    }

    @Step("User confirms new password in input field")
    public MyPersonalInformationPage withNewPasswordConfirmation(String newPassword) {
        log.info("Confirm new password {}", newPassword);
        newPasswordConfirmationInput.sendKeys(newPassword);
        return this;
    }

    // Mandatory:
    @Step("User sets current password to input field")
    public MyPersonalInformationPage withCurrentPassword(String currentPassword) {
        log.info("Insert current password {}", currentPassword);
        currentPasswordInput.sendKeys(currentPassword);
        return this;
    }

    @Step("User checks if alert message is displayed")
    public boolean isAlertSuccessMessageDisplayed() {
        log.info("Check if alert message is displayed");
        return webDriverWait.until(webDriver -> alertSuccessMessage.isDisplayed());
    }

    @Step("User clicks on Submit button")
    public MyPersonalInformationPage submit() {
        log.info("Submit login form");
        submitButton.click();
        return this;
    }

    @Step("User checks if alert message is displayed")
    public boolean isAlertErrorMessageDisplayed() {
        log.info("Check if alert message is displayed");
        return webDriverWait.until(webDriver -> alertErrorMessage.isDisplayed());
    }

    @Step("User goes to 'My account'")
    public MyAccountPage goToMyAccountPage() {
        log.info("Goes to My Account Page");
        backToYourAccount.click();
        return pageFactory.create(MyAccountPage.class);
    }
}
