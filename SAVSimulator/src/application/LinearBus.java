package application;

import java.util.PriorityQueue;

/**
 * Bus that determines location based on who has been in the bus the longest.
 */
public class LinearBus extends Bus<PriorityQueue<Person>> {

	public LinearBus(Intersection location, int capacity, City<LinearBus> city) {
		super(location, capacity, city);

		passengers = new PriorityQueue<>();
	}

	/**
	 * Sets destination of bus to that of the person waiting the longest on the bus.
	 */
	@Override
	public void generateDestination() {
		if (passengers.size() > 0) {
			Intersection destination = getDestinationOfPersonOnBusLongest();
			setDestination(destination);
		} else {
			findPeople();
		}
	}

	/**
	 * Looks at every passenger and determines who has been riding the bus for the
	 * longest, returning their destination. Assumes people are on the bus.
	 * 
	 * @return Intersection of destination.
	 */
	private Intersection getDestinationOfPersonOnBusLongest() {
		return passengers.peek().getDestination();
	}

}
