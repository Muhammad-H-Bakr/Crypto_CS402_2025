package MonoAlpha;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("DuplicatedCode")
public class MonoGUI extends Application {
    MonoCipher cipher = new MonoCipher();

    @Override
    public void start(Stage primaryStage) {
        // Title text
        Text title = new Text("Mono-Alphabetic Cipher");
        title.setFont(new Font("Arial", 24));

        // Create input fields
        TextField text = new TextField();
        text.setPromptText("Enter Text");
        text.setMinHeight(30);

        // Create result area
        TextArea result = new TextArea();
        result.setEditable(false);
        result.setPromptText("Result will appear here...");
        result.setMinHeight(100);

        // Create buttons
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");

        // Style buttons
        encryptButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        decryptButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");

        // Button actions
        encryptButton.setOnAction(_ -> {
            try {
                String targetText = text.getText();
                result.setText(cipher.encrypt(targetText));
            } catch (NumberFormatException e) {
                result.setText("Invalid key. Please enter a number.");
            }
        });

        decryptButton.setOnAction(_ -> {
            try {
                String targetText = text.getText();
                result.setText(cipher.decrypt(targetText));
            } catch (NumberFormatException e) {
                result.setText("Invalid key. Please enter a number.");
            }
        });

        // Layout for buttons
        HBox buttonBox = new HBox(10, encryptButton, decryptButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        // Main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, text, buttonBox, result);

        // Set up the scene
        Scene scene = new Scene(root, 450, 350);
        primaryStage.setTitle("MonoAlphabetic Cipher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}