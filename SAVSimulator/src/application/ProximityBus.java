package application;

import java.util.LinkedList;

public class ProximityBus extends Bus {

	LinkedList<Person> passengers;
	
	public ProximityBus(Intersection location, int capacity) {
		super(location, capacity);
	}

	/**
	 * Sets
	 */
	@Override
	public void setDestination() {
		
	}
	
	/**
	 * Runs through all of the passengers' destinations to find the closest one.
	 * 
	 * @return The closest Intersection destination.
	 */
	public Intersection findClosestDestination() {
		return null;
	}

}
