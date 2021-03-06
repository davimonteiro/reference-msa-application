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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Initialize and populate the local database.
 *
 * @author Davi Monteiro
 */
@Service
public class DatabaseInitializer {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public void populate() {
        customerRepository.deleteAll();
        for (long i = 0; i < 1000; i++) {
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
            address2.setCity("San Francisco");
            address2.setStreet1("12 Lombard Street");
            address2.setStreet2("");
            address2.setZipCode(84393);
            address2.setAddressType(Address.AddressType.SHIPPING);

            CreditCard creditCard = new CreditCard();
            creditCard.setNumber("1234567801234567");
            creditCard.setType(CreditCard.CreditCardType.MASTERCARD);


            Account account = new Account();
            account.setAccountNumber("12345"+i);
            account.setDefaultAccount(Boolean.TRUE);
            account.setUserId(i);
            account.setAddresses(Sets.newHashSet(address1, address2));
            account.setCreditCards(Sets.newHashSet(creditCard));

            Customer customer = new Customer();
            customer.setFirstName("John"+i);
            customer.setLastName("Doe"+i);
            customer.setEmail("john.doe@example.com"+i);
            customer.setAccount(account);

            customerRepository.save(customer);
        }
    }
}
