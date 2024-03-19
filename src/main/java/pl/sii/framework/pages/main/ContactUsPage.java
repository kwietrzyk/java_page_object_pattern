package pl.sii.framework.pages.main;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import pl.sii.framework.base.component.Page;

import java.util.EnumSet;
import java.util.Random;

@Slf4j
public class ContactUsPage extends Page {
    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#id_contact")
    WebElement subjectHeading;

    @FindBy(css = "#email")
    WebElement emailInput;

    @FindBy(css = "#message")
    WebElement messageField;

    @FindBy(css = "#fileUpload")
    WebElement attachment;

    @FindBy(css = "#submitMessage")
    WebElement submitButton;

    @FindBy(xpath = "//*[contains(text(), 'Your message has been successfully sent')]")
    WebElement successAlert;

    @FindBy(xpath = "//*[contains(text(), 'Please select a subject')]")
    WebElement emptySubjectAlert;

    @FindBy(xpath = "//*[contains(text(), 'The message cannot be blank')]")
    WebElement emptyMessageAlert;

    @FindBy(xpath = "//*[contains(text(), 'Invalid email address')]")
    WebElement invalidEmailAlert;

    @Step("User sets subject heading")
    public ContactUsPage withSubject(Subject subject) {
        log.info("Set subject {}", subject);
        Select select = new Select(subjectHeading);
        select.selectByValue(subject.getValue());
        return this;
    }

    @Step("User sets email to input field")
    public ContactUsPage withEmail(String email) {
        log.info("Set email {}", email);
        emailInput.sendKeys(email);
        return this;
    }

    @Step("User fills message field")
    public ContactUsPage withMessage(String msg) {
        log.info("Fill message field");
        messageField.sendKeys(msg);
        return this;
    }

    @Step("User attaches file")
    public ContactUsPage withAttachment(String path) {
        log.info("Attach file from path");
        attachment.sendKeys(path);
        return this;
    }

    @Step("User submits sending message")
    public ContactUsPage submitWithoutSuccess() {
        log.info("Submit message");
        submitButton.click();
        return this;
    }

    @Step("User submits sending message")
    public ContactUsPage submit() {
        log.info("Submit message");
        submitButton.click();
        return this;
    }

    @Step("User checks if alert message is displayed")
    public boolean isSuccessMessageDisplayed() {
        log.info("Check if success message is displayed");
        return webDriverWait.until(webDriver -> successAlert.isDisplayed());
    }

    @Step("User checks if alert message is displayed")
    public boolean isEmptySubjectMessageAlertDisplayed() {
        log.info("Check if empty subject message is displayed");
        return webDriverWait.until(webDriver -> emptySubjectAlert.isDisplayed());
    }

    @Step("User checks if alert message is displayed")
    public boolean isEmptyMessageAlertDisplayed() {
        log.info("Check if empty message is displayed");
        return webDriverWait.until(webDriver -> emptyMessageAlert.isDisplayed());
    }

    @Step("User checks if alert message is displayed")
    public boolean isInvalidEmailAlertDisplayed() {
        log.info("Check if invalid email message is displayed");
        return webDriverWait.until(webDriver -> invalidEmailAlert.isDisplayed());
    }

    public enum Subject {
        CUSTOMER_SERVICE("1"),
        WEBMASTER("2");

        private final String value;

        private Subject(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }

        private static final EnumSet<ContactUsPage.Subject> VALUES = EnumSet.allOf(ContactUsPage.Subject.class);
        private static final Random RANDOM = new Random();

        public static ContactUsPage.Subject randomValue() {
            int index = RANDOM.nextInt(VALUES.size());
            return VALUES.toArray(new ContactUsPage.Subject[0])[index];
        }
    }

}
