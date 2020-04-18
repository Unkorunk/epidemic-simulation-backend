package simulation;

import parser.DayData;
import parser.ParserData;

import java.time.LocalDate;

public interface Simulation {
    public void Tick(int duration);
    public void Reset();
    public void Reset(LocalDate startDate);
    public void AddInfo(ParserData source);
    public DayData GetInfo();
}
