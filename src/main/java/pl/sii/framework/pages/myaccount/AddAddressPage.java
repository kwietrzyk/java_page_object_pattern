package pl.sii.framework.pages.myaccount;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import pl.sii.framework.base.component.Page;

import java.util.List;
import java.util.Random;
@Slf4j
public class AddAddressPage extends Page {
    public AddAddressPage(WebDriver driver) {
        super(driver);
    }

    // Mandatory:
    @FindBy(css = "#firstname")
    private WebElement firstNameInput;

    @FindBy(css = "#lastname")
    private WebElement lastNameInput;

    @FindBy(css = "#address1")
    private WebElement addressInput;

    @FindBy(css = "#city")
    private WebElement cityInput;

    @FindBy(css = "#id_state")
    private WebElement stateInput;

    @FindBy(css = "#postcode")
    private WebElement zipCodeInput;

    @FindBy(css = "#id_country")
    private WebElement countryInput;

    @FindBy(css = "#phone")
    private WebElement homePhoneInput;

    @FindBy(css = "#phone_mobile")
    private WebElement mobilePhoneInput;

    @FindBy(css = "#alias")
    WebElement addressTitle;

    @FindBy(css = "#submitAddress")
    WebElement submitButton;


    // Not mandatory:
    @FindBy(css = "#company")
    private WebElement companyInput;

    @FindBy(css = "#address2")
    private WebElement additionalAddressInput;

    @FindBy(css = "#other")
    WebElement additionalInfoInput;


    // Other:
    @FindBy(css = "a[href*='controller=address'].btn")
    WebElement backClick;

    @FindBy(css = ".alert-danger")
    WebElement alertErrorMessage;

    @FindBy(css = "")
    WebElement alertSuccessMessage;


    @Step("User sets first name to input field")
    public AddAddressPage withFistName(String firstName) {
        log.info("Set first name {}", firstName);
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        return this;
    }

    @Step("User sets last name to input field")
    public AddAddressPage withLastName(String lastName) {
        log.info("Set last name {}", lastName);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        return this;
    }

    @Step("User sets company to input field")
    public AddAddressPage withCompany(String company) {
        log.info("Set company name {}", company);
        companyInput.sendKeys(company);
        return this;
    }

    @Step("User sets address to input field")
    public AddAddressPage withAddress(String address) {
        log.info("Set address {}", address);
        addressInput.sendKeys(address);
        return this;
    }

    @Step("User sets additional address to input field")
    public AddAddressPage withAdditionalAddress(String address) {
        log.info("Set additional address {}", address);
        additionalAddressInput.sendKeys(address);
        return this;
    }

    @Step("User sets city to input field")
    public AddAddressPage withCity(String city) {
        log.info("Set city name {}", city);
        cityInput.sendKeys(city);
        return this;
    }

    @Step("User sets state to input field")
    public AddAddressPage withState(String state) {
        log.info("Set state {}", state);
        Select select = new Select(stateInput);
        List<WebElement> options = select.getOptions();
        if (options.stream().anyMatch(option -> option.getText().equals(state))) {
            select.selectByVisibleText(state);
        } else {
            throw new IllegalArgumentException("This state is not available in the list");
        }
        return this;
    }

    @Step("User sets zipcode to input field")
    public AddAddressPage withZipCode(String zipCode) {
        log.info("Set zipCode name {}", zipCode);
        zipCodeInput.sendKeys(zipCode);
        return this;
    }

    @Step("User sets home phone to input field")
    public AddAddressPage withHomePhoneNumber(String homePhone) {
        log.info("Set homePhone name {}", homePhone);
        homePhoneInput.sendKeys(homePhone);
        return this;
    }

    @Step("User sets mobile phone to input field")
    public AddAddressPage withMobilePhoneNumber(String mobilePhone) {
        log.info("Set mobile phone number {}", mobilePhone);
        mobilePhoneInput.sendKeys(mobilePhone);
        return this;
    }

    @Step("User sets additional information to input field")
    public AddAddressPage withAdditionalInfo(String additionalInfo) {
        log.info("Set additionalInfo {}", additionalInfo);
        additionalInfoInput.sendKeys(additionalInfo);
        return this;
    }

    @Step("User sets additional information to input field")
    public AddAddressPage withTitleForAddress(String title) {
        log.info("Set title for address {}", title);
        addressTitle.clear();
        addressTitle.sendKeys(title);
        return this;
    }

    @Step("User clicks on Submit button")
    public MyAddressesPage submit() {
        log.info("Submit login form");
        submitButton.click();
        return pageFactory.create(MyAddressesPage.class);
    }

    @Step("User clicks on Submit button")
    public AddAddressPage submitWithoutSuccess() {
        log.info("Submit login form");
        submitButton.click();
        return this;
    }

    public MyAddressesPage addNewValidAddress(Faker faker) {
        String addressTitle = faker.address().streetName();
        return this
                .withFistName(faker.name().firstName())
                .withLastName(faker.name().lastName())
                .withAddress(faker.address().streetAddress())
                .withCity(faker.address().city())
                .withState(faker.address().state())
                .withZipCode(String.valueOf(new Random().nextInt(80000) + 10010))
                .withHomePhoneNumber(faker.phoneNumber().cellPhone())
                .withTitleForAddress(addressTitle)
                .submit();
    }
}
