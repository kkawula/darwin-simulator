package agh.ics.oop.utils;

import agh.ics.oop.presenter.SimulationStats;

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
    private static final List<String[]> list = new ArrayList<>();

    public CsvWriter() {
        String[] header = {"Day", "Animal alive", "Plants", "Dead animals", "Average age of deaths",
                "Average energy", "Average quantity of kids", "Free fields", "Best genes"};
        list.add(header);
    }

    public void saveFile() {
        String fileName = FileNameGenerator.generateFileName() + ".csv";
        try {
            Path resourcesPath = Paths.get(getClass().getClassLoader().getResource("simulationData").toURI());
            Path filePath = resourcesPath.resolve(fileName).normalize().toAbsolutePath();
            writeToCsvFile(list, filePath);

        } catch (IOException | URISyntaxException exception) {
            System.out.println("File didn't save properly.");
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
                .map(l -> formatCsvField(l))
                .collect(Collectors.joining(separator));

    }

    private String formatCsvField(final String field) {

        String result = field;

        if (result.contains(COMMA)
                || result.contains(DOUBLE_QUOTES)
                || result.contains(NEW_LINE_UNIX)
                || result.contains(NEW_LINE_WINDOWS)) {

            result = result.replace(DOUBLE_QUOTES, EMBEDDED_DOUBLE_QUOTES);

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

    public void addDayToCsv(SimulationStats simulationStats) {
        String[] record = {String.valueOf(simulationStats.getWorldLifespan()), String.valueOf(simulationStats.getAnimalsAlive()),
                String.valueOf(simulationStats.getGrass()), String.valueOf(simulationStats.getAnimalsDead()),
                String.valueOf(simulationStats.getAverageLifeLength()), String.valueOf(simulationStats.getAverageEnergy()),
                String.valueOf(simulationStats.getAverageChildrenNumber()), String.valueOf(simulationStats.getFreeFields()), String.valueOf(simulationStats.getBestGenes())};

        list.add(record);
    }
}
