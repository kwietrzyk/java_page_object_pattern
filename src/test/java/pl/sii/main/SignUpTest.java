/*
 * Copyright (c) 2024.  Sii Poland
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.sii.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pl.sii.base.BaseTest;
import pl.sii.framework.helpers.Title;
import pl.sii.framework.pages.signing.CreateAccountPage;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class SignUpTest extends BaseTest {

    // Required fields in form: lastname, firstname, email, passwd.

    @Test
    @Tag("signup")
    @DisplayName("Sign up with correct credentials")
    public void userShouldBeAbleToSignUpWhenFormFilledCorrectly() {

        CreateAccountPage createAccountPage = createAccountPageWithRandomValidEmail();
        assertThat(createAccountPage
                .waitForPageLoadWithProperPartUrl("account-creation")
                    .setTitle(Title.randomValue())
                    .withFirstName(faker.name().firstName())
                    .withLastName(faker.name().lastName())
                    .withPassword(faker.internet().password(5, 15))
                    .withDayOfBirth(ThreadLocalRandom.current().nextInt(1, 29))
                    .withMonthOfBirth(ThreadLocalRandom.current().nextInt(1, 13))
                    .withYearOfBirth(ThreadLocalRandom.current().nextInt(1900, 2020))
                    .signUpForNewsletter()
                    .submit()
                    .isSuccessMessageDisplayed())
                .isTrue()
                .withFailMessage("Sign up with correct credentials failed");
    }

    @Test
    @Tag("signup")
    @DisplayName("Sign up with correct credentials - mandatory fields only")
    public void userShouldBeAbleToSignUpWithCorrectMandatoryFields() {

        CreateAccountPage createAccountPage = createAccountPageWithRandomValidEmail();
        assertThat(createAccountPage
                    .waitForPageLoadWithProperPartUrl("account-creation")
                    .withFirstName(faker.name().firstName())
                    .withLastName(faker.name().lastName())
                    .withPassword(faker.internet().password(5, 15))
                    .submit()
                    .isSuccessMessageDisplayed())
                .isTrue()
                .withFailMessage("Sign up with correct credentials failed");
    }

    @Test
    @Tag("signup")
    @DisplayName("Sign up with already registered email address")
    public void userShouldNotBeAbleToSignUpWithAlreadyRegisteredEmail() {
        assertThat(
                application.open()
                        .signUp()
                        .withEmail(configEmailInput)
                        .submitWithoutSuccess()
                        .isAlertMessageDisplayed())
                .isTrue()
                .withFailMessage("Alert message is not displayed");
    }

    @Test
    @Tag("signup")
    @DisplayName("Sign up without mandatory field : first name")
    public void userShouldNotBeAbleToSignUpWithoutFirstName() {

        CreateAccountPage createAccountPage = createAccountPageWithRandomValidEmail();
        assertThat(createAccountPage
                    .waitForPageLoadWithProperPartUrl("account-creation")
                    .setTitle(Title.randomValue())
                    .withLastName(faker.name().lastName())
                    .withPassword(faker.internet().password())
                    .withDayOfBirth(ThreadLocalRandom.current().nextInt(1, 29))
                    .withMonthOfBirth(ThreadLocalRandom.current().nextInt(1, 13))
                    .withYearOfBirth(ThreadLocalRandom.current().nextInt(1900, 2020))
                    .submitWithoutSuccess()
                    .isMissingFieldAlertMessageDisplayed())
                .isTrue()
                .withFailMessage("Sign up with correct credentials failed");
    }

    private CreateAccountPage createAccountPageWithRandomValidEmail() {
            return application
                            .open()
                            .signUp()
                            .withEmail(faker.internet().emailAddress())
                            .submit();
    }
}
