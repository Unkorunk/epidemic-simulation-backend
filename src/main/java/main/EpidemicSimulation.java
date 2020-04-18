package main;

import org.springframework.boot.autoconfigure.SpringBootApplication;

//import account.UserAccount;
//import account.UserAccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import parser.NCoV2019Parser;

@SpringBootApplication
@ComponentScan(basePackages = { "account" })
public class EpidemicSimulation {
    public static void main(String[] args) {
        ParserTest();
        ConfigurableApplicationContext context = SpringApplication.run(EpidemicSimulation.class, args);

        //UserAccountRepository repository = context.getBean(UserAccountRepository.class);
    }

    public static void ParserTest() {
        NCoV2019Parser parser = new NCoV2019Parser();
        var res = parser.Parse();
        System.out.println(res.data.get(0).toString());
    }
}
