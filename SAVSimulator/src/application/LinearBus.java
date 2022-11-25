package application;

/**
 * Bus that determines location based on who has been in the bus the longest.
 */
public class LinearBus extends Bus {

	public LinearBus(Intersection location, int capacity) {
		super(location, capacity);

	}

	/**
	 * Sets destination of bus to that of the person waiting the longest on the bus.
	 */
	@Override
	public void generateDestination() {

	}

	/**
	 * Looks at every passenger and determines who has been riding the bus for the
	 * longest, returning their destination.
	 * 
	 * @return Intersection of destination.
	 */
	private Intersection getDestinationOfPersonOnBusLongest() {
		return null;
	}

}
