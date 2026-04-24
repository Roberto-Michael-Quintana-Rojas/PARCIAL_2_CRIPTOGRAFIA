package com.example.UI.Vigenere;

import com.example.cipher.Vigenere.VigenereCipher;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class VigenereController {

    @FXML
    private TextField inputText;

    @FXML
    private TextField outputText;

    @FXML
    private TextField keyField;

    @FXML
    protected void onEncryptClick() {
        String text = inputText.getText();
        String key = keyField.getText();

        VigenereCipher cipher = new VigenereCipher(key);
        String result = cipher.encrypt(text);

        outputText.setText(result);
    }
}