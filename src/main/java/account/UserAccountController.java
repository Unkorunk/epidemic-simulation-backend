package account;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserAccountController {

    private final UserAccountRepository repository;

    UserAccountController(UserAccountRepository repository) {
        this.repository = repository;
    }

    // Find
    @GetMapping("/account")
    List<UserAccount> all() {
        return (List<UserAccount>) repository.findAll();
    }

    // Save
    @PostMapping("/account")
    UserAccount newUserAccount(@RequestBody UserAccount newEmployee) {
        return repository.save(newEmployee);
    }

    // Find
    @GetMapping("/account/{id}")
    UserAccount one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserAccountNotFoundException(id));
    }

    // Update or save
    @PutMapping("/account/{id}")
    UserAccount replaceUserAccount(@RequestBody UserAccount newUserAccount,
                                   @PathVariable Long id){

        return repository.findById(id)
                .map(user -> {
                    user.setFirstName(newUserAccount.getFirstName());
                    user.setLastName(newUserAccount.getLastName());

                    //user.setLogin(newUserAccount.getLogin());
                    user.setPassword(newUserAccount.getPassword());

                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUserAccount.setId(id);
                    return repository.save(newUserAccount);
                });
    }

    // Delete
    @DeleteMapping("/account/{id}")
    void deleteUserAccount(@PathVariable Long id) {
        repository.deleteById(id);
    }

    //@GetMapping("/login")
    //public UserAccount account(@RequestParam(value = "login") String login,
    //                           @RequestParam(value = "password") String password) {
//
    //    UserAccount user = repository.findByLogin(login);
//
    //    if (UserAccount.getSha256(password) == user.getPassHash()) {
//
    //    }
    //}
}
