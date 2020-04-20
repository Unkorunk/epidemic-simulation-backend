package simulation;

import parser.JHUCSSEParser;
import parser.NCoV2019Parser;
import parser.ParserData;

public class BalPerf {
    private static ParserData cached = null;

    public static void Update(int duration, int rateInf, int rateDea, int rateRec) {
        var parseData = new NCoV2019Parser().Parse();
        parseData.Merge(new JHUCSSEParser().Parse());

        Simulation.rateInf = rateInf;
        Simulation.rateDea = rateDea;
        Simulation.rateRec = rateRec;
        parseData.Merge(Simulation.Predict(parseData.data.get(parseData.data.size() - 1), duration));

        cached = parseData;
    }

    public static ParserData GetInfo() {
        return cached;
    }
}
