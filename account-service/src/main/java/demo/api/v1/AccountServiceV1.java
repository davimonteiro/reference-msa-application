package demo.api.v1;

import demo.domain.Account;
import demo.repository.AccountRepository;
import demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceV1 {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    @LoadBalanced
    private OAuth2RestTemplate oAuth2RestTemplate;

    public List<Account> getUserAccounts() {
        List<Account> account = null;
        User user = oAuth2RestTemplate.getForObject("http://user-service/uaa/v1/me", User.class);
        if (user != null) {
            account = accountRepository.findAccountsByUserId(user.getId());
        }

        // Mask credit card numbers
        if (account != null) {
            account.forEach(acct -> acct.getCreditCards()
                    .forEach(card ->
                            card.setNumber(card.getNumber()
                                    .replaceAll("([\\d]{4})(?!$)", "****-"))));
        }

        return account;
    }
}
