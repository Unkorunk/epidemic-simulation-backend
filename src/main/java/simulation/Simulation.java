package simulation;

import parser.ParserData;

public interface Simulation {
    public void Tick(int duration);
    public void Reset();
    public void AddInfo(ParserData source);
    public ParserData GetInfo();
}
