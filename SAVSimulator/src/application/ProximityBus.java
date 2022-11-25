package application;

/**
 * Bus which has the destination of the person with the closest destination.
 */
public class ProximityBus extends Bus {

	public ProximityBus(Intersection location, int capacity) {
		super(location, capacity);
	}

	/**
	 * Sets destination of bus based on whichever destination of the passengers is
	 * the closest to the bus' current location.
	 */
	@Override
	public void generateDestination() {

	}

	/**
	 * Runs through all of the passengers' destinations to find the closest one.
	 * 
	 * @return The closest Intersection destination.
	 */
	private Intersection findClosestDestination() {
		return null;
	}

}
