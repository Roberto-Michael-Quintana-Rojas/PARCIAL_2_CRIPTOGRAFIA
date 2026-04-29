package com.example.UI.RailFence;

import com.example.cipher.RailFence.RailFenceCipher;
import com.example.router.Router;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RailFenceController {

  @FXML
  private TextArea inputTextArea;

  @FXML
  private TextArea outputTextArea;

  @FXML
  private TextField railsField;

  @FXML
  private Label statusLabel;

  @FXML
  private void encrypt() {
    String input = inputTextArea.getText();

    if (input == null || input.trim().isEmpty()) {
      showStatus("Ingresa un texto para cifrar.", true);
      return;
    }

    Integer rails = parseRails();
    if (rails == null) {
      return;
    }

    RailFenceCipher cipher = new RailFenceCipher(rails);
    outputTextArea.setText(cipher.encrypt(input));
    showStatus("Texto cifrado correctamente.", false);
  }

  @FXML
  private void decrypt() {
    String input = inputTextArea.getText();

    if (input == null || input.trim().isEmpty()) {
      showStatus("Ingresa un texto para descifrar.", true);
      return;
    }

    Integer rails = parseRails();
    if (rails == null) {
      return;
    }

    RailFenceCipher cipher = new RailFenceCipher(rails);
    outputTextArea.setText(cipher.decrypt(input));
    showStatus("Texto descifrado correctamente.", false);
  }

  @FXML
  private void clearAll() {
    inputTextArea.clear();
    outputTextArea.clear();
    railsField.clear();
    showStatus("Campos limpiados.", false);
  }

  private Integer parseRails() {
    String railsText = railsField.getText();

    if (railsText == null || railsText.trim().isEmpty()) {
      showStatus("Ingresa la cantidad de rieles.", true);
      return null;
    }

    try {
      int rails = Integer.parseInt(railsText.trim());
      if (rails < 2) {
        showStatus("La cantidad de rieles debe ser mayor o igual a 2.", true);
        return null;
      }
      return rails;
    } catch (NumberFormatException exception) {
      showStatus("Ingresa un numero valido de rieles.", true);
      return null;
    }
  }

  private void showStatus(String message, boolean isError) {
    statusLabel.setText(message);
    statusLabel.setStyle(isError ? "-fx-text-fill: #b71c1c;" : "-fx-text-fill: #1b5e20;");
  }

  @FXML
  private void goBackToMainMenu() {
    Router.goToMainMenu();
  }
}
