module com.ayushrg.tubeflowx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.json;

    opens com.ayushrg.tubeflowx to javafx.fxml;
    opens com.ayushrg.tubeflowx.model to javafx.base;
    exports com.ayushrg.tubeflowx;
    exports com.ayushrg.tubeflowx.controller;
    opens com.ayushrg.tubeflowx.controller to javafx.fxml;
}