package application;

import java.util.Comparator;

/**
 * Compares two intersections in how far they are from another intersection.
 */
public class RelativeIntersectionComparator implements Comparator<Intersection> {

	// Intersection that the input variables for compare will be compared to.
	private Intersection primary;
	
	/**
	 * @param primary
	 */
	public RelativeIntersectionComparator(Intersection primary) {
		this.primary = primary;
	}
	
	/**
	 * Compares the two inputed intersections to the primary intersection. If o1 is closer than o2, the return will be negative.
	 */
	@Override
	public int compare(Intersection o1, Intersection o2) {
		return primary.distanceFrom(o1) - primary.distanceFrom(o2);
	}

}
