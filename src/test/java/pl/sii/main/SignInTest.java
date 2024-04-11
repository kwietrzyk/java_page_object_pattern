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

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pl.sii.base.BaseTest;

import static org.assertj.core.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class SignInTest extends BaseTest {

    @Test
    @Tag("signin")
    @DisplayName("Sign in with random incorrect credentials")
    @Feature("Sign in to application with incorrect credentials")
    public void errorMessageShouldBeVisibleWhenIncorrectCredentialsProvided() {
        assertThat(
                application.open()
                        .signIn()
                        .withEmail(faker.internet().emailAddress())
                        .withPassword(faker.internet().password(5, 15))
                        .submitWithoutSuccess()
                        .isAlertMessageDisplayed())
                .isTrue()
                .withFailMessage("Alert message is not displayed");
    }


    @Test
    @Tag("signin")
    @DisplayName("Retrieve forgotten password")
    @Feature("Retrieve forgotten password")
    public void shouldRetrieveForgottenPassword() {
        assertThat(
                application.open()
                        .signIn()
                        .retrieveForgottenPassword()
                        .withEmail(configEmailInput)
                        .submit()
                        .isAlertSuccessMessageDisplayed())
                .isTrue()
                .withFailMessage("Alert message is not displayed");
    }

    @Test
    @Tag("signin")
    @DisplayName("Sign in with correct credentials")
    public void signInWithCorrectCredentialsShouldSucceed() {
        assertThat(
                application.open()
                        .signIn()
                        .withEmail(configEmailInput)
                        .withPassword(configPasswordInput)
                        .submit()
                        .isSignInSucceed())
                .isTrue()
                .withFailMessage("Sign in with correct credentials failed");
    }

    @Test
    @Tag("signout")
    @DisplayName("Sign out from My Account page")
    public void signOutShouldSucceed() {
        assertThat(
                signInSuccessfully().waitForPageLoadWithProperPartUrl("=my-account")
                    .signOut()
                    .isSignedOutProperly())
                .isTrue()
                .withFailMessage("Sign out failed");
    }

}