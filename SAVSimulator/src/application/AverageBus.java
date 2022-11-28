package application;

import java.util.LinkedList;

/**
 * Bus which determines its destination by averaging everyone's destination and then finding a real destination that is closest to the average.
 */
public class AverageBus extends LinkedBus {

	public AverageBus(Intersection location, int capacity, City city) {
		super(location, capacity, city);
		
		passengers = new LinkedList<>();
	}

	/**
	 * Sets destination of bus using the following steps:
	 * 1. Taking average of all passengers' destinations
	 * 2. Finding the passenger's destination that is closest to the generated average.
	 */
	@Override
	public void generateDestination() {
		if (passengers.size() > 0) {
			Intersection average = findAverageDestination();
			setDestination(findClosestDestination(average));
		} else {
			findPeople();
		}
	}
	
	/**
	 * Runs through all of the passengers' destinations and finds the average.
	 * 
	 * @return Intersection of passengers' average destination.
	 */
	private Intersection findAverageDestination() {
		int sumX = 0;
		int sumY = 0;
		
		for (Person passenger : passengers) {
			sumX += passenger.getDestination().getX();
			sumY += passenger.getDestination().getY();
		}
		
		// Using round should never return an index not in the grid.
		int averageX = Math.round((float) sumX / passengers.size());
		int averageY = Math.round((float) sumY / passengers.size());
		
		Intersection average = getCity().getGrid()[averageY][averageX];
		
		return average;
	}
	
}
