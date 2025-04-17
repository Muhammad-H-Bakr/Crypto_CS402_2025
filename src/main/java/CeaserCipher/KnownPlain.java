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

@SuppressWarnings("DuplicatedCode")
public class KnownPlain extends Application {
    Ceaser cs = new Ceaser();

    @Override
    public void start(Stage primaryStage) {
        // Title text1
        Text title = new Text("Caesar KnownPlainText Attack");
        title.setFont(new Font("Arial", 24));

        // Create input fields
        TextField text1 = new TextField();
        text1.setPromptText("Enter cipherText");
        text1.setMinHeight(30);

        TextField text2 = new TextField();
        text2.setPromptText("Enter plainText");
        text2.setMinHeight(30);

        // Create result area
        TextArea result = new TextArea();
        result.setEditable(false);
        result.setPromptText("key will appear here...");
        result.setMinHeight(100);

        // Create buttons
        Button attackButton = new Button("Attack");

        // Style buttons
        attackButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

        // Button actions

        attackButton.setOnAction(_ -> {

            String ciphered = text1.getText();
            String plainText = text2.getText();
            if (ciphered.length() != plainText.length()) {
                result.setText("The ciphered text does not" +
                        " match the plain text in the given text.");
            } else {
                String key = cs.knowPlainAttack(ciphered, plainText);
                if(key.equals("null")){
                    result.setText("Enter characters in your ciphered/plain text.");
                }else if (!ciphered.equals(cs.encrypt(plainText, Integer.parseInt(key)))) {
                    result.setText("The ciphered text does not" +
                            " match the plain text in the given text.");
                }else{
                    result.setText("The key is: "+key);
                }
            }
        });

        // Layout for buttons
        HBox buttonBox = new HBox(10, attackButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        // Main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, text1, text2, buttonBox, result);

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