package application;

/**
 * Bus which determines its destination by averaging everyone's destination and then finding a real destination that is closest to the average.
 */
public class AverageBus extends Bus {

	public AverageBus(Intersection location, int capacity) {
		super(location, capacity);
		
	}

	/**
	 * Sets destination of bus using the following steps:
	 * 1. Taking average of all passengers' destinations
	 * 2. Finding the passenger's destination that is closest to the generated average.
	 */
	@Override
	public void generateDestination() {
		
	}
	
	/**
	 * Runs through all of the passengers' destinations and finds the average.
	 * 
	 * @return Intersection of passengers' average destination.
	 */
	private Intersection findAverageDestination() {
		return null;
	}
	
	
	/**
	 * Finds closest destination of a passenger from input intersection.
	 * 
	 * @param intersection Intersection that may or may not be a destination of a passenger.
	 * @return Intersection closest to intersection that is a destination of a passenger.
	 */
	private Intersection findClosestDestination(Intersection intersection) {
		return null;
	}
	
}
