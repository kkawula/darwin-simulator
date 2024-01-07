package agh.ics.oop.presenter;

import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.WorldMap;
import agh.ics.oop.utils.ConfigurationData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimulationLauncher extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private ConfigurationData config;
    private WorldMap worldMap;

    public void openNewWindow(ConfigurationData config) {
        this.config = config;

        try {
            Simulation simulation = new Simulation(config);
            worldMap = simulation.worldMap;
//            Thread simulationThread = new Thread(simulation);
            start(new Stage());
//            simulationThread.start();
            simulation.run();
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
        SimulationController controller = loader.getController();
        controller.wire(config, worldMap);

        controller.generateGrid();

        primaryStage.setTitle("Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startSimulation() {
    	Simulation simulation = new Simulation(config);
        for (int i = 0; i < 1; i++) {
            simulation.run();
        }
    }
}
