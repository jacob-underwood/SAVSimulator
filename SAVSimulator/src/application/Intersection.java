package application;

public class Intersection {

	private int x, y;

	public Intersection(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @param other Intersection to compare this to.
	 * @return Number of steps to get to other assuming a bus would go directly
	 *         there.
	 */
	public int distanceFrom(Intersection other) {
		/*
		 * Distance is measured by how much a bus must go horizontally and vertically,
		 * not by diagonals. In a grid city, a bus can only go north, south, east, and
		 * west. The fastest route is the one with the least turns, so it should just go
		 * all the way horizontally, then all the way vertically.
		 */
		int distance = Math.abs(other.getX() - x) + Math.abs(other.getY() - y);

		return distance;
	}
	
	/**
	 * Checks if distance between this and other intersection are 0.
	 * 
	 * @param obj Other intersection.
	 * @return true if distance 0.
	 */
	public boolean equals(Object obj) {
		return distanceFrom((Intersection) obj) == 0;
	}
	
	/**
	 * Returns x and y coordinates of the intersections.
	 */
	public String toString() {
		return x + ", " + y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
