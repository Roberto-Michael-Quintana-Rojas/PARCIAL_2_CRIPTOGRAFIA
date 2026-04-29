module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.UI.Vigenere to javafx.fxml;
    opens com.example.UI.PigPen to javafx.fxml;
    opens com.example.UI.RailFence to javafx.fxml;
    opens com.example.UI.AES to javafx.fxml;

    //aqui agregar sus propios controladores de cada cifrado compañeres

    //algo asi como esto:
    /*
    opens com.example.UI.AES to javafx.fxml;
    opens com.example.UI.Cesar to javafx.fxml;
    opens com.example.UI.PigPen to javafx.fxml;
    opens com.example.UI.RailFence to javafx.fxml; */
    exports com.example;
}
