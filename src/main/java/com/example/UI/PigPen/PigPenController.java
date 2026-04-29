package com.example.UI.PigPen;

import com.example.cipher.PigPen.PigPenCipher;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;

public class PigPenController {

  @FXML
  private TextArea inputTextArea;
  @FXML
  private TextArea outputTextArea;
  @FXML
  private Button encryptButton;
  @FXML
  private Button decryptButton;
  @FXML
  private Button clearButton;
  @FXML
  private Label statusLabel;

  private final PigPenCipher cipher = new PigPenCipher();
  private Font pigpenFont;
  private Font defaultFont;

  @FXML
  public void initialize() {
    loadPigpenFont();
    defaultFont = Font.font("System", 14);
    inputTextArea.setPromptText("Escribe aquí el texto (plano o cifrado)...");
    outputTextArea.setPromptText("El resultado aparecerá aquí...");
    outputTextArea.setEditable(false);

    statusLabel.setText("✅ Listo - Introduce texto y presiona Cifrar o Descifrar");
  }

  private void loadPigpenFont() {
    try {
      var fontStream = getClass().getResourceAsStream("/com/example/fonts/BabelStonePigpen.ttf");
      if (fontStream != null) {
        pigpenFont = Font.loadFont(fontStream, 26);
        System.out.println("Fuente BabelStonePigpen cargada correctamente");
      } else {
        System.err.println("No se encontró la fuente BabelStonePigpen.ttf");
        pigpenFont = Font.font("System", 22);
      }
    } catch (Exception e) {
      System.err.println("Error al cargar la fuente: " + e.getMessage());
      pigpenFont = Font.font("System", 22);
    }
  }

  @FXML
  private void encrypt() {
    String input = inputTextArea.getText().trim();

    if (input.isEmpty()) {
      showStatus("Por favor, escribe algo para cifrar", true);
      return;
    }

    String result = cipher.encrypt(input);

    outputTextArea.setFont(pigpenFont);
    outputTextArea.setText(result);

    showStatus("Texto cifrado correctamente ✓", false);
  }

  @FXML
  private void decrypt() {
    String input = inputTextArea.getText().trim();

    if (input.isEmpty()) {
      showStatus("Por favor, escribe algo para descifrar", true);
      return;
    }
    String result = cipher.decrypt(input);

    outputTextArea.setFont(defaultFont);
    outputTextArea.setText(result);

    showStatus("Texto descifrado correctamente ✓", false);
  }

  @FXML
  private void clearAll() {
    inputTextArea.clear();
    outputTextArea.clear();
    outputTextArea.setFont(defaultFont);
    showStatus("Campos limpiados", false);
  }

  private void showStatus(String message, boolean isError) {
    statusLabel.setText(message);
    statusLabel.setStyle(isError
        ? "-fx-text-fill: #d32f2f;"
        : "-fx-text-fill: #388e3c;");
  }

  @FXML
  private void copyCipheredText() {
    String text = inputTextArea.getText().trim();

    if (text.isEmpty()) {
      text = outputTextArea.getText().trim();
    }

    if (text.isEmpty()) {
      showStatus("No hay texto para copiar", true);
      return;
    }

    String ciphered = cipher.toPigpenSymbols(text);

    javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
    javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
    content.putString(ciphered);
    clipboard.setContent(content);

    showStatus("✅ Texto cifrado (Unicode) copiado al portapapeles", false);
  }
}