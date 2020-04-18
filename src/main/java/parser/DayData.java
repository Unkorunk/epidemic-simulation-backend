package parser;

import java.time.LocalDate;
import java.util.HashMap;

public class DayData implements Comparable<DayData> {
    public DayData() {
        countries = new HashMap<Integer, CountryData>();
        date = LocalDate.now();
    }

    /// Key - country id
    public HashMap<Integer, CountryData> countries;

    public LocalDate date;

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

    @Override
    public int compareTo(DayData other) {
        return this.date.compareTo(other.date);
    }

    public String toString() {
        var sb = new StringBuilder();
        sb.append("Date: " + date.toString() + '\n');

        for (var entry : countries.entrySet()) {
            sb.append(entry.getValue());
            sb.append('\n');
        }

        return sb.toString();
    }
}