package simulation;

import parser.CountryData;
import parser.DayData;
import parser.ParserData;

import java.time.LocalDate;
import java.util.*;

public class SimpleSimulation implements Simulation {
    private List<ParserData> sources = new LinkedList<>();
    private DayData infoNow;
    private LocalDate dateNow;

    public SimpleSimulation(LocalDate startDate) {
        dateNow = startDate;
    }

    private class MyComparator implements Comparator<DayData> {
        @Override
        public int compare(DayData o1, DayData o2) {
            return (int) (o1.date.toEpochDay() - o2.date.toEpochDay());
        }
    }

    @Override
    public void Tick(int duration) {
        DayData infoNext = new DayData();
        LocalDate dateNext = dateNow.plusDays(duration);



    }

    @Override
    public void Reset() {

    }

    @Override
    public void AddInfo(ParserData source) {
        int length = source.data.size();
        for (int i = 1; i < length; i++) {
            var from = source.data.get(i - 1);
            var to = source.data.get(i);
            if (from.date.plusDays(1) != to.date) {
                var count = to.date.toEpochDay() - from.date.toEpochDay();
                for (int j = 1; j < count; j++) {
                    var dayInterpolated = new DayData();
                    dayInterpolated.date = from.date.plusDays(j);
                    for (var country_id : to.countries.keySet()) {
                        var countryFrom = from.countries.get(country_id);
                        if (countryFrom != null) {
                            var countryTo = to.countries.get(country_id);

                            var stepInfected = (countryTo.infected - countryFrom.infected) / (double) count;
                            var stepDeaths = (countryTo.deaths - countryFrom.deaths) / (double) count;
                            var stepRecovered = (countryTo.recovered - countryFrom.recovered) / (double) count;

                            var countryInterpolated = new CountryData();
                            countryInterpolated.countryId = countryFrom.countryId;
                            countryInterpolated.countryName = countryFrom.countryName;
                            countryInterpolated.infected = (int) Math.round(countryFrom.infected + j * stepInfected);
                            countryInterpolated.deaths = (int) Math.round(countryFrom.deaths + j * stepDeaths);
                            countryInterpolated.recovered = (int) Math.round(countryFrom.recovered + j * stepRecovered);
                            dayInterpolated.countries.put(countryInterpolated.countryId, countryInterpolated);
                        }
                    }
                    source.data.add(dayInterpolated);
                }
            }
        }
        source.data.sort(new MyComparator());

        sources.add(source);
    }

    @Override
    public DayData GetInfo() {


        return new DayData();
    }

    private void UpdateDate() {

    }
}
