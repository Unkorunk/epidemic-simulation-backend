package parser;

public class CountryData {
    public CountryData(Country country, int infected, int deaths, int recovered) {
        this.countryCode = country.countryCode;
        this.countryName = country.name;
        this.infected = infected;
        this.deaths = deaths;
        this.recovered = recovered;
        this.population = country.population;
    }

    public String countryCode;

    public String countryName;

    public int infected;
    public int deaths;
    public int recovered;

    public int population;

    public void Merge(CountryData cd) throws Exception {
        if (!countryCode.equals(cd.countryCode)) {
            throw new Exception("Can't merge countries with different ids");
        }

        this.infected = (this.infected + cd.infected) / 2;
        this.deaths = (cd.deaths + this.deaths) / 2;
        this.recovered = (cd.recovered + this.recovered) / 2;
    }

    public void Add(CountryData cd) throws Exception {
        if (!countryCode.equals(cd.countryCode)) {
            throw new Exception("Can't merge countries with different ids");
        }

        this.infected += cd.infected;
        this.deaths += cd.deaths;
        this.recovered += cd.recovered;

        this.population += cd.population;
    }

    public String toString() {
        return countryCode + " - " + countryName + ": " + infected + " " + deaths + " " + recovered;
    }
}
