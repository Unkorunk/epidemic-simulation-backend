package parser;

public class CountryData {
    public CountryData(String code, String countryName, int infected, int deaths, int recovered) {
        this.countryCode = code;
        this.countryName = countryName;
        this.infected = infected;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public String countryCode;

    public String countryName;

    public int infected;
    public int deaths;
    public int recovered;

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
    }

    public String toString() {
        return countryCode + " - " + countryName + ": " + infected + " " + deaths + " " + recovered;
    }
}
