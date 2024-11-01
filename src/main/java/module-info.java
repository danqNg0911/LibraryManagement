module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires okhttp3;
    requires com.google.gson;

    opens com.example.library to javafx.fxml;
    exports com.example.library;
    opens com.example.library.fxml to javafx.fxml;
}