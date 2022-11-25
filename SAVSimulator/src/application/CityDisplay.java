package application;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * Deals with graphical output.
 *
 */
public class CityDisplay {

	private Group root;

	public CityDisplay(Group root) {
		this.root = root;
	}

	/**
	 * Sets up the intersection layout. It creates the intersection circles, the
	 * rings that represent the presence of the bus, and the people surrounding the
	 * intersections. Then, hides everything but the intersections.
	 * 
	 * @param screenSize      Size of screen in pixels.
	 * @param gridSize        How many intersections in one side. Square to get
	 *                        total number.
	 * @param intersectRadius Radius of circles representing intersections.
	 * @param extraBoundary   Total extra boundary added in a certain direction.
	 *                        Halve to get cushion added on a singular side.
	 * @param ringDistance    Radius difference from intersection to ring.
	 * @param ringStrokeSize  Stroke size is added on top of the radius.
	 * @param personDistance  Distance from ring to center of person.
	 * @param personRadius    Radius of person circle.
	 */
	public void setupCity(int screenSize, int gridSize, int intersectRadius, int extraBoundary, int ringDistance,
			int ringStrokeSize, int personDistance, int personRadius) {
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
	}
	
	/**
	 * Adds a circle representing a person to an intersection.
	 * 
	 * @param x Intersection x.
	 * @param y Intersection y.
	 * @return true if successful.
	 */
	public boolean addPerson(int x, int y) {
		return false;
	}
	
	/**
	 * Removes a circle representing a person from an intersection.
	 * 
	 * @param x Intersection x.
	 * @param y Intersection y.
	 * @return true if successful.
	 */
	public boolean removePerson(int x, int y) {
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
		return false;
	}
	
	/**
	 * Removes bus marker from an intersection.
	 * 
	 * @param x Intersection x.
	 * @param y Intersection y.
	 * @return true if successful.
	 */
	public boolean busLeft(int x, int y) {
		return false;
	}
	
	/**
	 * OPTIONAL: Displays animation of bus marker moving from one intersection to another.
	 * NOTE: If implemented, it would still use busArrived and busLeft, but they should be private and this would be the public equivalent.
	 * 
	 * @param x1 First intersection x.
	 * @param y1 First intersection y.
	 * @param x2 Second intersection x.
	 * @param y2 Second intersection y.
	 * @return true if successful.
	 */
	public boolean busMoved(int x1, int y1, int x2, int y2) {
		return false;
	}

}