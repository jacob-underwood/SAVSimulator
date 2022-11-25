package application;

public class Intersection {

	private int x, y;
	
	public Intersection(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @param other Intersection to compare this to.
	 * @return Number of steps to get to other assuming a bus would go directly there.
	 */
	public int distanceFrom(Intersection other) {
		return -1;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
