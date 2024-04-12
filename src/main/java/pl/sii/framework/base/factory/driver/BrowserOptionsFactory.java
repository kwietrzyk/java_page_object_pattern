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
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

@Slf4j
public class BrowserOptionsFactory {

    public static AbstractDriverOptions getOptions() {
        switch (FactoryHelper.driverType) {
            case CHROME -> {
                return new ChromeOptions().addArguments("start-maximized");
            }
            case FIREFOX -> {
                return new FirefoxOptions();
            }
            default -> {
                log.warn("Browser not provided, using default one");
                WebDriverManager.chromedriver().setup();
                return new ChromeOptions();
            }
        }
    }
}
