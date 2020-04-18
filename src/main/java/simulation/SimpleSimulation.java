package simulation;

import parser.ParserData;

public class SimpleSimulation implements Simulation {


    @Override
    public void Tick(int duration) {

    }

    @Override
    public void Reset() {

    }

    @Override
    public void AddInfo(ParserData source) {

    }

    @Override
    public ParserData GetInfo() {
        return new ParserData();
    }
}
