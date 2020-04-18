package main;

import org.springframework.boot.autoconfigure.SpringBootApplication;

//import account.UserAccount;
//import account.UserAccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "account" })
public class EpidemicSimulation {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EpidemicSimulation.class, args);

        //UserAccountRepository repository = context.getBean(UserAccountRepository.class);

    }
}
