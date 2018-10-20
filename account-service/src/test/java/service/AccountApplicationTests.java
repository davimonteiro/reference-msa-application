package service;

import com.google.common.collect.Sets;
import demo.AccountApplication;
import demo.domain.Account;
import demo.domain.Address;
import demo.domain.CreditCard;
import demo.domain.Customer;
import demo.repository.CustomerRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AccountApplication.class)
@ActiveProfiles(profiles = "test")
public class AccountApplicationTests extends TestCase {

    private Logger log = LoggerFactory.getLogger(AccountApplicationTests.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void customerTest() {
        log.info("*** Starting Customer Test");

        // Create a new account
        Account account = new Account();
        account.setUserId(5L);
        account.setAccountNumber("12345");
        account.setCreditCards(Sets.newHashSet());
        account.setAddresses(Sets.newHashSet());

        // Create a new customer for the account
        Customer customer = new Customer();
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@gmail.com");
        customer.setAccount(account);


        // Create a new credit card for the account
        CreditCard creditCard = new CreditCard();
        creditCard.setNumber("1234567801234567");
        creditCard.setType(CreditCard.CreditCardType.VISA);

        // Add the credit card to the customer's account
        customer.getAccount()
                .getCreditCards()
                .add(creditCard);

        // Create a new shipping address for the customer
        Address address = new Address();
        address.setStreet1("1600 Pennsylvania Ave NW");
        address.setState("DC");
        address.setCity("Washington");
        address.setCountry("United States");
        address.setZipCode(20500);
        address.setAddressType(Address.AddressType.SHIPPING);

        // Add address to the customer's account
        customer.getAccount()
                .getAddresses()
                .add(address);

        // Apply the cascading update by persisting the customer object
        customer = customerRepository.save(customer);

        // Query for the customer object to ensure cascading persistence of the object graph
        log.info(customerRepository.findById(customer.getId()).toString());
    }
}
