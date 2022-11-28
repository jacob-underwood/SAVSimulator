package application;

import javafx.animation.Timeline;
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
			CityDisplay cityDisplay = new CityDisplay(root, 15);
			cityDisplay.setupCity(screenSize, 8, 20, 2, 2, 4, 2);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("SAV Simulator");

			primaryStage.show();
			
			// Testing
			
			
			
			cityDisplay.addPerson(3, 0);
			cityDisplay.busArrived(0, 0);
			cityDisplay.busMoved(0, 0, 4, 5);
			
			// Create City object, then run simulation.

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
