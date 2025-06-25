module edu.pja.mas.s22899.artgallerygui {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;

    // Our library dependencies
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.net.http;
    requires atlantafx.base;

    // Allow JavaFX to access the main package
    opens edu.pja.mas.s22899.artgallerygui to javafx.fxml;
    exports edu.pja.mas.s22899.artgallerygui;

    // Allow Jackson to access the DTO package
    opens edu.pja.mas.s22899.artgallerygui.dto to com.fasterxml.jackson.databind;
    exports edu.pja.mas.s22899.artgallerygui.dto;

    // Allow JavaFX to access the controller package
    opens edu.pja.mas.s22899.artgallerygui.controller to javafx.fxml;
    exports edu.pja.mas.s22899.artgallerygui.controller;
}