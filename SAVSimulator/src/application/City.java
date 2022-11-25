package application;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Simulation driver.
 */
public class City {

	// List of all people in simulation; number changes as people reach their
	// destination and are thus removed from the simulation.
	private LinkedList<Person> people;
	// List of all buses; number does not change after instantiated.
	private Bus[] buses;
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
	 * Creates all people and buses at the start of the program.
	 */
	public void setupSimulation() {

	}

}
