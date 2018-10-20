package demo.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/v1")
public class AccountControllerV1 {

    @Autowired
    private AccountServiceV1 accountService;

    @RequestMapping(path = "/accounts")
    public ResponseEntity getUserAccount() throws Exception {
        return Optional.ofNullable(accountService.getUserAccounts())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Accounts for user do not exist."));
    }

}
