module agh.ics.oop {
    requires javafx.controls;
    requires javafx.fxml;

    exports agh.ics.oop.presenter;
    opens agh.ics.oop.presenter to javafx.fxml;
    exports agh.ics.oop.view;
    opens agh.ics.oop.view to javafx.fxml;
}