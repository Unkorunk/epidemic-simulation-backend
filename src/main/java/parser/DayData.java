package parser;

import java.util.HashMap;

public class DayData {
    /// Key - country id
    public HashMap<Integer, CountryData> countries;

    public void Merge(DayData dayData) {
        for (var entry : dayData.countries.entrySet()) {
            if (this.countries.containsKey(entry.getKey())) {
                try {
                    this.countries.get(entry.getKey()).Merge(entry.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                this.countries.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
