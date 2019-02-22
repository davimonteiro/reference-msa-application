package demo.api;

import demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


/**
 * This REST controller provides APIs to retrieve user accounts from the current user.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@RestController
@RequestMapping(path = "/v1")
public class AccountControllerV1 {

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/accounts")
    public ResponseEntity getUserAccount() throws Exception {
        return Optional.ofNullable(accountService.getUserAccounts())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Accounts for user do not exist."));
    }

}