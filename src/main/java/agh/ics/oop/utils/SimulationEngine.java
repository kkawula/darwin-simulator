package agh.ics.oop.utils;

import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.view.SimulationLauncher;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationEngine {
    public void start(ConfigurationData config) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        SimulationLauncher simulationLauncher = new SimulationLauncher();

        Stage stage = new Stage();
        stage.setOnCloseRequest(event -> {
            executorService.shutdownNow();
        });

        Simulation simulation = new Simulation(config);

        simulationLauncher.run(stage, simulation);

        executorService.execute(simulation);
    }

}