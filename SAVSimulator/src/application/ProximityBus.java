package application;

import java.util.LinkedList;

/**
 * Bus which has the destination of the person with the closest destination.
 */
public class ProximityBus extends Bus<LinkedList<Person>> {

	public ProximityBus(Intersection location, int capacity, City city) {
		super(location, capacity, city);
		
		passengers = new LinkedList<>();
	}

	/**
	 * Sets destination of bus based on whichever destination of the passengers is
	 * the closest to the bus' current location.
	 */
	@Override
	public void generateDestination() {
		Intersection destination = findClosestDestination(getLocation());
		setDestination(destination);
	}

}
