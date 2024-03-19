package pl.sii.framework.pages.myaccount;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.sii.framework.base.component.Page;
import pl.sii.framework.pages.signing.SignUpPage;

import java.util.List;

@Slf4j
public class MyAddressesPage extends Page {
    public MyAddressesPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "*[title=Delete]")
    List<WebElement> deleteButtons;

    @FindBy(xpath = "//p[contains(text(), 'No addresses are available')]")
    WebElement noAddressesInfo;

    @FindBy(css = "a[href*='controller=my-account']")
    WebElement backToMyAccount;

    @FindBy(css = "a[title='Add an address']")
    WebElement addNewAddressButton;

    @FindBy(css = "a[title='Update']")
    List<WebElement> myAddresses;

    @Step("User clicks 'Delete' buttons")
    public MyAddressesPage removeExistingAddressesIfAny() {
        log.info("Delete addresses");
        while (!deleteButtons.isEmpty()) {
            deleteButtons.get(0).click();
            acceptAlert();
            // deleteButtons = driver.findElements(By.cssSelector("*[title=Delete]"));   // in case of StaleElementException you should uncomment this line to refresh elements list
        }
        return this;
    }

    @Step("User accepts 'Delete' alert")
    private void acceptAlert() {
        webDriverWait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Step("User clicks 'Back to your account'")
    public MyAccountPage goToMyAccount() {
        log.info("Go to 'My addresses' page");
        backToMyAccount.click();
        return pageFactory.create(MyAccountPage.class);
    }

    public boolean isAddressAddedSuccessfully(String addressTitle) {
        WebElement addedTitle = driver.findElement(By.xpath("//*[contains(text(), '" + addressTitle + "')]"));
        return webDriverWait.until(webDriver -> addedTitle.isDisplayed());
    }

    @Step("User verifies if no address is available")
    public boolean isNoAddressAvailable() {
        return webDriverWait.until(webDriver -> noAddressesInfo.isDisplayed());
    }

    @Step("User adds new address")
    public AddAddressPage addNewAddress() {
        log.info("Add new address");
        addNewAddressButton.click();
        return pageFactory.create(AddAddressPage.class);
    }

    public List<WebElement> getMyAddresses() {
        return myAddresses;
    }

    @Step("User adds specified number of new addresses")
    public MyAddressesPage addNewValidAddresses(int number, Faker faker) {
        for (int i = 0; i < number; i++) {
            this.addNewAddress().addNewValidAddress(faker);
        }
        return this;
    }
}
