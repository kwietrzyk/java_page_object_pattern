package pl.sii.framework.pages.myaccount;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.sii.framework.base.component.Page;

@Slf4j
public class MyAccountPage extends Page {

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//p[contains(text(), 'Your account has been created.')]") // OR @FindBy(css = .alert-success")
    WebElement successAlertMessage;

    @FindBy(css = "*[title='Add my first address']")
    WebElement addMyAddress;

    @FindBy(css = "*[title='My addresses']")
    WebElement myAddressesButton;

    @FindBy(css = "*[title='Information']")
    WebElement myPersonalInfoButton;

    @Step("User checks if sing in ends with success")
    public boolean isSignInSucceed() {
        log.info("Check if url contains 'my-account'");
        return webDriverWait.until(ExpectedConditions.urlContains("my-account"));
    }

    @Step("User checks if alert message is displayed")
    public boolean isSuccessMessageDisplayed() {
        log.info("Check if success message is displayed");
        return webDriverWait.until(webDriver -> successAlertMessage.isDisplayed());
    }

    @Step("User clicks Add My First Address")
    public AddAddressPage addFirstAddress() {
        log.info("Go to 'Add my first address' page");
        addMyAddress.click();
        return pageFactory.create(AddAddressPage.class);
    }

    @Step("User clicks My Addresses")
    public MyAddressesPage goToMyAddresses() {
        log.info("Go to 'My addresses' page");
        myAddressesButton.click();
        return pageFactory.create(MyAddressesPage.class);
    }

    public MyPersonalInformationPage updateMyPersonalInfo() {
        log.info("Go to 'My personal information' page");
        myPersonalInfoButton.click();
        return pageFactory.create(MyPersonalInformationPage.class);
    }
}
