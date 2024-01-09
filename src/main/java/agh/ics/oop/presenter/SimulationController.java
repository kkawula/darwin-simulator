package agh.ics.oop.presenter;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.simulation.Simulation;
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


public class SimulationController {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private ConfigurationData config;
    private WorldMap worldMap;
    private Simulation simulation;

    @FXML
    private VBox content;

    @FXML
    private Label mapWidthValue;

    @FXML
    private Label mapHeightValue;

    @FXML
    private Label animalsAliveValue;

    @FXML
    private Label animalsDeadValue;

    @FXML
    private Label plantsValue;

    private int animalsAlive = 0;

    public void wire(ConfigurationData config, Simulation simulation) {
        this.config = config;
        this.simulation = simulation;
        this.worldMap = simulation.worldMap;
        animalsAlive = config.getInitialAnimals();
    }

    public void generateGrid() {
        content.getChildren().clear();

        int rows = config.getMapHeight();
        int columns = config.getMapWidth();
        GridPane grid = new GridPane();
        int size = Math.min(WIDTH/columns, HEIGHT/rows);

        HashMap<Vector2d, Integer> animalsPositions = worldMap.getAnimalsPositions();
        List<Vector2d> grassesPositions = worldMap.getGrassesPositions();

        animalsAlive = worldMap.getAliveAnimals().size();

        ColumnConstraints width = new ColumnConstraints(size);
        RowConstraints height = new RowConstraints(size);

        grid.setGridLinesVisible(true);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                GridPane cell = new GridPane();
                cell.setGridLinesVisible(true);
                cell.getColumnConstraints().add(width);
                cell.getRowConstraints().add(height);

                if (animalsPositions.keySet().contains(new Vector2d(col, row))) {
                    Label animal = new Label(animalsPositions.get(new Vector2d(col, row)) + "");
                    cell.add(animal, 0, 0);
                }
                if (grassesPositions.contains(new Vector2d(col, row))) {
                    cell.setStyle("-fx-background-color: #7CFC00;");
                }

                grid.add(cell, col, row);
            }
        }

        setLabelValues();
        content.getChildren().add(grid);
    }


    @FXML
    private void startSimulation(){
        simulation.start();
    }
    @FXML void stopSimulation(){
        simulation.stop();
    }
    @FXML void pauseSimulation(){
        simulation.pause();
    }

    public void setLabelValues() {

        mapWidthValue.setText(config.getMapWidth() + "");
        mapHeightValue.setText(config.getMapHeight() + "");
        animalsAliveValue.setText(animalsAlive + "");
        animalsDeadValue.setText(worldMap.getDeadAnimals().size() + "");
        plantsValue.setText(worldMap.getGrasses().size() + "");
//         freeFieldsValue.setText(freeFields);
//         averageEnergyValue.setText(averageEnergy);
//         averageLifespanValue.setText(averageLifespan);
//         averageChildrenValue.setText(averageChildren);

//         birthdayValue.setText(birthday);
//         genomeValue.setText(genome);
//         activeGeneValue.setText(activeGene);
//         energyValue.setText(energy);
//         eatenPlantsValue.setText(eatenPlants);
//         childrenValue.setText(children);
//         descendantsValue.setText(descendants);
//         ageValue.setText(age);
//         directionValue.setText(direction);
    }

}
