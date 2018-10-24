package demo.api.v1;

import demo.domain.Account;
import demo.domain.User;
import demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The {@link AccountServiceV1} class provides a service to retrieve user accounts from the current user.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Service
public class AccountServiceV1 {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    @LoadBalanced
    private OAuth2RestTemplate oAuth2RestTemplate;

    public List<Account> getUserAccounts() {
        List<Account> accounts = null;
        User user = oAuth2RestTemplate.getForObject("http://user-service/uaa/v1/me", User.class);
        if (user != null) {
            accounts = accountRepository.findAccountsByUserId(user.getId());
        }

        // Mask credit card numbers
        if (accounts != null) {
            accounts.forEach(acct -> acct.getCreditCards()
                    .forEach(card ->
                            card.setNumber(card.getNumber()
                                    .replaceAll("([\\d]{4})(?!$)", "****-"))));
        }

        return accounts;
    }
}