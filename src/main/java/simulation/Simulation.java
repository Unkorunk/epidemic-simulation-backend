package simulation;

import parser.DayData;
import parser.ParserData;

public interface Simulation {
    public void Tick(int duration);
    public void Reset();
    public void AddInfo(ParserData source);
    public DayData GetInfo();
}
