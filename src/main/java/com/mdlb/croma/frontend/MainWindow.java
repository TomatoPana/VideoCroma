package com.mdlb.croma.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.mdlb.croma.SystemInfo;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) {
        Label first_name = new Label("First Name");
        first_name.setPrefWidth(200);
        Label last_name = new Label("Last Name");
        TextField tf1 = new TextField();
        tf1.setPrefWidth(200);
        TextField tf2 = new TextField();
        Button Submit = new Button("Submit");
        GridPane root = new GridPane();
        root.setPrefWidth(400);
        root.setPrefHeight(200);
        Scene scene = new Scene(root, 400, 200);
        root.addRow(0, first_name, tf1);
        root.addRow(1, last_name, tf2);
        root.addRow(2, Submit);
        stage.setScene(scene);
        stage.show();
    }

    private void tutorial(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}
