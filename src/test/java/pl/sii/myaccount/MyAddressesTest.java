package pl.sii.myaccount;

import org.junit.jupiter.api.*;
import pl.sii.base.BaseTest;
import pl.sii.framework.pages.myaccount.MyAccountPage;
import pl.sii.framework.pages.myaccount.MyAddressesPage;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;

public class MyAddressesTest extends BaseTest {

    private MyAccountPage myAccount;

    @BeforeEach
    public void setupClearMyAccountPage() {
        myAccount = signInSuccessfully().goToMyAddresses().removeExistingAddressesIfAny().goToMyAccount();
    }

    @Test
    @Tag("address")
    @DisplayName("Add valid first address, verify and delete")
    public void fillAddressMandatoryFieldsShouldSucceed() {
        String addressTitle = faker.address().streetName();
        MyAddressesPage myAddressesPage = myAccount.addFirstAddress()
                .withFistName(faker.name().firstName())
                .withLastName(faker.name().lastName())
                .withAddress(faker.address().streetAddress())
                .withCity(faker.address().city())
                .withState(faker.address().state())
                .withZipCode(String.valueOf(new Random().nextInt(80000) + 10010))
                .withHomePhoneNumber(faker.phoneNumber().cellPhone())
                .withTitleForAddress(addressTitle)
                .submit();

        assertThat(myAddressesPage.isAddressAddedSuccessfully(addressTitle)).isTrue().withFailMessage("Address is not added properly");
        assertThat(myAddressesPage.removeExistingAddressesIfAny().isNoAddressAvailable()).isTrue().withFailMessage("'No Addresses' info is not displayed");
    }

    @Test
    @Tag("address")
    @DisplayName("Add several valid addresses, verify and delete")
    public void shouldAddSeveralNewAddresses() {
        int number = 2;
        MyAddressesPage myAddressesPage = myAccount.goToMyAddresses().addNewValidAddresses(number, faker);

        assertThat(myAddressesPage.getMyAddresses().size() == number).withFailMessage(number + " addresses were not created");
        myAddressesPage.removeExistingAddressesIfAny();
    }
}
