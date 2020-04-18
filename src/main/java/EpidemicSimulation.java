import simulation.SimpleSimulation;
import simulation.Simulation;

public class EpidemicSimulation {
    public static void main(String[] args) {
        Simulation simulation = new SimpleSimulation();
        simulation.Tick(1);
        System.out.println("Hello World");
    }
}
