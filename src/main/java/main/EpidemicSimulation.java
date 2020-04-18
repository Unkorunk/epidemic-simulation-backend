package main;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import parser.CountryInfoHelper;
import parser.NCoV2019Parser;

@SpringBootApplication
@ComponentScan(basePackages = { "account" })
public class EpidemicSimulation {
    public static void main(String[] args) {
        CountryInfoHelper.Init();
        ParserTest();
        ConfigurableApplicationContext context = SpringApplication.run(EpidemicSimulation.class, args);

        //UserAccountRepository repository = context.getBean(UserAccountRepository.class);
    }

    public static void ParserTest() {
        NCoV2019Parser parser = new NCoV2019Parser();
        var res = parser.Parse();

        System.out.println("Second parsing");

        parser.Parse();

        CountryInfoHelper.SaveCache();
        System.out.println(res.data.get(0).toString());
    }
}
