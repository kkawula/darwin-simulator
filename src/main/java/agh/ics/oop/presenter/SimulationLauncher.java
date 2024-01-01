package agh.ics.oop.presenter;

import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.utils.ConfigurationData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationLauncher {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    public void openNewWindow(ConfigurationData config) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            Scene scene = new Scene(loader.load());
            SimulationController controller = loader.getController();
            controller.wire(config);
            controller.generateGrid();

            Simulation simulation = new Simulation(config);
            for (int i = 0; i < 10000; i++) {
                simulation.run();
            }
            Stage stage = new Stage();
            stage.setTitle("Simulation");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error while opening new window");
            e.printStackTrace();
        }
    }

}
