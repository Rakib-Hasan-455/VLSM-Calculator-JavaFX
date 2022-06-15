package com.example.vlsm_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VLSMApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VLSMApplication.class.getResource("VLSMCALCULATOR.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 670, 710);
        stage.setTitle("VLSM CALCULATOR!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}