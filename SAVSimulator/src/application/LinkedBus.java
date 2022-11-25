package application;

import java.util.LinkedList;

/**
 * Child class of bus that uses a LinkedList for passengers.
 */
public abstract class LinkedBus extends Bus<LinkedList<Person>> {

	public LinkedBus(Intersection location, int capacity, City city) {
		super(location, capacity, city);
		
		passengers = new LinkedList<>();
	}

	/**
	 * Runs through all of the passengers' destinations to find the closest to the given intersection.
	 * 
	 * @param intersection Base intersection to compare all of the passengers' destinations to. It may or may not be a destination of a passenger.
	 * @return Intersection closest to input intersection that is a destination of a passenger.
	 */
	protected Intersection findClosestDestination(Intersection intersection) {
		// TODO: How do I make this method more abstract by not having it in two child classes but manage to make it work.
		Person chosen = passengers.get(0);
		
		for (int i = 1; i < passengers.size(); i++) {
			// Person to compare chosen to.
			Person other = passengers.get(i);
			
			// Distance of other's destination from current bus location.
			int otherDistance = getLocation().distanceFrom(other.getDestination());
			// Distance of chosen's distance from current bus location.
			int chosenDistance = getLocation().distanceFrom(chosen.getDestination());
			
			if (otherDistance < chosenDistance) {
				chosen = other;
			} else if (otherDistance == chosenDistance) {
				
				// If other has been on bus longer, prioritize them.
				if (other.getTimeInBus() > chosen.getTimeInBus()) {
					chosen = other;
				}
				
			}
		}
		
		return chosen.getDestination();
	}
	
}
