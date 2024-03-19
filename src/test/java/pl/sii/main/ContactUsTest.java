package pl.sii.main;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pl.sii.base.BaseTest;
import pl.sii.framework.pages.main.ContactUsPage;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class ContactUsTest extends BaseTest {

    @AfterAll
    public static void removeTestFile() {
        if (file != null && file.exists()) {
            file.delete();
            System.out.println("File has been removed");
        } else {
            System.out.println("File does not exist");
        }
    }

    @Test
    @Tag("contact")
    @DisplayName("Fill ContactUs page with mandatory parameters")
    public void unsignedUserShouldBeAbleToSendContactUsRequest() {
        assertThat(
                application.open()
                        .contactUs()
                        .withSubject(ContactUsPage.Subject.CUSTOMER_SERVICE)
                        .withEmail(faker.internet().emailAddress())
                        .withMessage(RandomString.make())
                        .submit()
                        .isSuccessMessageDisplayed())
                .isTrue()
                .withFailMessage("Success alert is not displayed");
    }

    @Test
    @Tag("contact")
    @DisplayName("Fill ContactUs page with attachement")
    public void unsignedUserShouldBeAbleToSendContactUsRequestWithAttachement() {

        createTestFile();
        assertThat(
                application.open()
                        .contactUs()
                        .withSubject(ContactUsPage.Subject.CUSTOMER_SERVICE)
                        .withEmail(faker.internet().emailAddress())
                        .withMessage(RandomString.make())
                        .withAttachment(file.getAbsolutePath())
                        .submit()
                        .isSuccessMessageDisplayed())
                .isTrue()
                .withFailMessage("Success alert is not displayed");
    }

    @Test
    @Tag("contact")
    @DisplayName("Fill ContactUs page without mandatory parameter : email")
    public void userShouldNotBeAbleToSendContactUsRequestWithoutEmailAddress() {
        assertThat(
                application.open()
                        .contactUs()
                        .withSubject(ContactUsPage.Subject.CUSTOMER_SERVICE)
                        .withMessage(RandomString.make())
                        .submitWithoutSuccess()
                        .isInvalidEmailAlertDisplayed())
                .isTrue()
                .withFailMessage("Invalid email alert is not displayed");
    }

    @Test
    @Tag("contact")
    @DisplayName("Fill ContactUs page with invalid mandatory parameter : email")
    public void userShouldNotBeAbleToSendContactUsRequestWithInvalidEmailAddress() {
        assertThat(
                application.open()
                        .contactUs()
                        .withSubject(ContactUsPage.Subject.CUSTOMER_SERVICE)
                        .withEmail(RandomString.make())
                        .withMessage(RandomString.make())
                        .submitWithoutSuccess()
                        .isInvalidEmailAlertDisplayed())
                .isTrue()
                .withFailMessage("Invalid email alert is not displayed");
    }

    @Test
    @Tag("contact")
    @DisplayName("Fill ContactUs page without mandatory parameter : subject")
    public void userShouldNotBeAbleToSendContactUsRequestWithoutSubject() {
        assertThat(
                application.open()
                        .contactUs()
                        .withEmail(faker.internet().emailAddress())
                        .withMessage(RandomString.make())
                        .submitWithoutSuccess()
                        .isEmptySubjectMessageAlertDisplayed())
                .isTrue()
                .withFailMessage("Empty subject alert is not displayed");
    }

    @Test
    @Tag("contact")
    @DisplayName("Fill ContactUs page without message")
    public void userShouldNotBeAbleToSendContactUsRequestWithoutMessage() {
        assertThat(
                application.open()
                        .contactUs()
                        .withSubject(ContactUsPage.Subject.CUSTOMER_SERVICE)
                        .withEmail(faker.internet().emailAddress())
                        .submitWithoutSuccess()
                        .isEmptyMessageAlertDisplayed())
                .isTrue()
                .withFailMessage("Empty message alert is not displayed");
    }
}
