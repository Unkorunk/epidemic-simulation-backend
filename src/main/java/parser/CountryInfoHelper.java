package parser;

import org.geonames.Style;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

import java.io.*;
import java.util.HashMap;

public class CountryInfoHelper {
    public static int calls = 0;

    public static void Init() {
        WebService.setUserName("runolight");
        WebService.setGeoNamesServer("api.geonames.org");

        cachedResults = new HashMap<String, Country>();

        LoadCache();
    }

    private static HashMap<String, Country> cachedResults;

    public static void SaveCache() {
        System.out.println("Start saving cache. Processed " + calls + " calls");

        BufferedWriter bf = null;
        try {
            FileWriter fw = new FileWriter("CountryInfoHelperCache.txt");
            bf = new BufferedWriter(fw);

            bf.write("CountryInfoHelper cache" + '\n');

            for (var entry : cachedResults.entrySet()) {
                bf.write(entry.getKey() + '\n');
                if (entry.getValue() == null) {
                    for (int i = 0; i < 3; i++) {
                        bf.write("null\n");
                    }

                    continue;
                }

                bf.write(entry.getValue().countryCode + '\n');
                bf.write(entry.getValue().name + '\n');
                bf.write(Integer.toString(entry.getValue().population) + '\n');
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter" + ex);
            }
        }
    }

    public static Country GetCountryInfo(String countryName) {
        countryName = countryName.toLowerCase();
        countryName = countryName.replace(",", "");

        if (countryName.equals("kyrgyz republic")) {
            countryName = "kyrgyzstan";
        } else if (countryName.equals("us")) {
            countryName = "united states";
        } else if (countryName.equals("mainland china") || countryName.equals("hong kong sar")) {
            countryName = "china";
        } else if (countryName.equals("tanzania, united republic of")) {
            countryName = "tanzania";
        } else if (countryName.equals("macau") || countryName.equals("macao sar")) {
            countryName = "macao";
        } else if (countryName.equals("cape verde")) {
            countryName = "cabo verde";
        } else if (countryName.equals("eritrea")) {
            countryName = "eritrea";
        } else if (countryName.equals("northern ireland") || countryName.equals("north ireland") || countryName.equals("uk")) {
            countryName = "united kingdom";
        } else if (countryName.equals("czech republic")) {
            countryName = "czechia";
        } else if (countryName.equals("viet nam")) {
            countryName = "vietnam";
        } else if (countryName.equals("republic of korea") || countryName.equals("korea, south")) {
            countryName = "south korea";
        } else if (countryName.equals("taipei and environs")) {
            countryName = "taiwan";
        } else if (countryName.equals("congo (kinshasa)")) {
            countryName = "dr congo";
        } else if (countryName.equals("congo (brazzaville)") || countryName.equals("republic of congo") || countryName.equals("republic of the congo")) {
            countryName = "congo republic";
        } else if (countryName.equals("cote d'ivoire")) {
            countryName = "ivory coast";
        } else if (countryName.equals("curacao")) {
            countryName = "curaçao";
        } else if (countryName.equals("east timor")) {
            countryName = "timor-leste";
        } else if (countryName.equals("reunion")) {
            countryName = "réunion";
        }

        if (cachedResults.containsKey(countryName)) {
            if (cachedResults.get(countryName) == null) {
                return null;
            }

            return cachedResults.get(countryName).Clone();
        }

        ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
        searchCriteria.setName(countryName);
        searchCriteria.setNameEquals(countryName);
        searchCriteria.setStyle(Style.FULL);

        try {
            ToponymSearchResult searchResult = WebService.search(searchCriteria);
            var toponyms = searchResult.getToponyms();

            calls++;

            for (var toponym : toponyms) {
                if (toponym == null || toponym.getPopulation() == null || toponym.getCountryCode() == null || toponym.getCountryName() == null) {
                    continue;
                }

                Country res = new Country(toponym.getCountryCode(), toponym.getCountryName(), toponym.getPopulation());
                if (!res.name.toLowerCase().contains(countryName) && !countryName.contains(res.name.toLowerCase())) {
                    continue;
                }

                cachedResults.put(countryName, res);

                return res.Clone();
            }
        } catch (Exception e) {
            System.out.println(countryName);
            e.printStackTrace();
        }

        cachedResults.put(countryName, null);

        return null;
    }

    private static void LoadCache() {
        File txt = new File("CountryInfoHelperCache.txt");
        if (!txt.exists()) {
            return;
        }

        System.out.println("Load started");
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(txt);
            br = new BufferedReader(fr);

            br.readLine();

            String curStr = "";
            String curKey = null;
            String countryCode = null;
            String countryName = null;
            Integer population = null;

            while ((curStr = br.readLine()) != null) {
                if (curKey == null) {
                    curKey = curStr;
                } else if (countryCode == null) {
                    countryCode = curStr;
                } else if (countryName == null) {
                    countryName = curStr;
                } else if (population == null) {

                    if (curStr.equals("null")) {
                        cachedResults.put(curKey, null);
                    } else {
                        population = Integer.parseInt(curStr);
                        cachedResults.put(curKey, new Country(countryCode, countryName, population));
                    }

                    curKey = null;
                    countryCode = null;
                    countryName = null;
                    population = null;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    System.out.println("Load ended " + cachedResults.size());
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
