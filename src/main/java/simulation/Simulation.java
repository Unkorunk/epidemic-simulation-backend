package simulation;

import parser.DayData;
import parser.ParserData;

import java.util.ArrayList;

public class Simulation {
    public static ParserData Predict(DayData dayData, int duration) {
        var parserData = new ParserData();
        parserData.source = "Simulation";
        parserData.data = new ArrayList<>();
        parserData.data.add(dayData);


        return parserData;
    }
}
