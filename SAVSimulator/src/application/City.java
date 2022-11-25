package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Simulation driver.
 */
public class City {

	// List of all people in simulation; number changes as people reach their
	// destination and are thus removed from the simulation.
	private LinkedList<Person> people;
	// List of all buses; number does not change after instantiated.
	private Bus[] buses;
	// All Intersections in a city.
	private Intersection[][] grid;
	
	// Current step (time) the simulation is on. For example, each step could be 1
	// minute.
	private int step;

	// Stores CityDisplay object to manipulate graphical output.
	private CityDisplay cityDisplay;

	public City(CityDisplay cityDisplay) {

	}

	/**
	 * Runs simulation by 1. Moving buses 2. Having buses pick up people 3. Setting
	 * buses' destinations 4. Handling all other fundamental tasks in a step-wise
	 * manner
	 * 
	 * @return Current step.
	 */
	public int runSimulation() {
		// while loop using long System.currentTimeMillis(), modulo to create discrete
		// steps.
		return -1;
	}

	/**
	 * Creates all people, buses, and intersections at the start of the program.
	 */
	public void setupSimulation() {

	}
	
	/**
	 * Gets the intersection one over from the supplied intersection in the given direction.
	 * 
	 * @param intersection Intersection to use as starting location.
	 * @param direction Direction returned Intersection should be in.
	 * @return Intersection one over in given direction.
	 * @throws ArrayIndexOutOfBoundsException If called on an edge or corner intersection in the grid, one cannot go off the grid.
	 */
	public Intersection oneOver(Intersection intersection, Direction direction) throws ArrayIndexOutOfBoundsException {
		return null;
	}
	
	/**
	 * Returns list of everyone not in a bus at a certain intersection.
	 * 
	 * @param intersection Intersection to check for people at.
	 * @return People at Intersection in a PriorityQueue based on how long they have been waiting.
	 */
	public PriorityQueue<Person> getPeopleAtIntersection(Intersection intersection) {
		return null;
	}
	
	/**
	 * @return the grid
	 */
	public Intersection[][] getGrid() {
		return grid;
	}

}
