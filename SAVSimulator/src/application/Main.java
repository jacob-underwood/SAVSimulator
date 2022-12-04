package application;

import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Parameters params = getParameters();
		List<String> paramsList = params.getRaw();
		
		boolean display = true;
		String busType = null;
		
		try {
			display = Boolean.parseBoolean(paramsList.get(0));
			busType = paramsList.get(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getClass().getName() + ": " + e.getMessage());
		}
		
		try {
			// TODO: Make it so CityDisplay is not even required in City so simulations to test data can be more efficient.
			Group root = new Group();

			int screenSize = 700;

			Scene scene = new Scene(root, screenSize, screenSize, Color.BLACK);

			// Generate intersection layout. Current total intersections: 225.
			CityDisplay cityDisplay = new CityDisplay(root, 15, screenSize, 8, 20, 2, 2, 4, 2);
			cityDisplay.setupCity();

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("SAV Simulator");

			// Create City object, then run simulation.
				
			if (display) {
				primaryStage.show();
				
				City<?> city;
				
				if (busType.equals("average")) {
					city = new City<AverageBus>(cityDisplay, AverageBus.class, true);
				} else if (busType.equals("linear")) {
					city = new City<LinearBus>(cityDisplay, LinearBus.class, true);
				} else {
					city = new City<ProximityBus>(cityDisplay, ProximityBus.class, true);
				}
				
				city.setupSimulation();
				
				scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						city.runSimulation();
					}
				});
				
				scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent keyEvent) {
						city.runSimulation();
					}
				});
			} else {
				int count = 100;
				int stepSum = 0;
				
				for (int i = 0; i < count; i++) {
					City<?> city;
					
					if (busType.equals("average")) {
						city = new City<AverageBus>(cityDisplay, AverageBus.class, true);
					} else if (busType.equals("linear")) {
						city = new City<LinearBus>(cityDisplay, LinearBus.class, true);
					} else {
						city = new City<ProximityBus>(cityDisplay, ProximityBus.class, true);
					}
					
					city.setupSimulation();
					
					int step = 0;
					int maxStep = 0;
					
					while (step != -1) {
						maxStep = step;
						step = city.runSimulation();
					}
					
					stepSum += maxStep;
				}
				
				System.out.println("AVERAGE STEP COUNT: " + (stepSum / count));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
