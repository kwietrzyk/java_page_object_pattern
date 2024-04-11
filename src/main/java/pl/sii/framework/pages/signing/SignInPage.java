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

package pl.sii.framework.pages.signing;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.sii.framework.base.component.Page;
import pl.sii.framework.pages.myaccount.MyAccountPage;

@Slf4j
public class SignInPage extends Page {

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#email")
    private WebElement emailInput;

    @FindBy(css = "#passwd")
    WebElement passwordInput;

    @FindBy(css = "#SubmitLogin")
    WebElement submitButton;

    @FindBy(css = "a[title='Recover your forgotten password']")
    WebElement lostPasswordLink;

    @FindBy(css = ".alert-danger")
    WebElement alertMessage;


    @Step("User sets email address to email input field")
    public SignInPage withEmail(String email) {
        log.info("Set email {}", email);
        emailInput.sendKeys(email);
        return this;
    }

    @Step("User sets password to password input field")
    public SignInPage withPassword(String password) {
        log.info("Set password {}", password);
        passwordInput.sendKeys(password);
        return this;
    }

    @Step("User clicks on Submit button")
    public MyAccountPage submit() {
        log.info("Submit login form");
        submitButton.click();
        return pageFactory.create(MyAccountPage.class);
    }

    @Step("User clicks on Submit button")
    public SignInPage submitWithoutSuccess() {
        log.info("Submit login form");
        submitButton.click();
        return this;
    }

    @Step("User checks if alert message is displayed")
    public boolean isAlertMessageDisplayed() {
        log.info("Check if alert message is displayed");
        return webDriverWait.until(webDriver -> alertMessage.isDisplayed());
    }

    @Step("User clicks 'Lost password'")
    public ForgottenPasswordPage retrieveForgottenPassword() {
        log.info("Clicks 'Forgot your password?'");
        lostPasswordLink.click();
        return pageFactory.create(ForgottenPasswordPage.class);
    }
}