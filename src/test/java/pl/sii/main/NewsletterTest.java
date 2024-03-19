package pl.sii.main;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pl.sii.base.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class NewsletterTest extends BaseTest {

    @Test
    @Tag("newsletter")
    @DisplayName("Subscribe newsletter with invalid email address")
    public void userShouldNotBeAbleToSubscribeNewsletterWithInvalidEmail() {
        assertThat(
                application.open()
                        .subscribeNewsletterWithEmail(RandomString.make())
                        .isInvalidEmailMessageDisplayed())
                .isTrue()
                .withFailMessage("Alert message is not displayed");
    }

    @Test
    @Tag("newsletter")
    @DisplayName("Subscribe newsletter with correct email address")
    public void userShouldBeAbleToSubscribeNewsletterWithCorrectEmail() {
        assertThat(
                application.open()
                        .subscribeNewsletterWithEmail(faker.internet().emailAddress())
                        .isValidEmailMessageDisplayed())
                .isTrue()
                .withFailMessage("Alert message is not displayed");
    }
}
