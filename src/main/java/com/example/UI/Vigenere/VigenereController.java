package com.example.UI.Vigenere;

import com.example.cipher.Vigenere.VigenereCipher;
import com.example.router.Router;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class VigenereController {

<<<<<<< HEAD
    @FXML private TextField inputText;
    @FXML private TextField keyField;
    @FXML private TextField outputText;
    @FXML private Label errorLabel;

    @FXML
    protected void onEncryptClick() {
        errorLabel.setText("");
        String text = inputText.getText().trim();
        String key  = keyField.getText().trim();

        if (text.isEmpty() || key.isEmpty()) {
            errorLabel.setText("⚠ Completa todos los campos.");
            return;
        }

        VigenereCipher cipher = new VigenereCipher(key);
        outputText.setText(cipher.encrypt(text));
    }

    @FXML
    protected void onDecryptClick() {
        errorLabel.setText("");
        String text = inputText.getText().trim();  // pega el texto cifrado aquí
        String key  = keyField.getText().trim();

        if (text.isEmpty() || key.isEmpty()) {
            errorLabel.setText("⚠ Completa todos los campos.");
            return;
        }

        VigenereCipher cipher = new VigenereCipher(key);
        outputText.setText(cipher.decrypt(text));
    }
=======
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

  @FXML
  private void goBackToMainMenu() {
    Router.goToMainMenu();
  }
>>>>>>> eb967b3 (feat: implementacion de la ventana principal)
}