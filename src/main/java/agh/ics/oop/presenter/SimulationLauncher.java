package agh.ics.oop.presenter;

import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.utils.ConfigurationData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationLauncher {

    public void openNewWindow(ConfigurationData config) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            Scene scene = new Scene(loader.load());

            GridPane gridPane = generateGrid(config.getMapWidth(), config.getMapHeight());
            SimulationController controller = loader.getController();
            controller.getContent().getChildren().add(gridPane);

            Simulation simulation = new Simulation(config);
            simulation.run();
            Stage stage = new Stage();
            stage.setTitle("Simulation");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error while opening new window");
            e.printStackTrace();
        }
    }

    private GridPane generateGrid(int rows, int columns) {
        GridPane gridPane = new GridPane();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Label label = new Label("");
                label.getStyleClass().add("grid-cell");
                gridPane.add(label, col, row);
            }
        }

        return gridPane;
    }
}