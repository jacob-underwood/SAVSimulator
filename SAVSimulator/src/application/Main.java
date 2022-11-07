package application;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class Main extends Application {



	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();

			// Get screen sizes.
//			double screenX = Screen.getPrimary().getBounds().getMaxX() - screenXBorderPixels;
//			double screenY = Screen.getPrimary().getBounds().getMaxY() - screenYBorderPixels;
			
			int screenSize = 700;
			
			Scene scene = new Scene(root, screenSize, screenSize, Color.BLACK);

			// Generate intersection layout. Current total intersections: 225.
			CityDisplay cityDisplay = new CityDisplay(root);
			cityDisplay.setupCity(screenSize, 15, 8, 20, 2, 2, 4, 2);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("SAV Simulator");

			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
