package parser;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JHUCSSEParser extends Parser {
    @Override
    public ParserData Parse() {
        File repDir = new File("data/");
        if (!repDir.exists()) {
            if (!repDir.mkdir()) {
                return null;
            }

            System.out.println("Created " + repDir.getPath() + " dir");
        }

        File gitRep = new File(repDir.getPath() + "/.git");
        Git rep = null;
        if (!gitRep.exists()) {
            try {
                System.out.println("Clonning");
                rep = Git.cloneRepository()
                        .setURI("https://github.com/CSSEGISandData/COVID-19.git")
                        .setDirectory(repDir).call();
            } catch (GitAPIException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                rep = Git.open(gitRep);
                rep.pull().call();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        File dataDir = new File(repDir.getPath() + "/csse_covid_19_data/csse_covid_19_daily_reports");

        File[] csvs = dataDir.listFiles();

        if (csvs == null) {
            return null;
        }

        var result = new ParserData();
        result.source = "https://github.com/CSSEGISandData/COVID-19";

        var formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        for (var csv : csvs) {
            if (!FilenameUtils.getExtension(csv.getName()).equals("csv")) {
                continue;
            }

            var curDay = new DayData();

            var dateString = csv.getName().substring(0, csv.getName().length() - 4);
            curDay.date = LocalDate.parse(dateString, formatter);

            System.out.println("Date: " + curDay.date);

            CSVReader csvReader = null;
            try {
                var fileReader = new FileReader(csv);
                csvReader = new CSVReaderBuilder(fileReader).build();

                String[] headerLine = csvReader.readNext();
                int countryColumn = -1;
                int confirmedColumn = -1;
                int recoveredColumn = -1;
                int deathColumn = -1;

                for (int i = 0; i < headerLine.length; i++) {
                    if (headerLine[i].toLowerCase().contains("country")) {
                        countryColumn = i;
                    } else if (headerLine[i].toLowerCase().contains("confirmed")) {
                        confirmedColumn = i;
                    } else if (headerLine[i].toLowerCase().contains("deaths")) {
                        deathColumn = i;
                    } else if (headerLine[i].toLowerCase().contains("recovered")) {
                        recoveredColumn = i;
                    }
                }

                if (countryColumn == -1 || confirmedColumn == -1 || recoveredColumn == -1 || deathColumn == -1) {
                    System.out.println("FUCK YOU TABLEMAKER " + csv.getPath());
                }

                String[] line = null;
                while ((line = csvReader.readNext()) != null) {
                    Country curCountry = CountryInfoHelper.GetCountryInfo(line[countryColumn]);

                    if (line[confirmedColumn].equals("")) {
                        line[confirmedColumn] = "0";
                    }
                    if (line[deathColumn].equals("")) {
                        line[deathColumn] = "0";
                    }
                    if (line[recoveredColumn].equals("")) {
                        line[recoveredColumn] = "0";
                    }

                    int infected = Integer.parseInt(line[confirmedColumn]);
                    int deaths = Integer.parseInt(line[deathColumn]);
                    int recovered = Integer.parseInt(line[recoveredColumn]);

                    if (curCountry == null) {
                        curDay.AddCountry(new CountryData("Other", "Other", infected, deaths, recovered));
                        continue;
                    }

                    var countryData = new CountryData(curCountry.countryCode, curCountry.name, infected, deaths, recovered);

                    curDay.AddCountry(countryData);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (CsvValidationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (csvReader != null) {
                    try {
                        csvReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            result.data.add(curDay);
        }

        result.Sort();

        CountryInfoHelper.SaveCache();
        return result;
    }

}
