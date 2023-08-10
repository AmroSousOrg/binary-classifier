module najah.ai.binaryclassifierapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens najah.ai.binaryclassifierapp to javafx.fxml;
    exports najah.ai.binaryclassifierapp;
}