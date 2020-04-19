package main;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import parser.CountryInfoHelper;
import parser.JHUCSSEParser;
import parser.NCoV2019Parser;

@SpringBootApplication
@ComponentScan(basePackages = { "account" })
public class EpidemicSimulation {
    public static void main(String[] args) {
        CountryInfoHelper.Init();
        ParserTest();
        JHUCSSETest();

        ConfigurableApplicationContext context = SpringApplication.run(EpidemicSimulation.class, args);

        //UserAccountRepository repository = context.getBean(UserAccountRepository.class);
    }

    public static void ParserTest() {
        NCoV2019Parser parser = new NCoV2019Parser();
        var res = parser.Parse();

        System.out.println(res.data.get(0).toString());
    }

    public static void JHUCSSETest() {
        JHUCSSEParser parser = new JHUCSSEParser();
        var res = parser.Parse();

        System.out.println(res.data.get(res.data.size() - 1).toString());
    }
}
