package agh.ics.oop.view;

import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.utils.ConfigurationData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimulationLauncher extends Application {
    private ConfigurationData config;
    private Simulation simulation;
    private SimulationController controller;
    Thread simulationThread;

    public void openNewWindow(ConfigurationData config) {
        this.config = config;
        simulation = new Simulation(config, this);
        simulationThread = new Thread(simulation);
        Stage stage = new Stage();
        stage.setOnCloseRequest(event -> simulation.shutDown());

        try {
            start(stage);
            simulationThread.start();
        }
        catch (Exception e) {
            System.out.println("Error while opening new window");
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();
        controller.init(simulation);
        simulation.subscribe(controller);
        updateGrid();

        primaryStage.setTitle("Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateGrid() {
        controller.fillCells();
    }
}
