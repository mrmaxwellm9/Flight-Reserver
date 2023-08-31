package UI;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PageFunctions {
	public static void openFxml(Stage primaryStage, String fxmlPath) throws IOException {
	    Scene previousScene = primaryStage.getScene();
	    double previousWidth = previousScene.getWidth();
	    double previousHeight = previousScene.getHeight();

	    Parent mainMenuParent = FXMLLoader.load(PageFunctions.class.getResource(fxmlPath));

	    // Create an HBox to center content horizontally
	    HBox hbox = new HBox(mainMenuParent);
	    hbox.setAlignment(Pos.CENTER);

	    // Create a VBox to center the HBox vertically
	    VBox vbox = new VBox(hbox);
	    vbox.setAlignment(Pos.CENTER);

	    ScrollPane scrollPane = new ScrollPane(vbox);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setFitToHeight(true);

	    Scene mainMenuScene = new Scene(scrollPane, previousWidth, previousHeight);

	    primaryStage.setScene(mainMenuScene);

	    primaryStage.show();
	}
}