package com.example;

import com.example.router.Router;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Router.setStage(stage);
<<<<<<< HEAD
        Router.goToCesar();
=======

        // 👇 deja ambas líneas
        Router.goToVigenere();
        Router.goToCesar();

        stage.setTitle("Cifrados");
        stage.show();
>>>>>>> 6e5b1c2 (feat: implementar cifrado cesar)
    }

    public static void main(String[] args) {
        launch();
    }
}
