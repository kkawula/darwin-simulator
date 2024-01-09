package agh.ics.oop.presenter;

import agh.ics.oop.model.Behavior;
import agh.ics.oop.model.BehaviorVariant;
import agh.ics.oop.model.GrowthVariant;
import agh.ics.oop.utils.ConfigurationData;
import agh.ics.oop.utils.FileNameGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SetupController {
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
    private TextField minEnergyToReproduceField;

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
    private TextField movingCostField;

    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        growVariant1.setToggleGroup(group);
        growVariant2.setToggleGroup(group);

        ToggleGroup group2 = new ToggleGroup();
        behaviorVariant1.setToggleGroup(group2);
        behaviorVariant2.setToggleGroup(group2);

        Path resourcesPath = null;
        try {
            resourcesPath = Paths.get(getClass().getClassLoader().getResource("").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Path configFolder = resourcesPath.resolve("config");
        Path filePath = configFolder.resolve("default.txt").normalize().toAbsolutePath();
        loadConfigurationFromFile(String.valueOf(filePath));

    }

    @FXML
    private void saveData() {
        try {
            int height = Integer.parseInt(heightField.getText());
            int width = Integer.parseInt(widthField.getText());
            int initialPlants = Integer.parseInt(initialPlantsField.getText());
            int plantEnergy = Integer.parseInt(plantEnergyField.getText());
            int plantsPerDay = Integer.parseInt(plantsPerDayField.getText());

            GrowthVariant growthVariant;
            if (growVariant1.isSelected()) {
                growthVariant = GrowthVariant.FORESTED_EQUATOR;
            } else {
                growthVariant = GrowthVariant.LIFE_GIVING_CORPSES;
            }

            int initialAnimals = Integer.parseInt(initialAnimalsField.getText());
            int initialAnimalEnergy = Integer.parseInt(initialAnimalEnergyField.getText());
            int minEnergyToReproduce = Integer.parseInt(minEnergyToReproduceField.getText());
            int parentEnergyConsumption = Integer.parseInt(parentEnergyConsumptionField.getText());
            int minMutations = Integer.parseInt(minMutationsField.getText());
            int maxMutations = Integer.parseInt(maxMutationsField.getText());
            int genomeLength = Integer.parseInt(genomeLengthField.getText());

            BehaviorVariant behaviorVariant;
            if (behaviorVariant1.isSelected()) {
                behaviorVariant = BehaviorVariant.PREDESTINATION_BEHAVIOR;
            } else {
                behaviorVariant = BehaviorVariant.TRAVERSAL_BEHAVIOR;
            }
            int movingCost = Integer.parseInt(movingCostField.getText());

            ConfigurationData configurationData = new ConfigurationData(height, width, initialPlants, plantEnergy, plantsPerDay, growthVariant, initialAnimals, initialAnimalEnergy, minEnergyToReproduce, parentEnergyConsumption, minMutations, maxMutations, genomeLength, behaviorVariant, movingCost);

            new SimulationLauncher().openNewWindow(configurationData);

            System.out.println("Map height: " + height);
            System.out.println("Map width: " + width);
            System.out.println("Initial plant count: " + initialPlants);
            System.out.println("Plant energy gain: " + plantEnergy);
            System.out.println("Plants per day: " + plantsPerDay);
            System.out.println("Plant growth variant: " + growthVariant);
            System.out.println("Initial animal count: " + initialAnimals);
            System.out.println("Initial animal energy: " + initialAnimalEnergy);
            System.out.println("Min energy to reproduce: " + minEnergyToReproduce);
            System.out.println("Parent energy consumption: " + parentEnergyConsumption);
            System.out.println("Min mutations in offspring: " + minMutations);
            System.out.println("Max mutations in offspring: " + maxMutations);
            System.out.println("Genome length: " + genomeLength);
            System.out.println("Animal behavior variant: " + behaviorVariant);
            System.out.println("Moving cost: " + movingCost);

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
                case "Energy from eating one plant":
                    plantEnergyField.setText(value);
                    break;
                case "Plants growing each day":
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
                case "Min energy to reproduce":
                    minEnergyToReproduceField.setText(value);
                    break;
                case "Energy used for reproduction":
                    parentEnergyConsumptionField.setText(value);
                    break;
                case "Minimum mutations":
                    minMutationsField.setText(value);
                    break;
                case "Maximum mutations":
                    maxMutationsField.setText(value);
                    break;
                case "Genome length":
                    genomeLengthField.setText(value);
                    break;
                case "Animal behavior variant":
                    if (value.equals("1")) {
                        behaviorVariant1.setSelected(true);
                        behaviorVariant2.setSelected(false);
                    } else if (value.equals("2")) {
                        behaviorVariant1.setSelected(false);
                        behaviorVariant2.setSelected(true);
                    }
                    break;
                case "Moving cost":
                    movingCostField.setText(value);
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
            data.put("Min energy to reproduce", minEnergyToReproduceField.getText());
            data.put("Energy used for reproduction", parentEnergyConsumptionField.getText());
            data.put("Minimum mutations", minMutationsField.getText());
            data.put("Maximum mutations", maxMutationsField.getText());
            data.put("Genome length", genomeLengthField.getText());
            data.put("Animal behavior variant", behaviorVariant1.isSelected() ? "1" : "2");
            data.put("Moving cost", movingCostField.getText());


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
