package parser;

public class CountryData {
    public CountryData(int id, String countryName, int infected, int deaths, int recovered) {
        this.countryId = id;
        this.countryName = countryName;
        this.infected = infected;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public int countryId;

    public String countryName;

    public int infected;
    public int deaths;
    public int recovered;

    public void Merge(CountryData cd) throws Exception {
        if (countryId != cd.countryId) {
            throw new Exception("Can't merge countries with different ids");
        }

        this.infected += cd.infected;
        this.deaths += cd.deaths;
        this.recovered += cd.recovered;
    }

    public String toString() {
        return countryId + " - " + countryName + ": " + infected + " " + deaths + " " + recovered;
    }
}
