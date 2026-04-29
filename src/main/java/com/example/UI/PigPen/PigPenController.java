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
    private Button showKeyButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Label titleLabel;

    private final PigPenCipher cipher = new PigPenCipher();
    private Font pigpenFont;
    private Font defaultFont;

    @FXML
    public void initialize() {
        loadPigpenFont();
        defaultFont = Font.font("System", 14);
        inputTextArea.setPromptText("Escribe aquí el texto a cifrar...");
        outputTextArea.setPromptText("El resultado aparecerá aquí...");
        outputTextArea.setEditable(false);
        statusLabel.setText("✅ Listo - Introduce texto y usa los botones");
        encryptButton.setTooltip(new Tooltip("Cifrar el texto usando Pigpen"));
        decryptButton.setTooltip(new Tooltip("Descifrar el texto del resultado"));
        clearButton.setTooltip(new Tooltip("Limpiar ambos campos"));
        showKeyButton.setTooltip(new Tooltip("Mostrar la clave Pigpen completa"));
    }

    private void loadPigpenFont() {
        try {
            var fontStream = getClass().getResourceAsStream("/com/example/fonts/BabelStonePigpen.ttf");
            if (fontStream != null) {
                pigpenFont = Font.loadFont(fontStream, 26);
                System.out.println("Fuente BabelStonePigpen cargada correctamente (tamaño 26)");
            } else {
                System.err.println("Fuente no encontrada en /com/example/fonts/BabelStonePigpen.ttf");
                pigpenFont = Font.font("System", 22);
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando fuente: " + e.getMessage());
            pigpenFont = Font.font("System", 22);
        }
    }

    @FXML
    private void encrypt() {
        String input = inputTextArea.getText().trim();

        if (input.isEmpty()) {
            showStatus("Por favor, escribe algo en el campo de entrada", true);
            return;
        }

        String result = cipher.encrypt(input);

        outputTextArea.setFont(pigpenFont);
        outputTextArea.setText(result);

        showStatus("Texto cifrado correctamente con Pigpen ✓", false);
    }

    @FXML
    private void decrypt() {
        String input = outputTextArea.getText().trim();

        if (input.isEmpty()) {
            showStatus("No hay texto para descifrar", true);
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
        showStatus("Todos los campos han sido limpiados", false);
    }

    @FXML
    private void showKey() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Clave del Cifrado Pigpen");
        alert.setHeaderText("Cómo funciona el Cifrado Pigpen");
        alert.setContentText("""
                La clave consiste en 4 figuras:

                • Rejilla 3x3 sin puntos  → Letras A hasta I
                • Rejilla 3x3 con puntos   → Letras J hasta R
                • Cruz (X) sin puntos      → Letras S hasta V
                • Cruz (X) con puntos      → Letras W hasta Z

                El texto cifrado se ve con símbolos geométricos gracias a la fuente especial.
                """);
        alert.showAndWait();
    }

    @FXML
    private void copyToClipboard() {
        String input = inputTextArea.getText().trim();

        if (input.isEmpty() && outputTextArea.getText().trim().isEmpty()) {
            showStatus("No hay texto para copiar", true);
            return;
        }

        String textToCopy = cipher.toPigpenSymbols(input.isEmpty() ? outputTextArea.getText() : input);

        javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
        javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
        content.putString(textToCopy);
        clipboard.setContent(content);

        showStatus("✅ Texto cifrado (símbolos) copiado al portapapeles", false);
    }

    private void showStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setStyle(isError ? "-fx-text-fill: #d32f2f;" : "-fx-text-fill: #388e3c;");
    }

    public Font getPigpenFont() {
        return pigpenFont;
    }
}