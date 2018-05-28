package demo;

import com.google.common.collect.Sets;
import demo.domain.Account;
import demo.domain.Address;
import demo.domain.CreditCard;
import demo.domain.Customer;
import demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Profile({"development"})
public class DatabaseInitializer {

    @Autowired
    private CustomerRepository customerRepository;

    public void populate() throws Exception {

        Address address1 = new Address();
        address1.setCountry("United States");
        address1.setState("CA");
        address1.setCity("Palo Alto");
        address1.setStreet1("3495 Deer Creek Road");
        address1.setStreet2("");
        address1.setZipCode(94304);
        address1.setAddressType(Address.AddressType.BILLING);

        Address address2 = new Address();
        address2.setCountry("United States");
        address2.setState("CA");
        address2.setCity("Palo Alto");
        address2.setStreet1("3495 Deer Creek Road");
        address2.setStreet2("");
        address2.setZipCode(94304);
        address2.setAddressType(Address.AddressType.SHIPPING);

        CreditCard creditCard = new CreditCard();
        creditCard.setNumber("1234567801234567");
        creditCard.setType(CreditCard.CreditCardType.MASTERCARD);

        Account account = new Account();
        account.setAccountNumber("12345");
        account.setDefaultAccount(Boolean.TRUE);
        account.setUserId(1L);
        account.setAddresses(Sets.newHashSet(address1, address2));
        account.setCreditCards(Sets.newHashSet(creditCard));

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setAccount(account);

        customerRepository.save(customer);
    }

}
