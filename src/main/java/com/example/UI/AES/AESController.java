package com.example.UI.AES;

import com.example.cipher.AES.AESCipher;
import com.example.router.Router;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class AESController {

    @FXML
    private TextField inputText;
    @FXML
    private TextField keyField;
    @FXML
    private TextField outputText;
    @FXML
    private Label errorLabel;

    @FXML
    protected void onEncryptClick() {
        errorLabel.setText("");
        String text = inputText.getText().trim();
        String key = keyField.getText().trim();

        if (text.isEmpty() || key.isEmpty()) {
            errorLabel.setText("⚠ Completa todos los campos.");
            return;
        }

        AESCipher cipher = new AESCipher(key);
        outputText.setText(cipher.encrypt(text));
    }

    @FXML
    protected void onDecryptClick() {
        errorLabel.setText("");
        String hex = inputText.getText().trim(); // ← lee desde inputText
        String key = keyField.getText().trim();

        if (hex.isEmpty() || key.isEmpty()) {
            errorLabel.setText("⚠ Completa todos los campos.");
            return;
        }

        try {
            AESCipher cipher = new AESCipher(key);
            outputText.setText(cipher.decrypt(hex)); // ← muestra en outputText
        } catch (Exception e) {
            errorLabel.setText("⚠ Texto cifrado o clave inválidos.");
        }
    }

    @FXML
    private void goBackToMainMenu() {
        Router.goToMainMenu();
    }
}