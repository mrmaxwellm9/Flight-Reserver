package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set the initial scene for the primary stage
        Parent root = FXMLLoader.load(getClass().getResource("/UI/menu.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Flight Reservation Maker");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        
        // Call openFxml method with the primary stage
        PageFunctions.openFxml(primaryStage, "/UI/menu.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}