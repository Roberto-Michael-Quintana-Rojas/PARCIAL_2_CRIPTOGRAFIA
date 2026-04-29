package com.example;

import com.example.router.Router;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Router.setStage(stage);

        // Abre directamente Vigenere (puedes cambiar luego)
        Router.goToPigPen();
        
        stage.setTitle("Cifrados");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}