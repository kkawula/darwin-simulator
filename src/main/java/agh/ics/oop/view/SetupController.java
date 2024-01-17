package agh.ics.oop.view;

import agh.ics.oop.model.BehaviorVariant;
import agh.ics.oop.model.GrowthVariant;
import agh.ics.oop.utils.ConfigurationData;
import agh.ics.oop.utils.FileNameGenerator;
import agh.ics.oop.utils.SimulationEngine;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SetupController {

    @FXML
    private TextField heightField, widthField, initialPlantsField, plantEnergyField, plantsPerDayField, initialAnimalsField, initialAnimalEnergyField, minEnergyToReproduceField, parentEnergyConsumptionField, minMutationsField, maxMutationsField, genomeLengthField, movingCostField, refreshTimeField;

    @FXML
    public RadioButton csv0, csv1;

    @FXML
    private RadioButton growVariant1, growVariant2;

    @FXML
    private RadioButton behaviorVariant1, behaviorVariant2;

    private final SimulationEngine simulationEngine = new SimulationEngine();


    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        growVariant1.setToggleGroup(group);
        growVariant2.setToggleGroup(group);

        ToggleGroup group2 = new ToggleGroup();
        behaviorVariant1.setToggleGroup(group2);
        behaviorVariant2.setToggleGroup(group2);

        ToggleGroup group3 = new ToggleGroup();
        csv1.setToggleGroup(group3);
        csv0.setToggleGroup(group3);
        addValidationListener(heightField, 1, 150);
        addValidationListener(widthField, 1, 150);
        addValidationListener(initialPlantsField, 0, 1000);
        addValidationListener(plantEnergyField, 0, 10000);
        addValidationListener(plantsPerDayField, 0, 10000);
        addValidationListener(initialAnimalsField, 0, 1000);
        addValidationListener(initialAnimalEnergyField, 0, 10000);
        addValidationListener(minEnergyToReproduceField, 0, 10000);
        addValidationListener(parentEnergyConsumptionField, 0, 10000);
        addValidationListener(minMutationsField, 0, 32);
        addValidationListener(maxMutationsField, 0, 32);
        addValidationListener(genomeLengthField, 1, 32);
        addValidationListener(movingCostField, 0, 10000);
        addValidationListener(refreshTimeField, 0, 10000);
        loadConfigurationFromFile("Jungle.txt");

    }

    private void addValidationListener(TextField field, int min, int max) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !isValidIntegerInput(newValue, min, max)) {
                field.setText(oldValue);
            }
        });
    }

    private void showAlert(String validationMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation error");
        alert.setHeaderText(null);
        alert.setContentText(validationMessage);
        alert.showAndWait();
    }

    @FXML
    private void saveData() {

        if (heightField.getText().isEmpty() || widthField.getText().isEmpty() || initialPlantsField.getText().isEmpty() ||
                plantEnergyField.getText().isEmpty() || plantsPerDayField.getText().isEmpty() || initialAnimalsField.getText().isEmpty() ||
                initialAnimalEnergyField.getText().isEmpty() || minEnergyToReproduceField.getText().isEmpty() ||
                parentEnergyConsumptionField.getText().isEmpty() || minMutationsField.getText().isEmpty() ||
                maxMutationsField.getText().isEmpty() || genomeLengthField.getText().isEmpty() ||
                movingCostField.getText().isEmpty() || refreshTimeField.getText().isEmpty()) {
            showAlert("Nie wypelniles wszystkich pol!");
            return;
        }
        else if(Integer.parseInt(maxMutationsField.getText())>Integer.parseInt(genomeLengthField.getText()) ||
                Integer.parseInt(maxMutationsField.getText())<Integer.parseInt(minMutationsField.getText()))
        {
            showAlert("Zle wypelniles opcje mutacji");
            return;
        }

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
            int writeCsv;
            if (csv1.isSelected()) {
                writeCsv = 1;
            } else {
                writeCsv = 0;
            }

            int refreshTime = Integer.parseInt(refreshTimeField.getText());
            ConfigurationData configurationData = new ConfigurationData(height, width, initialPlants, plantEnergy, plantsPerDay, growthVariant, initialAnimals, initialAnimalEnergy, minEnergyToReproduce, parentEnergyConsumption, minMutations, maxMutations, genomeLength, behaviorVariant, movingCost, writeCsv, refreshTime);

            simulationEngine.start(configurationData);

        } catch (NumberFormatException e) {
            System.out.println("The entered data is not an integer.");
        }
    }

    @FXML
    private void loadDataFromFile() {
        Path configFolderPath = null;
        List<String> fileNames = new ArrayList<>();

        try {
            Path resourcesPath = Paths.get(getClass().getClassLoader().getResource("").toURI());
            configFolderPath = resourcesPath.resolve("config");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Files.walk(configFolderPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .forEach(path -> fileNames.add(path.getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(fileNames.get(0), fileNames);
        dialog.setTitle("Config chooser");
        dialog.setHeaderText("Choose config file");
        dialog.setContentText("Available configs:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::loadConfigurationFromFile);

    }

    public void loadConfigurationFromFile(String fileName) {
        String path = "config/" + fileName;
        String filePath = "";
        try {
            filePath = Paths.get(getClass().getClassLoader().getResource(path).toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

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
                case "Csv writing":
                    if (value.equals("1")) {
                        csv1.setSelected(true);
                        csv0.setSelected(false);
                    } else if (value.equals("0")) {
                        csv1.setSelected(false);
                        csv0.setSelected(true);
                    }
                    break;
                case "Refresh time":
                    refreshTimeField.setText(value);
                    break;
            }
        }
    }

    @FXML
    private void saveDataToText() {
        String fileName = FileNameGenerator.generateFileName() + ".txt";

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
            data.put("Csv writing", csv1.isSelected() ? "1" : "0");
            data.put("Refresh time", refreshTimeField.getText());
            data.put("Energy from eating one plant", plantEnergyField.getText());
            data.put("Plants growing each day", plantsPerDayField.getText());


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

    private boolean isValidIntegerInput(String input, int min, int max) {
        try {
            int value = Integer.parseInt(input);
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
