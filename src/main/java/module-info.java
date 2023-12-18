module agh.ics.oop.view {
    requires javafx.controls;
    requires javafx.fxml;

    exports agh.ics.oop.presenter;
    opens agh.ics.oop.presenter to javafx.fxml;
}