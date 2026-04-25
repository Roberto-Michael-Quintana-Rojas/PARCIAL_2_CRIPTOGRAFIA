package com.example.router;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Router {

    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void goToVigenere() {
        try {
<<<<<<< HEAD
            URL url = Router.class.getResource("/com/example/UI/Vigenere/Vigenere.fxml");
=======
            var url = Router.class.getResource("/com/example/UI/Vigenere/Vigenere.fxml");
            System.out.println("RUTA: " + url);
>>>>>>> 6e5b1c2 (feat: implementar cifrado cesar)
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene(loader.load(), 500, 400);

            stage.setScene(scene);
            stage.setTitle("Cifrado Vigenere");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToPigPen() {
        try {
            URL url = Router.class.getResource("/com/example/UI/PigPen/PigPen.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene(loader.load(), 645, 520);

            stage.setScene(scene);
            stage.setTitle("Cifrado PigPen");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public static void goToCesar() {
        try {
            URL url = Router.class.getResource("/com/example/UI/Cesar/Cesar.fxml");
=======
    // ✅ MÉTODO CESAR (este te faltaba)
    public static void goToCesar() {
        try {
            var url = Router.class.getResource("/com/example/UI/Cesar/Cesar.fxml");
            System.out.println("RUTA: " + url);
>>>>>>> 6e5b1c2 (feat: implementar cifrado cesar)
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene(loader.load(), 780, 620);

            stage.setScene(scene);
            stage.setTitle("Cifrado Cesar");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
