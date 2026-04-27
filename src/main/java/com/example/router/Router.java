package com.example.router;

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

            //aqui agregar las rutas de cada cifrado compañeres, algo asi como esto:
            //----------------------------------------------------------------
            var url = Router.class.getResource("/com/example/UI/Vigenere/Vigenere.fxml");
            //------------------------------------------------
            System.out.println("RUTA: " + url);
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene(loader.load(), 450, 350); //los valores que estan aqui son las dimensiones de la ventana, pueden cambiarse luego
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToPigPen() {
    try {
        var url = Router.class.getResource("/com/example/UI/PigPen/PigPen.fxml");
        System.out.println("RUTA: " + url);

        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load(), 645, 520);
        stage.setScene(scene);
        stage.setTitle("🔐 Cifrado Pigpen");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}