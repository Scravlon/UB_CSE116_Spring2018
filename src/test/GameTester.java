package test;

import static org.junit.Assert.*;

import org.junit.Test;

import code.Board;
import code.Game;
import code.Location;

public class GameTester {
	
	
	/**Tests setup to make sure the 25 locations of the array are filled with a codename and person, and isRevealed is set to false
	 * -Ariel
	 */
	@Test
	public void checkSetUp(){
		Game gameState = new Game(true);
		Board board = new Board(true);
		gameState.setUp(board);
		//checks if red turn
		assertEquals(0,gameState.isTeamTurn());
		//checks if each location is filled correctly
		assertEquals(25, board.getLocation().length);
		for(Location l : board.getLocation())
		{
			assertNotEquals(l.getCodename(), "");
			assertNotNull(l.getCodename());
			assertFalse(l.getRevealed());
			assertNotNull(l.getPerson());
		}
	}
	
	/**	Tests if isLegal returns legal moves true and illegal false
	 * 	-Ariel
	 */
	@Test
	public void checkLegal() {
		Game gameState = new Game(true);
		Board board = new Board(true);

		gameState.setUp(board);
		//random clues
		assertTrue(gameState.isLegal("ehiwhio"));
		assertTrue(gameState.isLegal("not a clue"));
		assertFalse(gameState.isLegal(""));
		assertTrue(gameState.isLegal("he's dead"));
		
		//should be legal if person is revealed
		gameState.getBoard().getLocation()[0].setRevealed(true);
		//System.out.println("GAME TEST " + gameState.board.locations[0].codename);
		//System.out.println("GAME TEST " + gameState.isLegal(gameState.board.locations[0].codename));
		
		assertTrue(gameState.isLegal(gameState.getBoard().getLocation()[0].getCodename()));
		
		//illegal to call 
		assertFalse(gameState.isLegal(board.getcodeNames().get(2)));
		
		
	}
	/**
	 * checks if update turn correctly switches the turn from red to blue and back
	 */
	@Test
	public void checkUpdate() {
		Game gamestate = new Game(false);
		assertEquals(0,gamestate.isTeamTurn());
		gamestate.updateTurn();
		assertEquals(1,gamestate.isTeamTurn());
		gamestate.updateTurn();
		assertEquals(2,gamestate.isTeamTurn());
		
	}
	
	/**
	 * Yong 
	 * checks if teamWon returns that the correct team won
	 */
	@Test
	public void checkWin() {
		Game gameState = new Game(true);
		gameState.setRedAgents(0); 
		gameState.CheckWin();
		//Set up for Red Wins
		assertEquals(0,gameState.teamWon());
		
		gameState.setRedAgents(1); 
		gameState.setBlueAgents(0); 
		gameState.CheckWin();
		//Set up for Blue Wins
		assertEquals(1,gameState.teamWon());
		
		Game gameState1 = new Game(true);
		gameState1.setTeamTurn(0);
		gameState1.setAssassinReleaved(true);
		gameState1.CheckWin();
		//Set up when Red Revealed Assassin 
		assertEquals(1,gameState.teamWon());
		
		gameState1.setTeamTurn(1);
		gameState1.setAssassinReleaved(true);
		gameState1.CheckWin();
		//Set up when Blue Revealed Assassin
		assertEquals(1,gameState.teamWon());
	
	}

	@Test
	public void check3assignments(){
		Game gameState = new Game(false);
		Board board = new Board(false);
		gameState.setUp(board);
		int redCount = 0;
		int greenCount = 0;
		int blueCount = 0;
		int assasinCount = 0;
		int innocentCount = 0;
		
		for(int i = 0; i < 25; i++) {
			String j = gameState.getBoard().getLocation()[i].getPerson();
			switch (j) {
			case "Red Agent":
				redCount++;
				break;
			case "Blue Agent":
				blueCount++;
				break;
			case "Green Agent":
				greenCount++;
				break;
			case "Innocent Bystander":
				innocentCount++;
				break;
			case "Assassin":
				assasinCount++;
				break;
			}
		}
		assertEquals(6, redCount);
		assertEquals(5, blueCount);
		assertEquals(5, greenCount);
		assertEquals(2, assasinCount);
		assertEquals(7, innocentCount);
	}

	// Greg
	@Test
	public void check3winassassin(){
		Game game = new Game(false);
		Board board = new Board(false);
		game.setUp(board);
		// finding assassins on board
		Location[] loc = board.getLocation();
		int idx1 = -1; int idx2 = -1;
		for (int i=0; i<loc.length; i++) {
			if (loc[i].getPerson().equals("Assassin") && idx1==-1) {
				idx1 = i;
			}
			else if (loc[i].getPerson().equals("Assassin")) {
				idx2 = i;
			}
		} 
		// first assassin revealed
		game.releavedAction(idx1);
		assertFalse(game.isAssassinReleaved());
		assertEquals(0, game.teamLose());
		assertTrue("Game should continue.", game.CheckWin());
		// second assassin revealed
		game.releavedAction(idx2);
		assertTrue(game.isAssassinReleaved());
		assertEquals(2, game.teamWon());
		assertFalse("Game shoud end.", game.CheckWin());
	}

	//Ariel
	@Test
	public void check3boardwin(){
		Game game = new Game(false);
		Board board = new Board(false);
		game.setUp(board);
		//green win no agents 
		game.setGreenAgents(0);
		assertFalse("Game is over", game.CheckWin());
		assertEquals("Green did not win with no agents left", 2, game.teamWon());
		//red win no agents
		game = new Game(false);
		game.setRedAgents(0);
		assertEquals("Red did not win with no agents left", 0, game.teamWon());
		//blue win no agents
		game = new Game(false);
		game.setBlueAgents(0);
		assertEquals("Blue did not win with no agents left", 0, game.teamWon());
		//if two teams killed by assassin
		game = new Game(false);
		game.setAssassinReleaved(true);
		game.updateTurn();
		game.setAssassinReleaved(true);
		game.updateTurn();
		assertFalse("game over when 2 assassins picked", game.CheckWin());
		assertEquals("Red did not win even though all other teams lost", 0, game.teamWon());
	}

	//Kyle
	@Test
	public void check3rotation(){
		Game game = new Game(false);
		Board board = new Board(false);
		//Red teams turn
		game.setTeamLose(0);
		game.updateTurn(); //Blue
		game.updateTurn(); //Green
		game.updateTurn(); //should be blue
		assertEquals(1,game.isTeamTurn());
		game.updateTurn(); //green
		assertEquals(2,game.isTeamTurn());
		game.updateTurn();
		assertEquals(1,game.isTeamTurn());


		game = new Game(false);
		board = new Board(false);
		//Red teams turn
		game.updateTurn(); //Blue
		game.setTeamLose(1);
		game.updateTurn(); //Green
		game.updateTurn(); //Red
		game.updateTurn(); //Should be green
		assertEquals(2,game.isTeamTurn());
		game.updateTurn(); //Should be red
		assertEquals(0,game.isTeamTurn());
		game.updateTurn(); //Should be green
		assertEquals(2,game.isTeamTurn());

		game = new Game(false);
		board = new Board(false);
		//Red teams turn
		game.updateTurn(); //Blue
		game.updateTurn(); //Green
		game.setTeamLose(2);
		game.updateTurn(); //Red
		game.updateTurn(); //Should be blue
		assertEquals(1,game.isTeamTurn());
		game.updateTurn(); //Should be red
		assertEquals(0,game.isTeamTurn());
		game.updateTurn(); //Should be blue
		assertEquals(1,game.isTeamTurn());
	}
}
