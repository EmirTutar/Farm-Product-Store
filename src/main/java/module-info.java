module Farm_Product_Store {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.base;
    requires org.junit.jupiter.api;
    requires org.testfx.junit5;
    requires org.testfx;
    requires org.mockito;
    requires org.mockito.junit.jupiter;

    opens org.app.controllers to javafx.fxml, org.junit.platform.commons, org.testfx;
    opens org.app to org.junit.platform.commons, org.testfx;

    exports org.app;
    exports org.app.controllers;
    //exports org.app.data;
    exports org.app.data.models;
    exports org.app.data.dataCollection;
    exports org.app.data.dataFormats;
    exports org.app.validation;
    exports org.app.validation.customExceptions;
    exports org.app.validation.ioExceptions;
    exports org.app.fileHandling.open;
    exports org.app.fileHandling.save;
    exports org.app.fileHandling.fileThreads;
    exports org.app.fileHandling;
}