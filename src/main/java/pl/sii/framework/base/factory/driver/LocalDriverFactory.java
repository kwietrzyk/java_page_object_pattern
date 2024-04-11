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

package pl.sii.framework.base.factory.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pl.sii.framework.base.factory.loader.ChromeDriverLoader;
import pl.sii.framework.base.factory.loader.FirefoxDriverLoader;

@Slf4j
public class LocalDriverFactory implements IDriverFactory {

    @Override
    public WebDriver getDriver() {
        switch (FactoryHelper.driverType) {
            case CHROME -> {
                ChromeDriverLoader.load();
                return new ChromeDriver((ChromeOptions) BrowserOptionsFactory.getOptions());
            }
            case FIREFOX -> {
                FirefoxDriverLoader.load();
                return new FirefoxDriver((FirefoxOptions) BrowserOptionsFactory.getOptions());
            }
            default -> {
                log.warn("Browser not provided, using default one");
                ChromeDriverLoader.load();
                return new ChromeDriver((ChromeOptions) BrowserOptionsFactory.getOptions());
            }
        }
    }

}