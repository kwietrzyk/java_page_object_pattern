/*
 * Copyright (c) 2019.  Sii Poland
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

@Slf4j
public class SignUpPage extends Page {

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#email_create")
    private WebElement emailInput;

    @FindBy(css = "#SubmitCreate")
    WebElement submitButton;

    @FindBy(css = "#create_account_error")
    WebElement alertMessage;


    @Step("User sets email address to email input field")
    public SignUpPage withEmail(String email) {
        log.info("Set email {}", email);
        emailInput.sendKeys(email);
        return this;
    }

    @Step("User clicks on Submit button")
    public CreateAccountPage submit() {
        log.info("Submit navigate to 'Create account' page");
        submitButton.click();
        return pageFactory.create(CreateAccountPage.class);
    }

    @Step("User clicks on Submit button")
    public SignUpPage submitWithoutSuccess() {
        log.info("Submit login form");
        submitButton.click();
        return this;
    }

    @Step("User checks if alert message is displayed")
    public boolean isAlertMessageDisplayed() {
        log.info("Check if alert message is displayed");
        return webDriverWait.until(webDriver -> alertMessage.isDisplayed());
    }

}
