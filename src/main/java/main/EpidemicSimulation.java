package main;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import parser.*;

import java.util.ArrayList;

@SpringBootApplication
@ComponentScan(basePackages = { "account" })
public class EpidemicSimulation {
    public static void main(String[] args) {
        CountryInfoHelper.Init();
        FullParcerTest();

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

    public static void FullParcerTest() {
        var resList = new ArrayList<ParserData>();

        NCoV2019Parser parser1 = new NCoV2019Parser();
        resList.add(parser1.Parse());

        JHUCSSEParser parser2 = new JHUCSSEParser();
        resList.add(parser2.Parse());

        System.out.println(resList.get(1).data.get(resList.get(1).data.size() - 3).GetTotal());
        System.out.println(resList.get(1).data.get(resList.get(1).data.size() - 3));

        var res = Parser.Merge(resList);
    }
}
