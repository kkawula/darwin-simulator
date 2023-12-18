package agh.ics.oop.view;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class setUpController {
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

            String growthVariant;
            if (growVariant1.isSelected()) {
                growthVariant = growVariant1.getText();
            } else {
                growthVariant = growVariant2.getText();
            }

            int initialAnimals = Integer.parseInt(initialAnimalsField.getText());
            int initialAnimalEnergy = Integer.parseInt(initialAnimalEnergyField.getText());
            int fullnessThreshold = Integer.parseInt(fullnessThresholdField.getText());
            int parentEnergyConsumption = Integer.parseInt(parentEnergyConsumptionField.getText());
            int minMutations = Integer.parseInt(minMutationsField.getText());
            int maxMutations = Integer.parseInt(maxMutationsField.getText());
            int genomeLength = Integer.parseInt(genomeLengthField.getText());

            String behaviorVariant;
            if (behaviorVariant1.isSelected()) {
                behaviorVariant = behaviorVariant1.getText();
            } else {
                behaviorVariant = behaviorVariant2.getText();
            }

            System.out.println("Wysokość mapy: " + height);
            System.out.println("Szerokość mapy: " + width);
            System.out.println("Startowa liczba roślin: " + initialPlants);
            System.out.println("Energia zjedzenia jednej rośliny: " + plantEnergy);
            System.out.println("Liczba roślin wyrastająca każdego dnia: " + plantsPerDay);
            System.out.println("Wariant wzrostu roślin: " + growthVariant);
            System.out.println("Startowa liczba zwierzaków: " + initialAnimals);
            System.out.println("Startowa energia zwierzaków: " + initialAnimalEnergy);
            System.out.println("Energia konieczna, by uznać zwierzaka za najedzonego: " + fullnessThreshold);
            System.out.println("Energia rodziców zużywana by stworzyć potomka: " + parentEnergyConsumption);
            System.out.println("Minimalna liczba mutacji u potomków: " + minMutations);
            System.out.println("Maksymalna liczba mutacji u potomków: " + maxMutations);
            System.out.println("Długość genomu zwierzaków: " + genomeLength);
            System.out.println("Wariant zachowania zwierzaków: " + behaviorVariant);
        } catch (NumberFormatException e) {
            System.out.println("Wprowadzone dane nie są liczbami całkowitymi.");
        }
    }
}
