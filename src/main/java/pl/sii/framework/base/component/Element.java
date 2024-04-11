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

package pl.sii.framework.base.component;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
public class Element implements WebElement, WrapsElement {

    private final WebElement webElement;

    public Element(final WebElement webElement) {
        this.webElement = webElement;
    }

    @Override
    public void click() {
        webElement.click();
    }

    @Override
    public void submit() {
        webElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        webElement.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        webElement.clear();
    }

    @Override
    public String getTagName() {
        return webElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return webElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return webElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    @Override
    public String getText() {
        return webElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return webElement.findElements(by);
    }

    public List<Element> findElements(Locator locator) {
        return findElements(locator.by()).stream()
                .map(Element::new)
                .collect(Collectors.toList());
    }

    @Override
    public WebElement findElement(By by) {
        return webElement.findElement(by);
    }

    public Element findElement(Locator locator) {
        return new Element(findElement(locator.by()));
    }

    public String getTitle() {
        return webElement.getAttribute("title");
    }

    public WebElement findElement(By by, int timeOutInSeconds) {
        try {
            return findElement(by);
        } catch (NotFoundException e) {
            new FluentWait<WebElement>(this)
                    .withTimeout(Duration.of(timeOutInSeconds, SECONDS))
                    .pollingEvery(Duration.of(100, MILLIS))
                    .ignoring(NoSuchElementException.class);
            return findElement(by);
        }
    }

    public boolean hasElement(final Locator locator) {
        try {
            webElement.findElement(locator.by());
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return webElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return webElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return webElement.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return webElement.getCssValue(propertyName);
    }

    @Override
    public WebElement getWrappedElement() {
        return webElement;
    }

    public String attribute(String name) {
        return getAttribute(name);
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            log.error("sleep exception", exception);
        }
    }

    public Element parent() {
        return new Element(this.webElement.findElement(By.xpath("..")));
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return null;
    }

    public boolean containsLink() {
        return getTagName().equals("a") || !findElements(By.tagName("a")).isEmpty();
    }
}