package agh.ics.oop.presenter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SetUpLauncher extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("setUp.fxml"));
        BorderPane viewRoot = loader.load();

        Path resourcesPath = Paths.get(getClass().getClassLoader().getResource("").toURI());
        Path configFolder = resourcesPath.resolve("config");
        Path filePath = configFolder.resolve("default.txt").normalize().toAbsolutePath();
        SetUpController controller = loader.getController();
        controller.loadConfigurationFromFile(String.valueOf(filePath));

        configureStage(primaryStage, viewRoot);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation setup");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}