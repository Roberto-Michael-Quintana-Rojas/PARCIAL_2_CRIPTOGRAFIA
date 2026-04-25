package com.example.UI.Cesar;

import com.example.cipher.Cesar.CesarCipher;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class CesarController {

    @FXML
    private TextField inputText;

    @FXML
    private TextField keyText;

    @FXML
    private TextArea resultText;

    @FXML
    private void handleEncrypt() {
        String text = inputText.getText();
        String key = keyText.getText();

        if (text == null || text.length() == 0) return;

        String result;

        boolean esNumero = true;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (c < '0' || c > '9') {
                esNumero = false;
                break;
            }
        }

        if (esNumero && key.length() > 0) {
            int shift = Integer.parseInt(key);
            CesarCipher cipher = new CesarCipher(shift);
            result = cipher.encrypt(text);
        } else {
            CesarCipher cipher = new CesarCipher(key);
            result = cipher.encrypt(text);
        }

        resultText.setText(result);
    }

    @FXML
    private void handleDecrypt() {
        String text = inputText.getText();
        String key = keyText.getText();

        if (text == null || text.length() == 0) return;

        String result;

        boolean esNumero = true;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (c < '0' || c > '9') {
                esNumero = false;
                break;
            }
        }

        if (esNumero && key.length() > 0) {
            int shift = Integer.parseInt(key);
            CesarCipher cipher = new CesarCipher(shift);
            result = cipher.decrypt(text);
        } else {
            CesarCipher cipher = new CesarCipher(key);
            result = cipher.decrypt(text);
        }

        resultText.setText(result);
    }
}
