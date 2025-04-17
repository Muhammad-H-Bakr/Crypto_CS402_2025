package DES;

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
import org.jetbrains.annotations.NotNull;

import static DES.Rounds.*;
import static DES.Structure.padding;
import static DES.Structure.stringToBinary;

@SuppressWarnings("DuplicatedCode")
public class DES_GUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title text1
        Text title = new Text("Data Encryption Standard");
        title.setFont(new Font("Arial", 24));

        // Create input fields
        TextField text1 = new TextField();
        text1.setPromptText("Enter Key (8 Characters)");
        text1.setMinHeight(30);

        // Create input fields
        TextField text2 = new TextField();
        text2.setPromptText("Enter Text (Blocks of 8 Characters)");
        text2.setMinHeight(30);

        // Create result area
        TextArea result = new TextArea();
        result.setEditable(false);
        result.setPromptText("Result will appear here...");
        result.setMinHeight(100);

        // Create buttons
        HBox buttonBox = getHBox(text1, text2, result);

        // Main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, text1, text2, buttonBox, result);

        // Set up the scene
        Scene scene = new Scene(root, 450, 350);
        primaryStage.setTitle("DES");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @NotNull
    private static HBox getHBox(TextField text1, TextField text2, TextArea result) {
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");

        // Style buttons
        encryptButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        decryptButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");

        // Button actions
        encryptButton.setOnAction(_ -> {
            if (text1.getText().length() > 8) {
                result.setText("Enter a valid key (8 Characters or less)");
            } else {
                String key = stringToBinary(padding(text1.getText(), 8), 8);
                String targetText = stringToBinary(padding(text2.getText(), 8), 8);
                result.setText(encrypt(targetText, key));
                System.out.println(roundKey(key));
            }
        });

        decryptButton.setOnAction(_ -> {
            if (text1.getText().length() > 8) {
                result.setText("Enter a valid key (8 Characters or less)");
            } else {
                String key = stringToBinary(padding(text1.getText(), 8), 8);
                String targetText = text2.getText();
                if (targetText.length() % 64 != 0) {
                    result.setText("Enter a valid CipherText.");
                } else {
                    result.setText(Structure.ASCII2String(decrypt(targetText, key)));
                    System.out.println(roundKey(key).reversed());
                }
            }
        });

        // Layout for buttons
        HBox buttonBox = new HBox(10, encryptButton, decryptButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));
        return buttonBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}