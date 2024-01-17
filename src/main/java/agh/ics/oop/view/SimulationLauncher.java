package agh.ics.oop.view;

import agh.ics.oop.simulation.Simulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimulationLauncher extends Application {
    private Simulation simulation;

    public void run(Stage primaryStage, Simulation simulation) {
        this.simulation = simulation;
        start(primaryStage);
    }

    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            SimulationController controller = loader.getController();
            controller.init(simulation);
            simulation.subscribe(controller);

            primaryStage.setTitle("Simulation");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
