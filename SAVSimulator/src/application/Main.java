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

//	final double screenXBorderPixels = 2.0;
//	final double screenYBorderPixels = 70.0;
	final int screenSize = 700;
	// How many intersections in one side. Square to get total number.
	final int gridSize = 15;
	// Radius of circles representing intersections.
	final int intersectRadius = 8;
	// Total extra boundary added in a certain direction. Halve to get cushion added
	// on a singular side.
	final int extraBoundary = 20;
	
	// Radius difference from intersection to ring.
	final int ringDistance = 2;
	// Stroke size is added on top of the radius.
	final int ringStrokeSize = 2;
	
	// Distance from ring to center of person.
	final int personDistance = 4;
	final int personRadius = 2;

	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();

			// Get screen sizes.
//			double screenX = Screen.getPrimary().getBounds().getMaxX() - screenXBorderPixels;
//			double screenY = Screen.getPrimary().getBounds().getMaxY() - screenYBorderPixels;

			Scene scene = new Scene(root, screenSize, screenSize, Color.BLACK);

			// Generate intersection layout. Current total intersections: 225.
			Group intersections = new Group();
			Group busMarkers = new Group();
			Group people = new Group();
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					Circle circle = new Circle(intersectRadius, Color.web("gray"));
					circle.setCenterX((i) * (screenSize - extraBoundary) / gridSize
							+ (screenSize) / ((gridSize + gridSize * 2) / 2));
					circle.setCenterY((j) * (screenSize - extraBoundary) / gridSize
							+ (screenSize) / ((gridSize + gridSize * 2) / 2));
					intersections.getChildren().add(circle);
					
					Circle ring = new Circle(intersectRadius + ringDistance, Color.web("transparent"));
					ring.setCenterX(circle.getCenterX());
					ring.setCenterY(circle.getCenterY());
					ring.setStrokeType(StrokeType.OUTSIDE);
					ring.setStroke(Color.web("red"));
					ring.setStrokeWidth(ringStrokeSize);
					ring.setVisible(false);
					busMarkers.getChildren().add(ring);
					
					double personRingXRadius = intersectRadius + ringDistance + ringStrokeSize + personDistance;
					double personRingYRadius = intersectRadius + ringDistance + ringStrokeSize + personDistance;
					for (int k = 0; k < 8; k++) {
						Circle person = new Circle(personRadius, Color.web("blue"));
						person.setCenterX(circle.getCenterX() + personRingXRadius * Math.cos(k * Math.PI / 4));
						person.setCenterY(circle.getCenterY() + personRingYRadius * Math.sin(k * Math.PI / 4));
						person.setVisible(false);
						people.getChildren().add(person);
					}
				}
			}

			root.getChildren().add(intersections);
			root.getChildren().add(busMarkers);
			root.getChildren().add(people);

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
