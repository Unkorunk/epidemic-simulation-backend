package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NCoV2019Parser extends Parser {
    public ParserData Parse() {
        System.out.println("Parsing started");
        var res = new ParserData();
        res.source = "ncov2019.live";
        var todayData = new DayData();

        Document doc = null;

        try {
            doc = Jsoup.connect("https://ncov2019.live/data").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (doc == null) {
            System.out.println("oh fuck");
            return res;
        }


        Element globalContainer = doc.getElementById("container_global");
        Elements rows = globalContainer.getElementsByTag("tr");

        for (var el : rows) {
            var country = el.getElementsByAttributeValue("data-type", "country");

            if (country.size() > 0) {
                String name = country.attr("data-name");
                var greenEls = el.getElementsByClass("text--green");
                int infected = Integer.parseInt(greenEls.eachText().get(0).replace(",", ""));

                var redEls = el.getElementsByClass("text--red");
                int deaths = Integer.parseInt(redEls.eachText().get(0).replace(",", ""));

                var blueEls = el.getElementsByClass("text--blue");
                int recovered = Integer.parseInt(blueEls.eachText().get(0).replace(",", ""));

                Country countryInfo = CountryInfoHelper.GetCountryInfo(name);
                if (countryInfo == null) {
                    continue;
                }

                var cData = new CountryData(countryInfo.countryCode, countryInfo.name, infected, deaths, recovered);
                todayData.countries.put(countryInfo.countryCode, cData);
            }
        }

        res.data.add(todayData);

        return res;
    }
}
