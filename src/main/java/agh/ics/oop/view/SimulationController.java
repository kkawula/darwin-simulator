package agh.ics.oop.view;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.presenter.*;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.WorldMap;
import agh.ics.oop.utils.ConfigurationData;

import javafx.application.Platform;
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


public class SimulationController implements SimulationObserver {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    @FXML
    private Button startButton, pauseButton;
    private ConfigurationData config;
    private WorldMap worldMap;
    private Simulation simulation;
    private Vector2d followedAnimalPosition;

    @FXML
    private VBox content, animalStatsPanel;

    @FXML
    private Label mapWidthValue, mapHeightValue, animalsAliveValue,
            animalsDeadValue, plantsValue, freeFieldsValue,
            averageEnergyValue, averageLifespanValue,
            averageChildrenValue, worldLifespanValue, birthdayValue,
            genomeValue, activeGeneValue,energyValue, eatenPlantsValue,
            childrenValue, descendantsValue, ageValue,
            positionValue, bestGenesValue, deathDayValue;
    private GridPane grid;
    private List<Vector2d> grassesPositions = new LinkedList<>();
    private HashMap<Vector2d, Integer> animalsPositions = new HashMap<>();
    private LinkedList<Vector2d> deadAnimalsPositions = new LinkedList<>();
    private LinkedList<Vector2d> theMostPopularGenesPositions = new LinkedList<>();
    private SimulationStats simulationStats;
    private AnimalStats animalStats;
    private int cellSize;
    private boolean isPaused = false;

    private boolean isFollowed = false;

    public void init(Simulation simulation) {
        this.simulation = simulation;
        this.config = simulation.config;
        this.worldMap = simulation.worldMap;

        generateGrid();
        fillCells();
    }

    @Override
    public void update(DisplayData displayData) {
        Platform.runLater(() -> {
            simulationStats = displayData.getSimulationStats();

            deadAnimalsPositions = displayData.getPositionsData().getLastDayDeadAnimalsPositions();
            animalsPositions = displayData.getPositionsData().getAnimalPositions();
            grassesPositions = displayData.getPositionsData().getGrassPositions();
            theMostPopularGenesPositions = displayData.getPositionsData().getTheMostPopularGenesPositions();

            animalStats = displayData.getAnimalStats();
            if (Objects.nonNull(animalStats))
                if(animalStats.isDead()) {
                    deathDayValue.setText(animalStats.getDeathDay() + "");
                } else {
                    updateFollowedAnimalStats();
                }

            fillCells();
            updateStats();
        });

    }

    public Circle createEnergyCircle(double energy) {
        double averageEnergy = simulationStats.getAverageEnergy();
        double distanceFromAverage = energy - averageEnergy;
        double shift = distanceFromAverage / averageEnergy;
        double saturation = Math.max(0.0, Math.min(1.0, 0.5 + shift));
        Color color = Color.hsb(0, saturation, 1.0);

        Circle circle = new Circle((cellSize / 2.0) * 0.7);
        circle.setFill(color);

        return circle;
    }

    private void fillCell(int col, int row) {
        Vector2d vector = new Vector2d(col, row);
        GridPane cell = (GridPane) grid.getChildren().get(row * config.mapWidth() + col);
        cell.getChildren().clear();

        switch (config.growthVariant()) {
            case FORESTED_EQUATOR -> {
                if (row >= config.mapHeight() * 0.4 && row <= config.mapHeight() * 0.6) {
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

    private void setNewFollowedAnimalPosition(Vector2d newFollowedAnimalPosition) {

        if (Objects.nonNull(followedAnimalPosition)) {
            int row = followedAnimalPosition.getY();
            int col = followedAnimalPosition.getX();
            fillCell(col, row);
        }
        int row = newFollowedAnimalPosition.getY();
        int col = newFollowedAnimalPosition.getX();

        animalStatsPanel.setVisible(true);

        GridPane cell = (GridPane) grid.getChildren().get(row * config.mapWidth() + col);
        Circle animal = (Circle) cell.getChildren().get(0);
        animal.setFill(Color.PURPLE);
        followedAnimalPosition = newFollowedAnimalPosition;
        if (isPaused) {
            updateFollowAnimalStatsOnPause(worldMap.getLastAnimal(followedAnimalPosition));
        } else {
            updateFollowedAnimalStats();
        }
    }

    public void fillCells() {

        for (int row = 0; row < config.mapHeight(); row++)
            for (int col = 0; col < config.mapWidth(); col++)
                fillCell(col, row);

        if(isFollowed) {
            if(animalStats.isDead())
            {
                deathDayValue.setText(animalStats.getDeathDay() + "");
            }
            else setNewFollowedAnimalPosition(animalStats.getPosition());
        }
    }

    public void generateGrid() {
        content.getChildren().clear();

        int rows = config.mapHeight();
        int columns = config.mapWidth();
        grid = new GridPane();
        cellSize = Math.min(WIDTH/columns, HEIGHT/rows);

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

                    if (isPaused && !worldMap.getAnimals().get(newFollowedAnimalPosition).isEmpty() && newFollowedAnimalPosition != followedAnimalPosition) {
                        isFollowed = true;
                        simulation.setFollowedAnimal(newFollowedAnimalPosition);
                        setNewFollowedAnimalPosition(newFollowedAnimalPosition);

                    }
                });

                grid.add(cell, col, row);
            }
        }
        grid.setGridLinesVisible(true);
        content.getChildren().add(grid);
    }

    private void fillTheMostPopularGenesPositions() {
        for (Vector2d position : theMostPopularGenesPositions) {
            GridPane cell = (GridPane) grid.getChildren().get(position.getY() * config.mapWidth() + position.getX());
            cell.setStyle("-fx-background-color: #FFD700;");
        }
    }


    @FXML
    private void startSimulation() {
        simulation.start();
        startButton.disableProperty().setValue(true);
        pauseButton.disableProperty().setValue(false);
        isPaused = false;
    }
    @FXML void pauseSimulation() {
        simulation.pause();
        startButton.disableProperty().setValue(false);
        pauseButton.disableProperty().setValue(true);
        isPaused = true;

        fillTheMostPopularGenesPositions();
    }
    @FXML void shutDownSimulation() {
        simulation.shutDown();
        startButton.disableProperty().setValue(true);
        pauseButton.disableProperty().setValue(true);
        isPaused = true;
    }
    @FXML
    synchronized void stopFollowingAnimal() {
        isFollowed = false;
        fillCell(followedAnimalPosition.getX(), followedAnimalPosition.getY());
        animalStatsPanel.setVisible(false);

    }

    public void updateStats() {
        setLabelValues();
    }

    public void setLabelValues() {

        mapWidthValue.setText(config.mapWidth() + "");
        mapHeightValue.setText(config.mapHeight() + "");
        animalsAliveValue.setText(simulationStats.getAnimalsAlive() + "");
        animalsDeadValue.setText(simulationStats.getAnimalsDead() + "");
        plantsValue.setText(simulationStats.getGrass() + "");
        freeFieldsValue.setText(simulationStats.getFreeFields() + "");
        averageEnergyValue.setText(String.format("%.2f", simulationStats.getAverageEnergy()));
        averageLifespanValue.setText(String.format("%.2f", simulationStats.getAverageLifeLength()));
        averageChildrenValue.setText(String.format("%.2f", simulationStats.getAverageChildrenNumber()));
        worldLifespanValue.setText(simulationStats.getWorldLifespan() + "");
        bestGenesValue.setText(simulationStats.getBestGenes() + "");
    }

    private void updateFollowedAnimalStats() {
        birthdayValue.setText(animalStats.getBirthday() + "");
        genomeValue.setText(animalStats.getGenome() + "");
        activeGeneValue.setText(animalStats.getActiveGene() + "");
        energyValue.setText(animalStats.getEnergy() + "");
        eatenPlantsValue.setText(animalStats.getEatenPlants() + "");
        childrenValue.setText(animalStats.getChildren() + "");
        descendantsValue.setText(animalStats.getDescendants() + "");
        ageValue.setText(animalStats.getAge() + "");
        positionValue.setText(animalStats.getPositionString() + "");
    }
    private void updateFollowAnimalStatsOnPause(Animal animal)
    {
        birthdayValue.setText(animal.getBirthday() + "");
        genomeValue.setText(animal.getGenome() + "");
        activeGeneValue.setText(animal.getActiveGene() + "");
        energyValue.setText(animal.getEnergy() + "");
        eatenPlantsValue.setText(animal.getEatenPlants() + "");
        childrenValue.setText(animal.getChildren() + "");
        descendantsValue.setText(animal.getOffspring() + "");
        ageValue.setText(animal.getAge() + "");
        positionValue.setText(animal.getPosition() + "");
    }
}
