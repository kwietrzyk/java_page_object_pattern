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

package pl.sii.base;

import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import pl.sii.framework.base.Application;
import pl.sii.framework.base.component.Page;
import pl.sii.framework.base.factory.DriverFactoryProvider;
import pl.sii.framework.configuration.Configuration;
import pl.sii.framework.pages.myaccount.MyAccountPage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {
    private WebDriver driver;
    protected Application application;
    protected Faker faker = new Faker();
    protected Configuration config = ConfigFactory.create(Configuration.class);
    protected String configEmailInput = config.email();
    protected String configPasswordInput = config.password();

    protected static File file;

    @BeforeEach
    public void setUpBeforeEach() {
        this.driver = new DriverFactoryProvider().getDriverFactory().getDriver();
        application = new Application(driver);
    }

    @AfterEach
    public void cleanUpAfterEach() {
        Allure.getLifecycle().addAttachment(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png", makeScreenShot());
        application.close();

    }

    private byte[] makeScreenShot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    protected void createTestFile() {
        String filePath = "src/main/resources/testFile.txt";
        file = new File(filePath);

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected MyAccountPage signInSuccessfully() {
        return application.open()
                .signIn()
                .withEmail(configEmailInput)
                .withPassword(configPasswordInput)
                .submit();
    }
}
