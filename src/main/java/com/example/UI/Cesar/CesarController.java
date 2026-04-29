package com.example.UI.Cesar;

import com.example.cipher.Cesar.CesarCipher;
import com.example.router.Router;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CesarController {

    @FXML
    private TextField inputText;

    @FXML
    private TextField keyText;

    @FXML
    private TextArea resultText;

    @FXML
    private RadioButton normalRadio;

    @FXML
    private RadioButton posicionesRadio;

    @FXML
    private RadioButton claveRadio;

    @FXML
    private void handleEncrypt() {
        procesar(true);
    }

    @FXML
    private void handleDecrypt() {
        procesar(false);
    }

    private void procesar(boolean cifrar) {
        String text = inputText.getText();
        String key = keyText.getText();

        if (text == null || text.length() == 0) {
            resultText.setText("Ingrese un texto.");
            return;
        }

        CesarCipher cipher;

        if (normalRadio.isSelected()) {
            cipher = new CesarCipher(3);

        } else if (posicionesRadio.isSelected()) {
            if (!esNumero(key)) {
                resultText.setText("Para César con posiciones, ingrese un número.");
                return;
            }

            int shift = Integer.parseInt(key);
            cipher = new CesarCipher(shift);

        } else if (claveRadio.isSelected()) {
            if (key == null || key.length() == 0 || !esPalabra(key)) {
                resultText.setText("Para César con clave, ingrese una palabra.");
                return;
            }

            cipher = new CesarCipher(key);

        } else {
            resultText.setText("Seleccione un tipo de cifrado César.");
            return;
        }

        if (cifrar) {
            resultText.setText(cipher.encrypt(text));
        } else {
            resultText.setText(cipher.decrypt(text));
        }
    }

    private boolean esNumero(String texto) {
        if (texto == null || texto.length() == 0) {
            return false;
        }

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }

    private boolean esPalabra(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);

            if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                return false;
            }
        }

        return true;
    }

    @FXML
    private void goBackToMainMenu() {
        Router.goToMainMenu();
    }
}
