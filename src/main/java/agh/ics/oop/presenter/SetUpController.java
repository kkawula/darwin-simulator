package agh.ics.oop.presenter;

import agh.ics.oop.utils.ConfigurationData;
import agh.ics.oop.utils.FileNameGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SetUpController {
    @FXML
    private TextField heightField;

    @FXML
    private TextField widthField;

    @FXML
    private TextField initialPlantsField;

    @FXML
    private TextField plantEnergyField;

    @FXML
    private TextField plantsPerDayField;

    @FXML
    private RadioButton growVariant1;

    @FXML
    private RadioButton growVariant2;

    @FXML
    private TextField initialAnimalsField;

    @FXML
    private TextField initialAnimalEnergyField;

    @FXML
    private TextField fullnessThresholdField;

    @FXML
    private TextField parentEnergyConsumptionField;

    @FXML
    private TextField minMutationsField;

    @FXML
    private TextField maxMutationsField;

    @FXML
    private TextField genomeLengthField;

    @FXML
    private RadioButton behaviorVariant1;

    @FXML
    private RadioButton behaviorVariant2;

    @FXML
    private void saveData() {
        try {
            int height = Integer.parseInt(heightField.getText());
            int width = Integer.parseInt(widthField.getText());
            int initialPlants = Integer.parseInt(initialPlantsField.getText());
            int plantEnergy = Integer.parseInt(plantEnergyField.getText());
            int plantsPerDay = Integer.parseInt(plantsPerDayField.getText());

            int growthVariant;
            if (growVariant1.isSelected()) {
                growthVariant = 1;
            } else {
                growthVariant = 2;
            }

            int initialAnimals = Integer.parseInt(initialAnimalsField.getText());
            int initialAnimalEnergy = Integer.parseInt(initialAnimalEnergyField.getText());
            int fullnessThreshold = Integer.parseInt(fullnessThresholdField.getText());
            int parentEnergyConsumption = Integer.parseInt(parentEnergyConsumptionField.getText());
            int minMutations = Integer.parseInt(minMutationsField.getText());
            int maxMutations = Integer.parseInt(maxMutationsField.getText());
            int genomeLength = Integer.parseInt(genomeLengthField.getText());

            int behaviorVariant;
            if (behaviorVariant1.isSelected()) {
                behaviorVariant = 1;
            } else {
                behaviorVariant = 2;
            }

            ConfigurationData configurationData = new ConfigurationData(height, width, initialPlants, plantEnergy, plantsPerDay, growthVariant, initialAnimals, initialAnimalEnergy, fullnessThreshold, parentEnergyConsumption, minMutations, maxMutations, genomeLength, behaviorVariant);
            // Send configurationData to the simulation

            System.out.println("Map height: " + height);
            System.out.println("Map width: " + width);
            System.out.println("Initial number of plants: " + initialPlants);
            System.out.println("Energy gained from eating one plant: " + plantEnergy);
            System.out.println("Number of plants growing each day: " + plantsPerDay);
            System.out.println("Plant growth variant: " + growthVariant);
            System.out.println("Initial number of animals: " + initialAnimals);
            System.out.println("Initial energy of animals: " + initialAnimalEnergy);
            System.out.println("Energy required to consider an animal well-fed: " + fullnessThreshold);
            System.out.println("Energy consumed by parents to create offspring: " + parentEnergyConsumption);
            System.out.println("Minimum number of mutations in offspring: " + minMutations);
            System.out.println("Maximum number of mutations in offspring: " + maxMutations);
            System.out.println("Length of animals' genome: " + genomeLength);
            System.out.println("Behavior variant of animals: " + behaviorVariant);
        } catch (NumberFormatException e) {
            System.out.println("The entered data is not an integer.");
        }
    }

    @FXML
    private void loadDataFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file with configuration");
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            loadConfigurationFromFile(selectedFile.getAbsolutePath());
        }
    }

    public void loadConfigurationFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseAndSetFieldValues(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseAndSetFieldValues(String line) {
        String[] parts = line.split(":");
        if (parts.length == 2) {
            String fieldName = parts[0].trim();
            String value = parts[1].trim();

            switch (fieldName) {
                case "Map height":
                    heightField.setText(value);
                    break;
                case "Map width":
                    widthField.setText(value);
                    break;
                case "Initial number of plants":
                    initialPlantsField.setText(value);
                    break;
                case "Energy gained from eating one plant":
                    plantEnergyField.setText(value);
                    break;
                case "Number of plants growing each day":
                    plantsPerDayField.setText(value);
                    break;
                case "Plant growth variant":
                    if (value.equals("1")) {
                        growVariant1.setSelected(true);
                        growVariant2.setSelected(false);
                    } else if (value.equals("2")) {
                        growVariant1.setSelected(false);
                        growVariant2.setSelected(true);
                    }
                    break;
                case "Initial number of animals":
                    initialAnimalsField.setText(value);
                    break;
                case "Initial energy of animals":
                    initialAnimalEnergyField.setText(value);
                    break;
                case "Energy required to consider an animal well-fed":
                    fullnessThresholdField.setText(value);
                    break;
                case "Energy consumed by parents to create offspring":
                    parentEnergyConsumptionField.setText(value);
                    break;
                case "Minimum number of mutations in offspring":
                    minMutationsField.setText(value);
                    break;
                case "Maximum number of mutations in offspring":
                    maxMutationsField.setText(value);
                    break;
                case "Length of animals' genome":
                    genomeLengthField.setText(value);
                    break;
                case "Behavior variant of animals":
                    if (value.equals("1")) {
                        behaviorVariant1.setSelected(true);
                        behaviorVariant2.setSelected(false);
                    } else if (value.equals("2")) {
                        behaviorVariant1.setSelected(false);
                        behaviorVariant2.setSelected(true);
                    }
                    break;
            }
        }
    }

    @FXML
    private void saveDataToText() {
        String fileName = FileNameGenerator.generateFileName();

        try {
            Path resourcesPath = Paths.get(getClass().getClassLoader().getResource("").toURI());
            Path configFolder = resourcesPath.resolve("config");
            Path filePath = configFolder.resolve(fileName).normalize().toAbsolutePath();

            Map<String, String> data = new HashMap<>();
            data.put("Map height", heightField.getText());
            data.put("Map width", widthField.getText());
            data.put("Initial number of plants", initialPlantsField.getText());
            data.put("Plant growth variant", growVariant1.isSelected() ? "1" : "2");
            data.put("Initial number of animals", initialAnimalsField.getText());
            data.put("Initial energy of animals", initialAnimalEnergyField.getText());
            data.put("Energy required to consider an animal well-fed", fullnessThresholdField.getText());
            data.put("Energy consumed by parents to create offspring", parentEnergyConsumptionField.getText());
            data.put("Minimum number of mutations in offspring", minMutationsField.getText());
            data.put("Maximum number of mutations in offspring", maxMutationsField.getText());
            data.put("Length of animals' genome", genomeLengthField.getText());
            data.put("Behavior variant of animals", behaviorVariant1.isSelected() ? "1" : "2");

            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue() + System.lineSeparator());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("The data has been saved to a file: " + filePath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
