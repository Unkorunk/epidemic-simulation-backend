package simulation;

import parser.Country;
import parser.CountryData;
import parser.DayData;
import parser.ParserData;

import java.util.ArrayList;

public class Simulation {
    public static int rateInf = 24;
    public static int rateDea = 317;
    public static int rateRec = 532;

    public static ParserData Predict(DayData dayData, int duration) {
        var parserData = new ParserData();
        parserData.source = "Simulation";
        parserData.data = new ArrayList<>();
        parserData.data.add(dayData);

        for (int i = 0; i < duration; i++) {
            var prevData = parserData.data.get(parserData.data.size() - 1);
            DayData newDay = new DayData();
            newDay.date = prevData.date.plusDays(1);
            for (var country_code : prevData.countries.keySet()) {
                var countryDataOld = prevData.countries.get(country_code);

                long infected = countryDataOld.infected + countryDataOld.infected / rateInf;
                long deaths = countryDataOld.deaths + countryDataOld.infected / rateDea;
                long recovered = countryDataOld.recovered + countryDataOld.infected / rateRec;

                var countryDataNew = new CountryData(
                        new Country(countryDataOld.countryCode, countryDataOld.countryName, countryDataOld.population),
                        infected, deaths, recovered
                );

                newDay.countries.put(country_code, countryDataNew);
            }
            parserData.data.add(newDay);
        }

        return parserData;
    }
}
