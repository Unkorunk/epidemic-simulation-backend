package account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.*;
import parser.CountryData;
import parser.DayData;
import parser.ParserData;

@RestController
public class UserAccountController {
//
//    private final UserAccountRepository repository;
//
//    UserAccountController(UserAccountRepository repository) {
//        this.repository = repository;
//    }

    // TOOD: remove that
    private static Random random = new Random();
    @RequestMapping("/randomdata")
    public ParserData getRandomParserData() {
        ParserData parserData = new ParserData();

        String[] countries = new String[] { "Russia", "USA", "China", "Canada", "Democratic Republic of the Congo" };

        parserData.source = "Random Source";
        int count_days = 3 + random.nextInt(10);

        parserData.data = new ArrayList<DayData>();
        for (int i = 0; i < count_days; i++) {
            parserData.data.add(new DayData());
            parserData.data.get(i).date = LocalDate.now().plusDays(i);
            parserData.data.get(i).countries = new HashMap<>();
            int count_countries = 2 + random.nextInt(2);
            for (int j = 0; j < count_countries; j++)  {
                CountryData countryData = new CountryData();
                countryData.countryId = random.nextInt(countries.length);
                countryData.countryName = countries[countryData.countryId];
                countryData.deaths = random.nextInt(10000);
                countryData.infected = random.nextInt(10000);
                countryData.recovered = random.nextInt(10000);

                parserData.data.get(i).countries.put(countryData.countryId, countryData);
            }
        }


        return parserData;
    }


//    // Find
//    @GetMapping("/account")
//    List<UserAccount> all() {
//        return (List<UserAccount>) repository.findAll();
//    }
//
//    // Save
//    @PostMapping("/account")
//    UserAccount newUserAccount(@RequestBody UserAccount newEmployee) {
//        return repository.save(newEmployee);
//    }
//
//    // Find
//    @GetMapping("/account/{id}")
//    UserAccount one(@PathVariable Long id) {
//
//        return repository.findById(id)
//                .orElseThrow(() -> new UserAccountNotFoundException(id));
//    }
//
//    // Update or save
//    @PutMapping("/account/{id}")
//    UserAccount replaceUserAccount(@RequestBody UserAccount newUserAccount,
//                                   @PathVariable Long id){
//
//        return repository.findById(id)
//                .map(user -> {
//                    user.setFirstName(newUserAccount.getFirstName());
//                    user.setLastName(newUserAccount.getLastName());
//
//                    //user.setLogin(newUserAccount.getLogin());
//                    user.setPassword(newUserAccount.getPassword());
//
//                    return repository.save(user);
//                })
//                .orElseGet(() -> {
//                    newUserAccount.setId(id);
//                    return repository.save(newUserAccount);
//                });
//    }
//
//    // Delete
//    @DeleteMapping("/account/{id}")
//    void deleteUserAccount(@PathVariable Long id) {
//        repository.deleteById(id);
//    }
//
////    @GetMapping("/login")
////    public UserAccount account(@RequestParam(value = "login") String login,
////                               @RequestParam(value = "password") String password) {
////
////        UserAccount user = repository.findByLogin(login);
////
////        if (UserAccount.getSha256(password) == user.getPassHash()) {
////
////        }
////    }
}
