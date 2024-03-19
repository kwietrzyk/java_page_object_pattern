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

package pl.sii.framework.base.component;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import pl.sii.framework.base.factory.PageFactory;
import pl.sii.framework.pages.main.ContactUsPage;
import pl.sii.framework.pages.signing.SignInPage;

import java.time.Duration;
import java.util.List;

@Slf4j
public abstract class Page {

    @Getter
    protected WebDriver driver;
    @Getter
    protected Wait<WebDriver> webDriverWait;
    protected PageFactory pageFactory;

    @FindBy(css = ".header_user_info")
    public WebElement signInLink;

    @FindBy(css = ".logout")
    public WebElement signOutLink;

    @FindBy(css = "a[href*='contact']")
    public WebElement contactLink;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(300))
                .withMessage("Condition was not met in specified timeout");
        pageFactory = new PageFactory(driver);
    }

    public List<Element> findElements(final Locator locator) {
        return driver.findElements(locator.by())
                .stream()
                .map(Element::new)
                .toList();
    }

    public Element findElement(final Locator locator) {
        return new Element(driver.findElement(locator.by()));
    }


    public void refresh() {
        driver.navigate().refresh();
    }

    public boolean isDisplayed(final Locator locator) {
        try {
            return driver.findElement(locator.by()).isDisplayed();
        } catch (NotFoundException | StaleElementReferenceException | ElementNotInteractableException e) {
            return false;
        }
    }

    public static boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NotFoundException | StaleElementReferenceException | ElementNotInteractableException e) {
            return false;
        }
    }

    @Step("User wait for page load")
    public Page waitForPageLoadWithProperPartUrl(String urlContains) {
        webDriverWait.until(ExpectedConditions.urlContains(urlContains));
        return this;
    }

    @Step("User navigate to 'Sign in' page")
    public SignInPage signIn() {
        log.info("Go to 'Sign in' page");
        signInLink.click();
        return pageFactory.create(SignInPage.class);
    }

    @Step("User clicks Sign out button")
    public Page signOut() {
        log.info("Click sign out button");
        signOutLink.click();
        return this;
    }

    @Step("User checks proper sign out")
    public boolean isSignedOutProperly() {
        log.info("Check if user is signed out");
        return webDriverWait.until(webDriver -> signInLink.isDisplayed());
    }

    @Step("User clicks Contact us button")
    public ContactUsPage contactUs() {
        log.info("Click Contact Us link");
        contactLink.click();
        return pageFactory.create(ContactUsPage.class);
    }
}
