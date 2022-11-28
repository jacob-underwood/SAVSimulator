package application;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 * Deals with graphical output.
 *
 */
public class CityDisplay {

	private Group root;
	// How many intersections in one side. Square to get total number.
	private int gridSize;
	// Counts how many people are at each intersection.
	private int[][] gridPersonCount;
	// Counts how many buses are at each intersections.
	private int[][] gridBusCount;

	public CityDisplay(Group root, int gridSize) {
		this.root = root;
		this.gridSize = gridSize;

		this.gridPersonCount = new int[gridSize][gridSize];
		this.gridBusCount = new int[gridSize][gridSize];
	}

	/**
	 * Sets up the intersection layout. It creates the intersection circles, the
	 * rings that represent the presence of the bus, and the people surrounding the
	 * intersections. Then, hides everything but the intersections.
	 * 
	 * @param screenSize      Size of screen in pixels.
	 * @param intersectRadius Radius of circles representing intersections.
	 * @param extraBoundary   Total extra boundary added in a certain direction.
	 *                        Halve to get cushion added on a singular side.
	 * @param ringDistance    Radius difference from intersection to ring.
	 * @param ringStrokeSize  Stroke size is added on top of the radius.
	 * @param personDistance  Distance from ring to center of person.
	 * @param personRadius    Radius of person circle.
	 */
	public void setupCity(int screenSize, int intersectRadius, int extraBoundary, int ringDistance, int ringStrokeSize,
			int personDistance, int personRadius) {
		// TODO: Should these be parents instead?
		Group intersections = new Group();
		Group busMarkers = new Group();
		Group people = new Group();

		intersections.setUserData("intersections");
		busMarkers.setUserData("busMarkers");
		people.setUserData("people");

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				Circle circle = new Circle(intersectRadius, Color.web("gray"));
				circle.setCenterX(
						(j) * (screenSize - extraBoundary) / gridSize + (screenSize) / ((gridSize + gridSize * 2) / 2));
				circle.setCenterY(
						(i) * (screenSize - extraBoundary) / gridSize + (screenSize) / ((gridSize + gridSize * 2) / 2));
				intersections.getChildren().add(circle);

				Circle ring = new Circle(intersectRadius + ringDistance, Color.web("transparent"));
				ring.setCenterX(circle.getCenterX());
				ring.setCenterY(circle.getCenterY());
				ring.setStrokeType(StrokeType.OUTSIDE);
				ring.setStroke(Color.web("red"));
				ring.setStrokeWidth(ringStrokeSize);
				ring.setOpacity(0f);
				busMarkers.getChildren().add(ring);

				double personRingXRadius = intersectRadius + ringDistance + ringStrokeSize + personDistance;
				double personRingYRadius = intersectRadius + ringDistance + ringStrokeSize + personDistance;
				for (int k = 0; k < 8; k++) {
					Circle person = new Circle(personRadius, Color.web("blue"));
					person.setCenterX(circle.getCenterX() + personRingXRadius * Math.cos(k * Math.PI / 4));
					person.setCenterY(circle.getCenterY() + personRingYRadius * Math.sin(k * Math.PI / 4));
					person.setOpacity(0f);
					people.getChildren().add(person);
				}
			}
		}

		root.getChildren().add(intersections);
		root.getChildren().add(busMarkers);
		root.getChildren().add(people);
	}

	/**
	 * Adds a circle representing a person to an intersection.
	 * 
	 * @param x Intersection x.
	 * @param y Intersection y.
	 * @return true if successful.
	 */
	public boolean addPerson(int x, int y) {
		return modifyPerson(x, y, true);
	}

	/**
	 * Removes a circle representing a person from an intersection.
	 * 
	 * @param x Intersection x.
	 * @param y Intersection y.
	 * @return true if successful.
	 */
	public boolean removePerson(int x, int y) {
		return modifyPerson(x, y, false);
	}

	/**
	 * Helper method that can modify visibility of one person at specified
	 * intersection.
	 * 
	 * @param x   Intersection x.
	 * @param y   Intersection y.
	 * @param add true for add, false for remove person (change visibility).
	 * @return true if successful.
	 */
	private boolean modifyPerson(int x, int y, boolean add) {
		// TODO: What to do if number of people exceeds the number that can be
		// displayed?
		// Used to overflow into next intersection (to the right) when eight
		// people are already at an intersection. Though, gridPersonCount maintains the
		// correct number. Now, returns false.

		if (gridPersonCount[y][x] < 8) {
			Group people = null;

			// Because just getting index 2 from root's children list might be error prone.
			for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
				if (root.getChildrenUnmodifiable().get(i).getUserData().equals("people")) {
					people = (Group) root.getChildrenUnmodifiable().get(i);
				}
			}

			int currentNumberPeople = gridPersonCount[y][x];

			// If adding a person, the circle that should be modified should not be
			// currently used/visible. If removing a person, the circle that should be
			// modified should be currently used/visible.
			int indexModifier;

			if (add) {
				indexModifier = 0;
			} else {
				indexModifier = -1;
			}

			Circle person = (Circle) people.getChildren()
					.get(y * gridSize * 8 + x * 8 + currentNumberPeople + indexModifier);

			float fromFade;
			float toFade;
			if (add) {
				fromFade = 0f;
				toFade = 1f;
			} else {
				toFade = 0f;
				fromFade = 1f;
			}

			// Make person invisible or visible depending on add.
			FadeTransition fade = new FadeTransition(Duration.millis(1000), person);
			fade.setFromValue(fromFade);
			fade.setToValue(toFade);
			fade.play();

			if (add) {
				gridPersonCount[y][x]++;
			} else {
				gridPersonCount[y][x]--;
			}

			// TODO: Maybe return number of people being displayed at intersection after
			// operation complete?
			return true;
		}
		return false;
	}

	/**
	 * Puts bus marker around an intersection.
	 * 
	 * @param x Intersection x.
	 * @param y Intersection y.
	 * @return true if successful.
	 */
	public boolean busArrived(int x, int y) {
		return modifyBus(x, y, true);
	}

	/**
	 * Removes bus marker from an intersection.
	 * 
	 * @param x Intersection x.
	 * @param y Intersection y.
	 * @return true if successful.
	 */
	public boolean busLeft(int x, int y) {
		// Currently this method is never used because busMoved is used instead and
		// buses never vanish.
		return modifyBus(x, y, false);
	}

	/**
	 * Helper method that can modify bus marker visibility at specified
	 * intersection.
	 * 
	 * @param x       Intersection x.
	 * @param y       Intersection y.
	 * @param arrived true for visible, false for hidden.
	 * @return true if successful.
	 */
	private boolean modifyBus(int x, int y, boolean arrived) {
		// TODO: What to do if number of buses exceeds 1?

		// If a bus that is not there tries to leave, returns false.
		if (gridBusCount[y][x] != 0 || arrived) {
			boolean removeMarker = gridBusCount[y][x] == 1 && !arrived;
			boolean addMarker = gridBusCount[y][x] == 0 && arrived;

			if (removeMarker || addMarker) {

				Group busMarkers = null;

				// Because just getting index 1 from root's children list might be error prone.
				for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
					if (root.getChildrenUnmodifiable().get(i).getUserData().equals("busMarkers")) {
						busMarkers = (Group) root.getChildrenUnmodifiable().get(i);
					}
				}

				Circle busMarker = (Circle) busMarkers.getChildren().get(y * gridSize + x);

				float fromFade;
				float toFade;
				if (arrived) {
					fromFade = 0f;
					toFade = 1f;
				} else {
					toFade = 0f;
					fromFade = 1f;
				}

				// Make bus invisible or visible depending on arrived.
				FadeTransition fade = new FadeTransition(Duration.millis(1), busMarker);
				fade.setFromValue(fromFade);
				fade.setToValue(toFade);
				fade.play();
				
			}

			if (arrived) {
				gridBusCount[y][x]++;
			} else {
				gridBusCount[y][x]--;
			}

			// TODO: Maybe return number of buses at intersection?
			return true;
		}
		return false;
	}

	/**
	 * Displays animation of bus marker moving from one intersection to another.
	 * 
	 * @param x1 First intersection x.
	 * @param y1 First intersection y.
	 * @param x2 Second intersection x.
	 * @param y2 Second intersection y.
	 * @return true if successful.
	 */
	public boolean busMoved(int x1, int y1, int x2, int y2) {
		// TODO: For some reason, the bus marker does not return to a symmetrical state
		// with regard to the intersection circle.

		// Only works if a bus is actually at the start location.
		if (gridBusCount[y1][x1] > 0) {
			// If there is more than one bus at the start location.
			boolean keepBus = gridBusCount[y1][x1] > 1;

			Group busMarkers = null;

			// Because just getting index 1 from root's children list might be error prone.
			for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
				if (root.getChildrenUnmodifiable().get(i).getUserData().equals("busMarkers")) {
					busMarkers = (Group) root.getChildrenUnmodifiable().get(i);
				}
			}

			Circle startBus = (Circle) busMarkers.getChildren().get(y1 * gridSize + x1);
			Circle endBus = (Circle) busMarkers.getChildren().get(y2 * gridSize + x2);

			// Move bus marker from start to end.
			TranslateTransition translateThere = new TranslateTransition(Duration.millis(2000), startBus);
			translateThere.setFromX(startBus.getCenterX() - startBus.getRadius() * Math.PI);
			translateThere.setFromY(startBus.getCenterY() - startBus.getRadius() * Math.PI);
			translateThere.setToX(endBus.getCenterX() - endBus.getRadius() * Math.PI);
			translateThere.setToY(endBus.getCenterY() - endBus.getRadius() * Math.PI);

			// Make bus marker from start invisible.
			FadeTransition fadeOut = new FadeTransition(Duration.millis(1), startBus);
			fadeOut.setFromValue(1f);
			fadeOut.setToValue(0f);

			// Make bus marker at end visible.
			FadeTransition fadeIn = new FadeTransition(Duration.millis(1), endBus);
			fadeIn.setFromValue(0f);
			fadeIn.setToValue(1f);

			// Move invisible bus marker from end back to start.
			TranslateTransition translateBack = new TranslateTransition(Duration.millis(1), startBus);
			translateBack.setFromX(endBus.getCenterX() - endBus.getRadius() * Math.PI);
			translateBack.setFromY(endBus.getCenterY() - endBus.getRadius() * Math.PI);
			translateBack.setToX(startBus.getCenterX() - startBus.getRadius() * Math.PI);
			translateBack.setToY(startBus.getCenterY() - startBus.getRadius() * Math.PI);

			// Make the two fades occur in unison.
			ParallelTransition parallelFade = new ParallelTransition();
			parallelFade.getChildren().addAll(fadeOut, fadeIn);

			if (keepBus) {
				Circle startPhantom = new Circle(startBus.getRadius(), startBus.getFill());
				startPhantom.setCenterX(startBus.getCenterX());
				startPhantom.setCenterY(startBus.getCenterY());
				startPhantom.setStrokeType(startBus.getStrokeType());
				startPhantom.setStroke(startBus.getStroke());
				startPhantom.setStrokeWidth(startBus.getStrokeWidth());
				busMarkers.getChildren().add(startPhantom);

				FadeTransition phantomFade = new FadeTransition(Duration.millis(1), startPhantom);
				phantomFade.setFromValue(1f);
				phantomFade.setToValue(0f);
				
				FadeTransition startFadeBack = new FadeTransition(Duration.millis(1), startBus);
				startFadeBack.setFromValue(0f);
				startFadeBack.setToValue(1f);
				
				ParallelTransition parallelKeepFade = new ParallelTransition();
				parallelKeepFade.getChildren().addAll(phantomFade, startFadeBack);

				// Make first translation, the fades, the second translation, and the removal of
				// phantom with replacement by the start marker occur in order.
				SequentialTransition sequence = new SequentialTransition();
				sequence.getChildren().addAll(translateThere, parallelFade, translateBack, parallelKeepFade);

				sequence.play();
			} else {
				// Make first translation, the fades, and the second translation occur in order.
				SequentialTransition sequence = new SequentialTransition();
				sequence.getChildren().addAll(translateThere, parallelFade, translateBack);

				sequence.play();
			}

			gridBusCount[y1][x1]--;
			gridBusCount[y2][x2]++;

			return true;
		}

		return false;
	}

}
