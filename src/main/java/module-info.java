module com.gui.gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gui.gui to javafx.fxml;
    opens com.gui.gui.View to javafx.fxml;
    opens com.gui.gui.Controller to javafx.fxml;
    exports com.gui.gui.View;
    exports com.gui.gui.Controller;
    exports com.gui.gui;
}