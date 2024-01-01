package agh.ics.oop.presenter;

import agh.ics.oop.utils.ConfigurationData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
public class SimulationController {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private ConfigurationData config;

    @FXML
    private VBox content;

    @FXML
    private Label mapWidthValue;

    @FXML
    private Label mapHeightValue;

    @FXML
    private Label animalsAliveValue;

    @FXML
    private void initialize() {
        setLabelValues();
    }
    public void wire(ConfigurationData config) {
        this.config = config;
    }

    public void generateGrid() {
        int rows = config.getMapHeight();
        int columns = config.getMapWidth();
        GridPane grid = new GridPane();
        int size = Math.min(WIDTH/columns, HEIGHT/rows);

        ColumnConstraints width = new ColumnConstraints(size);
        RowConstraints height = new RowConstraints(size);

        grid.setGridLinesVisible(true);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                GridPane cell = new GridPane();
                cell.setGridLinesVisible(true);
                cell.getColumnConstraints().add(width);
                cell.getRowConstraints().add(height);

                grid.add(cell, col, row);
            }
        }

        content.getChildren().add(grid);
    }


    @FXML
    private void startSimulation(){

    }
    @FXML void stopSimulation(){

    }
    @FXML void saveSimulation(){

    }

    public void setLabelValues() {

        mapWidthValue.setText("10");
        mapHeightValue.setText("12");
        animalsAliveValue.setText("12");

        // animalsDeadValue.setText(animalsDead);
        // plantsValue.setText(plants);
        // freeFieldsValue.setText(freeFields);
        // averageEnergyValue.setText(averageEnergy);
        // averageLifespanValue.setText(averageLifespan);
        // averageChildrenValue.setText(averageChildren);
        // birthdayValue.setText(birthday);
        // genomeValue.setText(genome);
        // activeGeneValue.setText(activeGene);
        // energyValue.setText(energy);
        // eatenPlantsValue.setText(eatenPlants);
        // childrenValue.setText(children);
        // descendantsValue.setText(descendants);
        // ageValue.setText(age);
        // directionValue.setText(direction);
    }

}
