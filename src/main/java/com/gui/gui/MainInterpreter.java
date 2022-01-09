package com.gui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainInterpreter extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainInterpreter.class.getResource("MainLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Toy Language - Please select a program");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
