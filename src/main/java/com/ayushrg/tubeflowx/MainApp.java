// **********************************************************************************
// Title: TubeFlow
// Author: Ayush Regmi
// Course Section: CMIS201-ONL2 (Seidel) Spring 2024
// File: Main.java
// Description: Main file and starting point of our application
// **********************************************************************************

package com.ayushrg.tubeflowx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/home-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            String css = Objects.requireNonNull(this.getClass().getResource("homeStyle.css")).toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}