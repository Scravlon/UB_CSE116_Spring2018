package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Board {
	
	/** Describe Board class
	 * 
	 */
	private Location[] locations; //25 location instances
	private ArrayList<String> codenames; //codenames of Person (SHOULD BE RANDOM)
	private ArrayList<String> assignments;//assignments of Person (SHOULD BE RANDOM)
	private boolean twoPlayers; //store if the game is two players or three players
	
	/**
	 * Initiate the Board class and store the locations;s
	 */
	public Board(boolean twoPlayers) {
		this.twoPlayers = twoPlayers;
		setUpBoard();
		
	}
	
	/**
	 * creates and instantiates the board with 25 locations and random codenames
	 */
	public void setUpBoard() {
		codenames = createRandomCodenames();
		createRandomAssignments();
		locations = new Location[25];
		for(int i=0;i<locations.length;i++){
			locations[i] = new Location(codenames.get(i),assignments.get(i),false);
		}
	}
	
	/**
	 * reads gamewords.txt and stores into arraylist codenames
	 * @return ArrayList<String> containing codenames
	 */
	public ArrayList<String> readCodenames() {
		ArrayList<String> nameList= new ArrayList<String>();
		try {
			for(String name: Files.readAllLines(Paths.get("GameWords.txt"))) {
				nameList.add(name);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nameList;
	}
	
	
	/**
	 * Selects 25 distinct codenames at random from a list of codenames.
	 * 
	 * @return ArrayList<String> of 25 distinct codenames 
	 */
	public ArrayList<String> createRandomCodenames() {
		ArrayList<String> allCodenames = readCodenames();
		Collections.shuffle(allCodenames);
		codenames = new ArrayList<String>();
		for (int i=0; i<25; i++) {
            codenames.add(allCodenames.get(i));
		}
		return codenames;
	}
	
	
	
	/**
	 *  Creates a List of 25 role assignments (9 Red Agents, 8 Blue Agents, 7 Innocent Bystanders, and 1 Assassin) in random order.
	 *  Ex. { Red Agent, Blue Agent, Assassin, Red Agent, Innocent Bystander, ... , Blue Agent }
	 *  
	 * @return ArrayList<Person> of 25 randomly ordered Person objects with role assignments
	 */
	public ArrayList<String> createRandomAssignments() {
		ArrayList<String> roles = new ArrayList<String>();
		assignments = new ArrayList<>();
		if(twoPlayers) {
		for (int i=0; i<9; i++) { roles.add("Red Agent"); }
		for (int i=0; i<8; i++) { roles.add("Blue Agent"); }
		for (int i=0; i<7; i++) { roles.add("Innocent Bystander"); }
		roles.add("Assassin");
		}else {
			for (int i=0; i<6; i++) { roles.add("Red Agent"); }
			for (int i=0; i<5; i++) { roles.add("Blue Agent"); }
			for (int i=0; i<5; i++) { roles.add("Green Agent"); }
			for (int i=0; i<7; i++) { roles.add("Innocent Bystander"); }
			roles.add("Assassin");
			roles.add("Assassin");

		}
		Collections.shuffle(roles);
		for (int i=0; i<roles.size(); i++) {
			
			assignments.add(roles.get(i));
		}
		return assignments;
	}
	
	/**
	 * getter for location[] 
	 * @return Location[] locations
	 */
	public Location[] getLocation() {
		return locations;
	}
	
	/**
	 * sets location revealed
	 * @param b boolean whether revealed
	 * @param index int index of location
	 */
	public void setLocationRevealed(Boolean b, int index) {
		locations[index].setRevealed(b);
	}
	
	/**
	 * gets codenames
	 * @return ArrayList<String> codenames
	 */
	public ArrayList<String> getcodeNames(){
		return codenames;
	}
	
	/**
	 * gets assignments
	 * @return Arraylist<String> assignments
	 */
	public ArrayList<String> getAssignment(){
		return assignments;
	}
	
}
