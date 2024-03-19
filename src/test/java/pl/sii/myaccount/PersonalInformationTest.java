package pl.sii.myaccount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sii.annotations.DisableConcurrentExecution;
import pl.sii.annotations.DisableConcurrentExecutionExtension;
import pl.sii.base.BaseTest;
import pl.sii.framework.pages.myaccount.MyAccountPage;
import pl.sii.framework.pages.myaccount.MyPersonalInformationPage;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DisableConcurrentExecutionExtension.class)
public class PersonalInformationTest extends BaseTest {

    private static final String newPassword = "new21Password!";

    @Test
    @Tag("personalInfo")
    @DisableConcurrentExecution
    @DisplayName("Change data with incorrect password")
    public void shouldNotUpdateData() {
        assertThat(signInSuccessfully().updateMyPersonalInfo()
                .withCurrentPassword("incorrectOne")
                .submit()
                .isAlertErrorMessageDisplayed())
                .isTrue().withFailMessage("Error message is not displayed");
    }

    @Test
    @Tag("personalInfo")
    @DisableConcurrentExecution
    @DisplayName("Change data with correct password")
    public void shouldUpdateData() {
        assertThat(signInSuccessfully().updateMyPersonalInfo()
                .withCurrentPassword(configPasswordInput)
                .submit()
                .isAlertSuccessMessageDisplayed())
                .isTrue().withFailMessage("Success message is not displayed");
    }

    @Test
    @Tag("personalInfo")
    @DisableConcurrentExecution
    @DisplayName("Change password to new one without confirmation")
    public void shouldNotChangePasswordWithoutConfirmation() {
        assertThat(signInSuccessfully().updateMyPersonalInfo()
                .withCurrentPassword(configPasswordInput)
                .withNewPassword(newPassword)
                .submit()
                .isAlertErrorMessageDisplayed())
                .isTrue().withFailMessage("Error message is not displayed");
    }

    // Do not run below test in parallel, because it might affect other tests when password in being changed
    @Test
    @Tag("personalInfo")
    @DisableConcurrentExecution
    @DisplayName("Change password to new one")
    public void shouldChangePassword() {
        MyPersonalInformationPage personalInfoPage = signInSuccessfully().updateMyPersonalInfo();
        assertThat(personalInfoPage
                .withCurrentPassword(configPasswordInput)
                .withNewPassword(newPassword)
                .withNewPasswordConfirmation(newPassword)
                .submit()
                .isAlertSuccessMessageDisplayed())
                .isTrue().withFailMessage("Success message is not displayed");

        changePasswordBackToDefault(personalInfoPage);
    }

    private void changePasswordBackToDefault(MyPersonalInformationPage personalInfoPage) {
        MyAccountPage myAccountPage = personalInfoPage.goToMyAccountPage();
        assertThat(myAccountPage.updateMyPersonalInfo()
                .withCurrentPassword(newPassword)
                .withNewPassword(configPasswordInput)
                .withNewPasswordConfirmation(configPasswordInput)
                .submit()
                .isAlertSuccessMessageDisplayed())
                .isTrue().withFailMessage("Success message is not displayed");
    }
}
