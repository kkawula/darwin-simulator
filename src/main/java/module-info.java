module agh.ics.oop.view {
    requires javafx.controls;
    requires javafx.fxml;

    opens agh.ics.oop.view  to javafx.fxml;
    exports agh.ics.oop.view;
    exports agh.ics.oop.presenter;
    opens agh.ics.oop.presenter to javafx.fxml;
}