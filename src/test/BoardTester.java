package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import code.Board;
import code.Location;


public class BoardTester {
	
	
	/**
	 * checks that setUpBoard class was correctly instantiated
	 */
	@Test
	public void checkBoard() {
		Board b = new Board(true);
		b.readCodenames();
		b.setUpBoard();
		assertEquals(25,b.getLocation().length);
		//System.out.println(b.codenames.get(0));
		Location L1 = new Location(b.getcodeNames().get(0),b.getAssignment().get(0),false);
		assertEquals(L1.toString(),b.getLocation()[0].toString());
	}
	
	/**
	 * Checks that codenames were read correctly
	 */
	@Test
	public void checkCodename() {
		Board b = new Board(true);
		ArrayList<String> codenames = b.readCodenames();
		assertTrue(codenames.contains("AZTEC"));
		assertFalse(codenames.contains("fjhdsif"));
		assertTrue(codenames.contains("HELICOPTER"));
	}
	
	/**
	 * Greg - compares two randomly generated codename lists to each other
	 *		 to assert that they are not the same or not in the same order
	 *		 runs 100 checks
	 */
	@Test
	public void checkRandom() {
		Board b = new Board(true);
		for (int i=0; i<10; i++) {
			Object[] random0 = b.createRandomCodenames().toArray();
			for (int j=0; j<10; j++) {
				Object[] random1 = b.createRandomCodenames().toArray();
				assertFalse(Arrays.equals(random0, random1));
			}
		}
	}
	
	/**
	 * Greg - compares two randomly ordered role lists to each other
	 *		 to assert that they are not in the same order (not same)
	 *		 runs 100 checks
	*/
	@Test
	public void checkAssignment() {
		Board b = new Board(true);
		for (int i=0; i<10; i++) {
			Object[] random0 = b.createRandomAssignments().toArray();
			for (int j=0; j<10; j++) {
				Object[] random1 = b.createRandomAssignments().toArray();
				assertFalse(Arrays.equals(random0, random1));
			}
		}
	}

}
