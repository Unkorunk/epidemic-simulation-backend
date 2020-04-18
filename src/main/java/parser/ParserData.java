package parser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ParserData {
    public String source;

    public HashMap<LocalDate, DayData> perDayData;

    public void Merge(ParserData pd) {
        for (var entry : pd.perDayData.entrySet()) {
            if (this.perDayData.containsKey(entry.getKey())) {
                this.perDayData.get(entry.getKey()).Merge(entry.getValue());
            } else {
                this.perDayData.put(entry.getKey(), entry.getValue());
            }
        }

        if (pd.source != this.source) {
            this.source = "undefined";
        }
    }
}
