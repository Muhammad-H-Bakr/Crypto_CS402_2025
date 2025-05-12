package RSA;

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

import java.math.BigInteger;

@SuppressWarnings("DuplicatedCode")
public class RabinGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title text
        Text title = new Text("Rabin-Millar Primality Testing");
        title.setFont(new Font("Arial", 24));

        // Create input fields
        TextField text1 = new TextField();
        text1.setPromptText("Enter a Number to test");
        text1.setMinHeight(30);

        // Create input fields
        TextField text2 = new TextField();
        text2.setPromptText("Enter Trials");
        text2.setMinHeight(30);

        // Create result area
        TextArea result = new TextArea();
        result.setEditable(false);
        result.setPromptText("Result will appear here...");
        result.setMinHeight(100);

        // Create buttons
        Button testButton = new Button("Test");

        // Style buttons
        testButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Button actions
        testButton.setOnAction(_ -> {
            try {
                int trails = Integer.parseInt(text2.getText());
                BigInteger num = new BigInteger(text1.getText());

                if (num.compareTo(BigInteger.valueOf(trails)) <= 0) {
                    result.setText("Trails must be less than the tested number");
                } else if (trails <= 0) {
                    result.setText("Trails must be greater than 0");
                } else {
                    result.setText(RabinMillar.probablyPrime(num, trails) ? "Inconclusive" :
                            "Not Prime");
                }
            } catch (NumberFormatException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        });

        // Layout for buttons
        HBox buttonBox = new HBox(10, testButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        // Main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, text1, text2, buttonBox, result);

        // Set up the scene
        Scene scene = new Scene(root, 450, 350);
        primaryStage.setTitle("Primality Testing");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
