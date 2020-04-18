package parser;

public class Country {
    public String countryCode;
    public String name;
    public int population;

    public Country(String code, String name, int population) {
        this.countryCode = code;
        this.name = name;
        this.population = population;
    }

    public Country Clone() {
        return new Country(this.countryCode, this.name, this.population);
    }
}
