package agh.ics.oop.presenter;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.WorldMap;
import agh.ics.oop.utils.ConfigurationData;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    @FXML
    private Label freeFieldsValue;

    @FXML
    private Label averageEnergyValue;

    @FXML
    private Label averageLifespanValue;

    @FXML
    private Label averageChildrenValue;

    @FXML
    private Label worldLifespanValue;

    @FXML
    private Label birthdayValue;

    @FXML
    private Label genomeValue;

    @FXML
    private Label activeGeneValue;

    @FXML
    private Label energyValue;

    @FXML
    private Label eatenPlantsValue;

    @FXML
    private Label childrenValue;

    @FXML
    private Label descendantsValue;

    @FXML
    private Label ageValue;

    @FXML
    private Label positionValue;

    @FXML
    private VBox animalStats;

    private StatsWriter statsWriter;


    public void wire(ConfigurationData config, Simulation simulation) {
        this.config = config;
        this.simulation = simulation;
        this.worldMap = simulation.worldMap;
        statsWriter = new StatsWriter(worldMap);
    }



    public void generateGrid() {
        content.getChildren().clear();

        int rows = config.getMapHeight();
        int columns = config.getMapWidth();
        GridPane grid = new GridPane();
        int size = Math.min(WIDTH/columns, HEIGHT/rows);

        HashMap<Vector2d, Integer> animalsPositions = worldMap.getAnimalsPositions();
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
                cell.setOnMouseClicked(event -> {
                    Node source = (Node)event.getSource();
                    int rowIndex = GridPane.getRowIndex(source);
                    int columnIndex = GridPane.getColumnIndex(source);

                    if (!worldMap.getAnimals().get(new Vector2d(columnIndex, rowIndex)).isEmpty()) {
                        statsWriter.setAnimal(worldMap.getAnimals().get(new Vector2d(columnIndex, rowIndex)).last());
                        animalStats.setVisible(true);
                        cell.setStyle("-fx-background-color: #c42828;");
                    }
                });

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
        if (statsWriter.isFollowed()) {
            int rowIndex = statsWriter.getPosition().getY();
            int columnIndex = statsWriter.getPosition().getX();
            GridPane cell = (GridPane) grid.getChildren().get(rowIndex * columns + columnIndex + 1);
            cell.setStyle("-fx-background-color: #c42828;");
        }

        content.getChildren().add(grid);
    }


    @FXML
    private void startSimulation() {
        simulation.start();
    }
    @FXML void shutDownSimulation() {
        simulation.shutDown();
    }
    @FXML void pauseSimulation() {
        simulation.pause();
    }
    @FXML
    void stopFollowingAnimal() {
        statsWriter.unFollowAnimal();
        animalStats.setVisible(false);

    }

    public void updateStats() {
        statsWriter.updateStats();
        setLabelValues();
    }

    public void setLabelValues() {

        mapWidthValue.setText(config.getMapWidth() + "");
        mapHeightValue.setText(config.getMapHeight() + "");
        animalsAliveValue.setText(statsWriter.getAnimalsAlive() + "");
        animalsDeadValue.setText(statsWriter.getAnimalsDead() + "");
        plantsValue.setText(worldMap.getGrasses().size() + "");
        freeFieldsValue.setText(statsWriter.getFreeFields() + "");
        averageEnergyValue.setText(statsWriter.getAverageEnergy() + "");
        averageLifespanValue.setText(statsWriter.getAverageLifeLength() + "");
        averageChildrenValue.setText(statsWriter.getAverageChildrenNumber() + "");
        worldLifespanValue.setText(statsWriter.getWorldLifespan() + "");

        birthdayValue.setText(statsWriter.getBrithday() + "");
        genomeValue.setText(statsWriter.getGenome() + "");
        activeGeneValue.setText(statsWriter.getActiveGene() + "");
        energyValue.setText(statsWriter.getEnergy() + "");
        eatenPlantsValue.setText(statsWriter.getEatenPlants() + "");
        childrenValue.setText(statsWriter.getChildren() + "");
        descendantsValue.setText(statsWriter.getDescendants() + "");
        ageValue.setText(statsWriter.getAge() + "");
        positionValue.setText(statsWriter.getPosition() + "");
    }

}
