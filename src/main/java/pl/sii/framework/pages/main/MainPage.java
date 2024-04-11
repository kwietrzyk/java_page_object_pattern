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

package pl.sii.framework.pages.main;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.sii.framework.base.component.Page;
import pl.sii.framework.pages.signing.SignInPage;
import pl.sii.framework.pages.signing.SignUpPage;

@Slf4j
public class MainPage extends Page {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#newsletter-input")
    private WebElement newsletter;

    @FindBy(css = "button[name=submitNewsletter]")
    private WebElement submitNewsletter;

    @FindBy(xpath = "//p[contains(text(), 'Newsletter : Invalid email address')]")
    WebElement invalidEmailAlert;

    @FindBy(xpath = "//p[contains(text(), 'You have successfully subscribed')]")
    WebElement validEmailAlert;


    @Step("User navigate to 'Sign up' page")
    public SignUpPage signUp() {
        log.info("Go to 'Sign up' page");
        signInLink.click();
        return pageFactory.create(SignUpPage.class);
    }

    @Step("User sends email address to subscribe the newsletter")
    public MainPage subscribeNewsletterWithEmail(String emailInput) {
        log.info("Send email address to subscribe the newsletter");
        newsletter.sendKeys(emailInput);
        submitNewsletter.click();
        return this;
    }

    @Step("User verifies if invalid email is rejected")
    public boolean isInvalidEmailMessageDisplayed() {
        return webDriverWait.until(webDriver -> invalidEmailAlert.isDisplayed());
    }

    @Step("User verifies if valid email is registered for newsletter")
    public boolean isValidEmailMessageDisplayed() {
        return webDriverWait.until(webDriver -> validEmailAlert.isDisplayed());
    }

}