package agh.ics.oop.presenter;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.simulation.WorldMap;
import agh.ics.oop.utils.ConfigurationData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class SimulationController {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private ConfigurationData config;
    private WorldMap worldMap;

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
    public void wire(ConfigurationData config, WorldMap worldMap) {
        this.config = config;
        this.worldMap = worldMap;
    }

    public void generateGrid() {
        int rows = config.getMapHeight();
        int columns = config.getMapWidth();
        GridPane grid = new GridPane();
        int size = Math.min(WIDTH/columns, HEIGHT/rows);

        List<Vector2d> animalsPositions = worldMap.getAnimalsPositions();
        List<Vector2d> grassesPositions = worldMap.getGrassesPositions();


        ColumnConstraints width = new ColumnConstraints(size);
        RowConstraints height = new RowConstraints(size);

        grid.setGridLinesVisible(true);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                GridPane cell = new GridPane();
                cell.setGridLinesVisible(true);
                cell.getColumnConstraints().add(width);
                cell.getRowConstraints().add(height);

                if (animalsPositions.contains(new Vector2d(col, row))) {
                    Label animal = new Label("A");
                    cell.add(animal, 0, 0);
                }
                if (grassesPositions.contains(new Vector2d(col, row))) {
                    cell.setStyle("-fx-background-color: #7CFC00;");
                }

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
