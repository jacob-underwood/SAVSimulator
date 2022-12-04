package application;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
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
	private ArrayList<ArrayList<LinkedList<Circle>>> busGrid;

	private int screenSize, intersectRadius, extraBoundary, ringDistance, ringStrokeSize, personDistance, personRadius;

	/**
	 * @param root            The root of the graphical output.
	 * @param gridSize        How many intersections are the square grid city on one
	 *                        side.
	 * @param screenSize      Size of screen in pixels.
	 * @param intersectRadius Radius of circles representing intersections.
	 * @param extraBoundary   Total extra boundary added in a certain direction.
	 *                        Halve to get cushion added on a singular side.
	 * @param ringDistance    Radius difference from intersection to ring.
	 * @param ringStrokeSize  Stroke size is added on top of the radius.
	 * @param personDistance  Distance from ring to center of person.
	 * @param personRadius    Radius of person circle.
	 */
	public CityDisplay(Group root, int gridSize, int screenSize, int intersectRadius, int extraBoundary,
			int ringDistance, int ringStrokeSize, int personDistance, int personRadius) {
		this.root = root;
		this.gridSize = gridSize;

		this.gridPersonCount = new int[gridSize][gridSize];
		this.busGrid = new ArrayList<>(gridSize);
		for (int i = 0; i < gridSize; i++) {
			this.busGrid.add(i, new ArrayList<>(gridSize));
			for (int j = 0; j < gridSize; j++) {
				this.busGrid.get(i).add(j, new LinkedList<Circle>());
			}
		}

		this.screenSize = screenSize;
		this.intersectRadius = intersectRadius;
		this.extraBoundary = extraBoundary;
		this.ringDistance = ringDistance;
		this.ringStrokeSize = ringStrokeSize;
		this.personDistance = personDistance;
		this.personRadius = personRadius;
	}

	/**
	 * Sets up the intersection layout. It creates the intersection circles, the
	 * rings that represent the presence of the bus, and the people surrounding the
	 * intersections. Then, hides everything but the intersections.
	 */
	public void setupCity() {
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

//				Circle ring = new Circle(intersectRadius + ringDistance, Color.web("transparent"));
//				ring.setCenterX(circle.getCenterX());
//				ring.setCenterY(circle.getCenterY());
//				ring.setStrokeType(StrokeType.OUTSIDE);
//				ring.setStroke(Color.web("red"));
//				ring.setStrokeWidth(ringStrokeSize);
//				ring.setOpacity(0f);
//				busMarkers.getChildren().add(ring);
				
//				Circle test = new Circle(25, Color.GREEN);
//				test.setCenterX(350);
//				test.setCenterY(350);
//				Group a = new Group();
//				a.setUserData("a");
//				a.getChildren().add(test);
//				
//				root.getChildren().add(a);
//				
//				
//				TranslateTransition translate2 = new TranslateTransition(Duration.millis(10000), test);
//				translate2.setFromX(350);
//				translate2.setFromY(350);
//				translate2.setToX(300);
//				translate2.setToY(300);
//				translate2.setByX(200);
//				translate2.setByY(200);
//				translate2.play();

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
//		System.out.println("ADD PERSON.");
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

		if (gridPersonCount[y][x] == 0 && !add) {
			return false;
		}
		
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

			// TODO: Is this error prone? Should user data be used?
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

			// TODO: Maybe return number of people being displayed at intersection after
			// operation complete?
		}
		
		if (add) {
			gridPersonCount[y][x]++;
		} else {
			gridPersonCount[y][x]--;
		}
		
		return true;
	}

	/**
	 * Puts bus marker around an intersection.
	 * 
	 * @param x Intersection x.
	 * @param y Intersection y.
	 * @return true if successful.
	 */
	public boolean createBus(int x, int y) {
		Group busMarkers = null;
		Group intersections = null;

		// Because just getting indexes 0 and 1 from root's children list might be error
		// prone.
		for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
			if (root.getChildrenUnmodifiable().get(i).getUserData().equals("busMarkers")) {
				busMarkers = (Group) root.getChildrenUnmodifiable().get(i);
			}
			if (root.getChildrenUnmodifiable().get(i).getUserData().equals("intersections")) {
				intersections = (Group) root.getChildrenUnmodifiable().get(i);
			}
		}

		Circle circle = (Circle) intersections.getChildren().get(y * gridSize + x);

		Circle ring = new Circle(intersectRadius + ringDistance, Color.web("transparent"));
		ring.setCenterX(circle.getCenterX());
		ring.setCenterY(circle.getCenterY());
		ring.setStrokeType(StrokeType.OUTSIDE);
		ring.setStroke(Color.web("red"));
		ring.setStrokeWidth(ringStrokeSize);
		busMarkers.getChildren().add(ring);

		busGrid.get(y).get(x).add(ring);

		return true;
//		return modifyBus(x, y, true);
	}

	/**
	 * Removes bus marker from an intersection.
	 * 
	 * @param x Intersection x.
	 * @param y Intersection y.
	 * @return true if successful.
	 */
	public boolean removeBus(int x, int y) {
		// Currently this method is never used because busMoved is used instead and
		// buses never vanish.

		if (busGrid.get(y).get(x).size() > 0) {
			Group busMarkers = null;

			Circle busToRemove = busGrid.get(y).get(x).get(0);

			// Because just getting index 1 from root's children list might be error prone.
			for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
				if (root.getChildrenUnmodifiable().get(i).getUserData().equals("busMarkers")) {
					busMarkers = (Group) root.getChildrenUnmodifiable().get(i);
				}
			}

			for (int i = 0; i < busMarkers.getChildrenUnmodifiable().size(); i++) {
				if (busMarkers.getChildrenUnmodifiable().get(i) == busToRemove) {
					busGrid.get(y).get(x).remove(0);
					busMarkers.getChildren().remove(busToRemove);
				}
			}

			return true;
		}

		return false;

//		return modifyBus(x, y, false);
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
		if (busGrid.get(y1).get(x1).size() > 0) {
			Group intersections = null;

			Circle busToMove = busGrid.get(y1).get(x1).get(0);

			// Because just getting index 0 from root's children list might be error prone.
			for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
				if (root.getChildrenUnmodifiable().get(i).getUserData().equals("intersections")) {
					intersections = (Group) root.getChildrenUnmodifiable().get(i);
				}
			}
			
			Circle startIntersection = (Circle) intersections.getChildrenUnmodifiable().get(y1 * gridSize + x1);
			Circle endIntersection = (Circle) intersections.getChildrenUnmodifiable().get(y2 * gridSize + x2);
			
			TranslateTransition translate = new TranslateTransition(Duration.millis(100), busToMove);
			translate.setByX(endIntersection.getCenterX() - startIntersection.getCenterX());
			translate.setByY(endIntersection.getCenterY() - startIntersection.getCenterY());
//			translate.setFromX(startIntersection.getCenterX() - busToMove.getRadius() * Math.PI);
//			translate.setFromY(startIntersection.getCenterY() - busToMove.getRadius() * Math.PI);
//			translate.setToX(endIntersection.getCenterX() - busToMove.getRadius() * Math.PI);
//			translate.setToY(endIntersection.getCenterY() - busToMove.getRadius() * Math.PI);
			translate.play();
			
			
			
			busGrid.get(y1).get(x1).remove(0);
			busGrid.get(y2).get(x2).add(busToMove);

			return true;
		}
		

		return false;
	}

}
