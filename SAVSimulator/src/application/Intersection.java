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
		int distance = (other.getX() - x) + (other.getY() - y);

		return Math.abs(distance);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
