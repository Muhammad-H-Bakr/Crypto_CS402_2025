package OneTime;

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

import java.util.List;

@SuppressWarnings("DuplicatedCode")
public class OneGUI extends Application {

    OneCipher cipher = new OneCipher();

    @Override
    public void start(Stage primaryStage) {
        // Title text1
        Text title = new Text("One-Time-Pad Cipher");
        title.setFont(new Font("Arial", 24));

        // Create input fields
        TextField text1 = new TextField();
        text1.setPromptText("Enter Key (For Decryption)");
        text1.setMinHeight(30);

        // Create input fields
        TextField text2 = new TextField();
        text2.setPromptText("Enter Text");
        text2.setMinHeight(30);

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
            String targetText = text2.getText();
            List<String> cs = cipher.encrypt(targetText);
            result.setText("CipherText: "+ cs.getFirst());
            System.out.println("Key: "+cs.get(1));
        });

        decryptButton.setOnAction(_ -> {
            String key = text1.getText();
            String targetText = text2.getText();
            result.setText(cipher.decrypt(targetText, key));
        });

        // Layout for buttons
        HBox buttonBox = new HBox(10, encryptButton, decryptButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        // Main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, text1, text2, buttonBox, result);

        // Set up the scene
        Scene scene = new Scene(root, 450, 350);
        primaryStage.setTitle("One-Time-Pad Cipher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}