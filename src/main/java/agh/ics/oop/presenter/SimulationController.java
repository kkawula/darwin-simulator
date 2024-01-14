package agh.ics.oop.presenter;

import agh.ics.oop.model.Grass;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.WorldMap;
import agh.ics.oop.utils.ConfigurationData;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class SimulationController {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    private ConfigurationData config;
    private WorldMap worldMap;
    private Simulation simulation;

    private Vector2d followedAnimalPosition;

    @FXML
    private VBox content;

    @FXML
    private Label mapWidthValue, mapHeightValue, animalsAliveValue,
            animalsDeadValue, plantsValue, freeFieldsValue,
            averageEnergyValue, averageLifespanValue,
            averageChildrenValue, worldLifespanValue, birthdayValue,
            genomeValue, activeGeneValue,energyValue, eatenPlantsValue,
            childrenValue, descendantsValue, ageValue,
            positionValue, bestGenesValue, deathDayValue;

    @FXML
    private VBox animalStats;
    private GridPane grid;

    private StatsWriter statsWriter;
    private List<Vector2d> grassesPositions;
    private HashMap<Vector2d, Integer> animalsPositions;
    private LinkedList<Vector2d> deadAnimalsPositions = new LinkedList<>();
    private int cellSize;

    public void init(Simulation simulation) {
        this.simulation = simulation;
        this.config = simulation.config;
        this.worldMap = simulation.worldMap;
        this.statsWriter = simulation.statsWriter;

        generateGrid();
    }

    public Circle createEnergyCircle(double energy) {
        double averageEnergy = statsWriter.getAverageEnergy();
        double distanceFromAverage = energy - averageEnergy;
        double shift = distanceFromAverage / averageEnergy;
        double saturation = Math.max(0.0, Math.min(1.0, 0.5 + shift));
        Color color = Color.hsb(0, saturation, 1.0);

        Circle circle = new Circle((cellSize / 2) * 0.7);
        circle.setFill(color);

        return circle;
    }

    private void fillCell(int col, int row) {
        Vector2d vector = new Vector2d(col, row);
        GridPane cell = (GridPane) grid.getChildren().get(row * config.getMapWidth() + col);
        cell.getChildren().clear();

        switch (config.getGrowthVariant()) {
            case FORESTED_EQUATOR -> {
                if (row >= config.getMapHeight() * 0.4 && row <= config.getMapHeight() * 0.6) {
                    cell.setStyle("-fx-background-color: #006400;");
                }
                else{
                    cell.setStyle("-fx-background-color: #8B4513;");
                }
            }
            case LIFE_GIVING_CORPSES -> {
                if (deadAnimalsPositions.contains(new Vector2d(col, row))) {
                    cell.setStyle("-fx-background-color: #654321;");
                }
                else{
                    cell.setStyle("-fx-background-color: #8B4513;");
                }
            }
        }

        if (animalsPositions.containsKey(vector)) {
            Circle animal = createEnergyCircle(animalsPositions.get(vector));
            cell.add(animal, 0, 0);
            GridPane.setHalignment(animal, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(animal, javafx.geometry.VPos.CENTER);

        }
        if (grassesPositions.contains(vector)) {
            cell.setStyle("-fx-background-color: #228B22;");
        }
    }

    private void setNewFollowedAnimalPosition(Vector2d newFollowedAnimalPosition){
        if (Objects.nonNull(followedAnimalPosition)) {
            int row = followedAnimalPosition.getY();
            int col = followedAnimalPosition.getX();
            fillCell(col, row);
        }

        statsWriter.setAnimal(newFollowedAnimalPosition);
        animalStats.setVisible(true);
        int row = newFollowedAnimalPosition.getY();
        int col = newFollowedAnimalPosition.getX();
        GridPane cell = (GridPane) grid.getChildren().get(row * config.getMapWidth() + col);
        Circle animal = (Circle) cell.getChildren().get(0);
        animal.setFill(Color.PURPLE);
        updateStats();
        followedAnimalPosition = newFollowedAnimalPosition;

    }

    public void fillCells() {
        deadAnimalsPositions = worldMap.getLastDayDeadAnimalsPositions();
        animalsPositions = worldMap.getAnimalsPositions();
        grassesPositions = worldMap.getGrassesPositions();

        for (int row = 0; row < config.getMapHeight(); row++)
            for (int col = 0; col < config.getMapWidth(); col++)
                fillCell(col, row);

        if (statsWriter.isFollowed()) {
            setNewFollowedAnimalPosition(statsWriter.getPosition());
        }

    }

    public void generateGrid() {
        content.getChildren().clear();

        int rows = config.getMapHeight();
        int columns = config.getMapWidth();
        grid = new GridPane();
        cellSize = Math.min(WIDTH/columns, HEIGHT/rows);

        animalsPositions = worldMap.getAnimalsPositions();
        grassesPositions = worldMap.getGrassesPositions();

        ColumnConstraints width = new ColumnConstraints(cellSize);
        RowConstraints height = new RowConstraints(cellSize);


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
                    Vector2d newFollowedAnimalPosition = new Vector2d(columnIndex, rowIndex);

                    if (!worldMap.getAnimals().get(newFollowedAnimalPosition).isEmpty() && newFollowedAnimalPosition != followedAnimalPosition) {
                        setNewFollowedAnimalPosition(newFollowedAnimalPosition);
                    }
                });

                grid.add(cell, col, row);
                fillCell(col, row);
            }
        }
        grid.setGridLinesVisible(true);
        content.getChildren().add(grid);
    }


    @FXML
    private void startSimulation() {
        simulation.start();
        startButton.disableProperty().setValue(true);
        pauseButton.disableProperty().setValue(false);
    }
    @FXML void pauseSimulation() {
        simulation.pause();
        startButton.disableProperty().setValue(false);
        pauseButton.disableProperty().setValue(true);

    }
    @FXML void shutDownSimulation() {
        simulation.shutDown();
        startButton.disableProperty().setValue(true);
        pauseButton.disableProperty().setValue(true);
    }
    @FXML
    synchronized void stopFollowingAnimal() {
        statsWriter.unFollowAnimal();
        fillCell(followedAnimalPosition.getX(), followedAnimalPosition.getY());
        animalStats.setVisible(false);

    }

    public void updateStats() {
        setLabelValues();
    }

    public void setLabelValues() {

        mapWidthValue.setText(config.getMapWidth() + "");
        mapHeightValue.setText(config.getMapHeight() + "");
        animalsAliveValue.setText(statsWriter.getAnimalsAlive() + "");
        animalsDeadValue.setText(statsWriter.getAnimalsDead() + "");
        plantsValue.setText(statsWriter.getGrass() + "");
        freeFieldsValue.setText(statsWriter.getFreeFields() + "");
        averageEnergyValue.setText(statsWriter.getAverageEnergy() + "");
        averageLifespanValue.setText(statsWriter.getAverageLifeLength() + "");
        averageChildrenValue.setText(statsWriter.getAverageChildrenNumber() + "");
        worldLifespanValue.setText(statsWriter.getWorldLifespan() + "");
        bestGenesValue.setText(statsWriter.getBestGenes() + "");
        if(animalStats.visibleProperty().get())
            updateFollowedAnimalStats();
    }

    private void updateFollowedAnimalStats() {
        birthdayValue.setText(statsWriter.getAnimal().getBirthday() + "");
        genomeValue.setText(statsWriter.getAnimal().getGenome() + "");
        activeGeneValue.setText(statsWriter.getAnimal().getActiveGene() + "");
        energyValue.setText(statsWriter.getAnimal().getEnergy() + "");
        eatenPlantsValue.setText(statsWriter.getAnimal().getEatenPlants() + "");
        childrenValue.setText(statsWriter.getAnimal().getChildren() + "");
        descendantsValue.setText(statsWriter.getAnimal().getOffspring() + "");
        ageValue.setText(statsWriter.getAnimal().getAge() + "");
        positionValue.setText(statsWriter.getAnimal().getPosition() + "");
        deathDayValue.setText(statsWriter.getAnimal().isDead() ? statsWriter.getAnimal().getDeathDay() + "" : "");
    }

}
