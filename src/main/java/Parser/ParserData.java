package main.java.Parser;

import java.util.Date;
import java.util.Dictionary;

public class ParserData {
    public String source;
    public Date startDate;
    public Date endDate;

    public Dictionary<Date, DayData> perDayData;
}
