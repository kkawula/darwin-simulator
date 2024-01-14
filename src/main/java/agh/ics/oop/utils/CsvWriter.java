package agh.ics.oop.utils;

import agh.ics.oop.presenter.StatsWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvWriter {
    private static final String COMMA = ",";
    private static final String DEFAULT_SEPARATOR = COMMA;
    private static final String DOUBLE_QUOTES = "\"";
    private static final String EMBEDDED_DOUBLE_QUOTES = "\"\"";
    private static final String NEW_LINE_UNIX = "\n";
    private static final String NEW_LINE_WINDOWS = "\r\n";
    private static List<String[]> list = new ArrayList<>();

    public CsvWriter() {
        String[] header = {"Day", "Animal alive", "Plants", "Dead animals", "Average age of deaths",
                "Average energy", "Average quantity of kids", "Free fileds"};
        list.add(header);
        for (String s : header) {
            System.out.print(s+',');
        }
        System.out.println();
    }

    public void saveFile() {
        String fileName = FileNameGenerator.generateFileName() + ".csv";
        try {
            Path resourcesPath = Paths.get(getClass().getClassLoader().getResource("").toURI());
            Path configFolder = resourcesPath.resolve("simulationData");
            Path filePath = configFolder.resolve(fileName).normalize().toAbsolutePath();
            writeToCsvFile(list, filePath);

        } catch (IOException | URISyntaxException exception) {
            System.out.println("nie zapisało się");
        }
    }

    private String convertToCsvFormat(final String[] line) {
        return convertToCsvFormat(line, DEFAULT_SEPARATOR);
    }

    private String convertToCsvFormat(final String[] line, final String separator) {
        return convertToCsvFormat(line, separator, true);
    }

    private String convertToCsvFormat(
            final String[] line,
            final String separator,
            final boolean quote) {

        return Stream.of(line)
                .map(l -> formatCsvField(l, quote))
                .collect(Collectors.joining(separator));

    }

    private String formatCsvField(final String field, final boolean quote) {

        String result = field;

        if (result.contains(COMMA)
                || result.contains(DOUBLE_QUOTES)
                || result.contains(NEW_LINE_UNIX)
                || result.contains(NEW_LINE_WINDOWS)) {

            result = result.replace(DOUBLE_QUOTES, EMBEDDED_DOUBLE_QUOTES);

        } else {
            if (quote) {
                result = result;
            }
        }

        return result;

    }

    private void writeToCsvFile(List<String[]> list, Path filePath) throws IOException {

        List<String> collect = list.stream()
                .map(this::convertToCsvFormat)
                .toList();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            for (String line : collect) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        }
    }

    public void addDayToCsv(StatsWriter stats) {
        String[] record = {String.valueOf(stats.getWorldLifespan()), String.valueOf(stats.getAnimalsAlive()),
                String.valueOf(stats.getGrass()), String.valueOf(stats.getAnimalsDead()),
                String.valueOf(stats.getAge()), String.valueOf(stats.getEnergy()),
                String.valueOf(stats.getChildren()), String.valueOf(stats.getFreeFields())};

        list.add(record);
    }
}
