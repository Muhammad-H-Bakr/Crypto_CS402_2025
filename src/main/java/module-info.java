module com.example.crypto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires annotations;

    //For every Task.
    opens CeaserCipher to javafx.fxml;
    exports CeaserCipher;
    opens MonoAlpha to javafx.fxml;
    exports MonoAlpha;
    opens PlayFair to javafx.fxml;
    exports PlayFair;
    opens Vigenere to javafx.fxml;
    exports Vigenere;
    opens OneTime to javafx.fxml;
    exports OneTime;
    opens DES to javafx.fxml;
    exports DES;
    opens AES to javafx.fxml;
    exports AES;
}