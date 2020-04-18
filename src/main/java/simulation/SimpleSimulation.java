package simulation;

import parser.CountryData;
import parser.DayData;
import parser.ParserData;

import java.time.LocalDate;
import java.util.*;

public class SimpleSimulation implements Simulation {
    private List<ParserData> sources = new LinkedList<>();
    private DayData infoNow;

    public SimpleSimulation() {
        infoNow = null;
    }

    public SimpleSimulation(LocalDate startDate) {
        infoNow = new DayData();
        infoNow.date = startDate;
    }

    private static class MyComparator implements Comparator<DayData> {
        @Override
        public int compare(DayData o1, DayData o2) {
            return (int) (o1.date.toEpochDay() - o2.date.toEpochDay());
        }
    }

    @Override
    public void Tick(int duration) {
        DayData infoNext = new DayData();
        if (infoNow == null) {
            if (sources.size() == 0) throw new RuntimeException();
            infoNext.date = LocalDate.MAX;
            for (var parsedData : sources) {
                infoNext.date = LocalDate.ofEpochDay(Math.min(infoNext.date.toEpochDay(), parsedData.data.get(0).date.toEpochDay()));
            }
        } else {
            infoNext.date = infoNow.date.plusDays(duration);
        }

        int count = 0;
        for (var parserData : sources) {
            if (parserData.data.get(0).date.isAfter(infoNext.date)
                    || parserData.data.get(parserData.data.size() - 1).date.isBefore(infoNext.date)) continue;

            DayData foundDay = null;
            for (int i = 0; i < parserData.data.size(); i++) {
                if (parserData.data.get(i).date == infoNext.date) {
                    foundDay = parserData.data.get(i);
                    break;
                }
            }
            if (foundDay == null) throw new RuntimeException();

            for(var country_id : foundDay.countries.keySet()) {
                var countryFound = foundDay.countries.get(country_id);
                var countryNext = infoNext.countries.get(country_id);
                if (countryNext == null) {
                    countryNext = new CountryData();
                    countryNext.countryName = countryFound.countryName;
                    countryNext.countryId = countryFound.countryId;
                    countryNext.infected = countryFound.infected;
                    countryNext.recovered = countryFound.recovered;
                    countryNext.deaths = countryFound.deaths;
                } else {
                    countryNext.infected += countryFound.infected;
                    countryNext.recovered += countryFound.recovered;
                    countryNext.deaths += countryFound.deaths;
                }
                infoNext.countries.put(country_id, countryNext);
            }

            count++;
        }

        for (var country_id : infoNext.countries.keySet()) {
            var country = infoNext.countries.get(country_id);
            country.infected = (int) Math.round(country.infected / (double)count);
            country.recovered = (int) Math.round(country.recovered / (double)count);
            country.deaths = (int) Math.round(country.deaths / (double)count);
        }

        infoNow = infoNext;
    }

    @Override
    public void Reset() {
        sources.clear();
        infoNow = null;
    }

    @Override
    public void Reset(LocalDate startDate) {
        sources.clear();
        infoNow = new DayData();
        infoNow.date = startDate;
    }

    @Override
    public void AddInfo(ParserData source) {
        if (source.data.size() == 0) return;

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
        if (infoNow == null) throw new RuntimeException();

        return infoNow;
    }
}
