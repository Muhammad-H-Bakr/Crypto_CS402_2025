package CeaserCipher;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class CeaserGUI extends Application {
    Ceaser cs = new Ceaser();

    @Override
    public void start(Stage primaryStage) {
        // Title text
        Text title = new Text("Caesar Cipher");
        title.setFont(new Font("Arial", 24));

        // Create input fields
        TextField text = new TextField();
        text.setPromptText("Enter Text");
        text.setMinHeight(30);

        TextField key = new TextField();
        key.setPromptText("Enter Key");
        key.setMinHeight(30);

        // Create result area
        TextArea result = new TextArea();
        result.setEditable(false);
        result.setPromptText("Result will appear here...");
        result.setMinHeight(100);

        // Create buttons
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");
        Button attackButton = new Button("Attack");

        // Style buttons
        encryptButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        decryptButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        attackButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

        // Button actions
        encryptButton.setOnAction(_ -> {
            try {
                String targetText = text.getText();
                int targetKey = Integer.parseInt(key.getText());
                result.setText(cs.encrypt(targetText, targetKey));
            } catch (NumberFormatException e) {
                result.setText("Invalid key. Please enter a number.");
            }
        });

        decryptButton.setOnAction(_ -> {
            try {
                String targetText = text.getText();
                int targetKey = Integer.parseInt(key.getText());
                result.setText(cs.decrypt(targetText, targetKey));
            } catch (NumberFormatException e) {
                result.setText("Invalid key. Please enter a number.");
            }
        });

        attackButton.setOnAction(_ -> {

            StringBuilder ciphered = new StringBuilder();

            try {
                File f1 = new File("D:\\CryptoCS402\\Crypto_CS402_2025" +
                        "\\src\\main\\java\\CeaserCipher\\Ciphered.txt");
                Scanner dataReader = new Scanner(f1);
                while (dataReader.hasNextLine()) {
                    ciphered.append(dataReader.nextLine());
                }
                dataReader.close();
            } catch (FileNotFoundException exception) {
                result.setText("File not found!");
                return;
            }

            List<String> atk = cs.attacks(ciphered.toString());

            try {
                FileWriter fwrite = new FileWriter("D:\\CryptoCS402\\Crypto_CS402_2025" +
                        "\\src\\main\\java\\CeaserCipher\\Deciphered.txt");
                for (String s : atk) {
                    fwrite.write(s + '\n');
                }
                fwrite.close();
                result.setText("Attack results saved to file.");
            } catch (IOException e) {
                result.setText("Error writing to file.");
            }
        });

        // Layout for buttons
        HBox buttonBox = new HBox(10, encryptButton, decryptButton, attackButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        // Main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, text, key, buttonBox, result);

        // Set up the scene
        Scene scene = new Scene(root, 450, 350);
        primaryStage.setTitle("Caesar Cipher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}