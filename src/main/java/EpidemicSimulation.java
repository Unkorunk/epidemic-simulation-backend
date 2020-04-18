import parser.NCoV2019Parser;
import parser.ParserData;
import simulation.SimpleSimulation;
import simulation.Simulation;

public class EpidemicSimulation {
    public static void main(String[] args) {
//        Simulation simulation = new SimpleSimulation();
//        simulation.Tick(1);
//        System.out.println("Hello World");
        System.out.println("tesst");
        ParserTest();


    }

    public static void ParserTest() {
        NCoV2019Parser parser = new NCoV2019Parser();
        ParserData res = parser.Parse();

        System.out.println(res.data.get(0).toString());
    }
}
