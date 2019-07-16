package code;

public class Game {
	/*
	 * 
	 */
	private Board board;
	private String codename; // codename of character
	private boolean isLegal; // true if move is legal, false otherwise
	private boolean gameState; // True when the game is still running, false when the game ends
	private int teamTurn; // 0 = Red, 1 = Blue, 2 = Green
	private boolean assassinReleaved; // True when assassin in revealed, false when is not
	private int teamWon; //True if red won, false if blue won
	private int teamLose; //True if red won, false if blue won
	private boolean continueTurn;
	private String clue; // Current Clue
	private int count;	// Current Count
	private int redAgents; // number of redAgents not revealed
	private int blueAgents; // number of blueAgents not revealed
	private int greenAgents; // number of greenAgents not revealed
	private int assasinCounts;
	private boolean twoPlayers;

	/**
	 * constructs Game instance to run the game
	 */
	public Game(boolean twoPlayers) {
		this.twoPlayers= twoPlayers;
		teamTurn = 0;
		assassinReleaved = false;
		if(twoPlayers) {
			redAgents = 9;
			blueAgents = 8;
			greenAgents = 0;
			assasinCounts = 1;
		} else {
			redAgents = 6;
			blueAgents = 5;
			greenAgents = 5;
			assasinCounts = 2;
		}
		teamLose = -1;
		
		count = 1;
	}

	/**
	 * Sets up board and initialized the 25 locations in board with a person,
	 * codename and sets isRevealed to false
	 * 
	 * @param board
	 *            where the game is being played
	 */
	public void setUp(Board board) {
		this.board = board;
		teamTurn = 0;
		assassinReleaved = false;
		if(twoPlayers) {
			redAgents = 9;
			blueAgents = 8;
			greenAgents = 0;
		} else {
			redAgents = 6;
			blueAgents = 5;
			greenAgents = 5;
		}
		
		count = 1;
	}

	/**
	 * Tells whether move isLegal if codename of non-revealed character is used as a
	 * clue
	 * 
	 * @param clue
	 *            the clue being used
	 * @return boolean isLegal true if legal, false if not
	 */
	public boolean isLegal(String clue) {
		isLegal = true;
		for (Location l : board.getLocation()) {
			if (l.getCodename().equalsIgnoreCase(clue) && !l.getRevealed() || clue.equals("") ) {
				isLegal = false;
				return isLegal;
			}
		}
		return isLegal;
	}

	//Checks if the submitted count is legal
	public boolean isLegal(int count){
		return (count > 0 && count <= 25);
	}

	/**
	 * To be called after every turn, to update the turn
	 */
	public void updateTurn() {
		if(twoPlayers) {
			switch (teamTurn) {
			case 0:
				teamTurn = 1;
				break;
			case 1:
				teamTurn = 0;
				break;
			}
		} else {
			switch (teamTurn) {
			case 0:
				if(teamLose == 1) {
				 teamTurn = 2;
				} else {
				teamTurn = 1;
				}
				break;
			case 1:
				if(teamLose == 2) {
					teamTurn = 0;
				} else {
				teamTurn = 2;
				}
				break;
			case 2:
				if(teamLose ==0) {
					teamTurn = 1;
				} else {
				teamTurn = 0;
				}
				break;
			}
		}
	}

	/**
	 * Call this method at every end of a team's turn to check if the game ends
	 * 
	 * @return True when the game is still running, False when the game ends.
	 */
	public boolean CheckWin() {
		if(twoPlayers) {
		if (redAgents == 0 || (teamTurn == 1 && assassinReleaved)) {
			teamWon = 0;
			return false;
		} else if (blueAgents == 0 || (teamTurn == 0 && assassinReleaved)) {
			teamWon = 1;
			return false;
			}
		return true;
		} else {
			if (redAgents == 0 || (teamTurn != 0 && teamLose != 0  && assassinReleaved)) {
				teamWon = 0;
				return false;
			} else if (blueAgents == 0 || (teamTurn != 1 && teamLose != 1  && assassinReleaved)) {
				teamWon = 1;
				return false;
				}else if (greenAgents == 0 || (teamTurn != 2 && teamLose != 2 && assassinReleaved)) {
					teamWon = 2;
					return false;
				}
			return true;
		}
	}
	
	/**
	 * 	Update the game status when a location is clicked. Update the turn when the game is still running	
	 * 
	 * @param i - index of the Location that had been clicked
	 */
	public void releavedAction(int i) {
		board.setLocationRevealed(true, i);
		Location loc = getIndexLocation(i);
		//loc.setRevealed(true);
		//board.get
		decrementCount();

		if(loc.getPerson().equals("Red Agent")) {
			redRevealed();
			if(isTeamTurn() == 0 && count >0) {
				continueTurn = true;
			} else {
				continueTurn = false;
			}
			
		} else if(loc.getPerson().equals("Blue Agent")) {
			blueRevealed();
			if(isTeamTurn() == 1 && count > 0) {
				continueTurn = true;
			} else {
				continueTurn = false;
			}
		} else if(loc.getPerson().equals("Green Agent")) {
			greenRevealed();
			if(isTeamTurn() == 2 && count > 0) {
				continueTurn = true;
			} else {
				continueTurn = false;
			}
		} else if(loc.getPerson().equals("Assassin")) {
			
			//TWO ASSASIN
			if(assasinCounts == 2) {
				setAssassinReleaved(false);
				continueTurn = false;
				teamLose = teamTurn;
				assasinCounts--;
			} else{
			setAssassinReleaved(true);
			continueTurn = false;
			assasinCounts--;
			}
		} else if(loc.getPerson().equals("Innocent Bystander")) {
			continueTurn = false;
		}
		if(CheckWin() && !continueTurn) {
			updateTurn();
		}	
	}


	/**
	 * call if green agent revealed on map
	 */
	private void greenRevealed() {
		greenAgents--;
	}

	/**
	 * 
	 * @return 0 if Red team won, 1 if Blue team won, 2 if Green team won;
	 */
	public int teamWon() {
		return teamWon;
	}
	
	/**
	 * 
	 * @return 0 if Red team lose, 1 if Blue team lose, 2 if Green team lose;
	 */
	public int teamLose() {
		return teamLose;
	}

	/**
	 * sets which team lost
	 * @param i team lost (0 red, 1 blue, 2 green)
	 */
	public void setTeamLose(int i){
		teamLose = i;
	}
	

	/**
	 * Remove one red Agent
	 */
	public void redRevealed() {
		redAgents--;
	}

	/**
	 * Remove one blue Agent
	 */
	public void blueRevealed() {
		blueAgents--;
	}

	/**
	 * returns whether turn will continue
	 * @return boolean continue
	 */
	public boolean continueTurn() {
		
		return continueTurn;
	}
	
	/**
	 * gets codenames
	 * @return String codename
	 */
	public String getCodename() {
		return codename;
	}

	
	/**
	 * Generic getter and setter methods 
	 */
	
	
	public void setCodename(String codename) {
		this.codename = codename;
	}

	public boolean isGameState() {
		return gameState;
	}

	public void setGameState(boolean gameState) {
		this.gameState = gameState;
	}

	public String getClue() {
		return clue;
	}

	public void setClue(String clue) {
		this.clue = clue;
	}

	public void setCount(int count){
		if(isLegal(count)){this.count = count;}
	}

	public int getCount(){
		return count;
	}

	public void decrementCount(){
		count -= 1;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public boolean isLegal() {
		return isLegal;
	}

	public void setLegal(boolean isLegal) {
		this.isLegal = isLegal;
	}

	public int isTeamTurn() {
		return teamTurn;
	}
	
	public String teamTurn() {
		if(teamTurn == 0) {
			return "Red";
		} else if(teamTurn == 1){
			return "Blue";
		} else {
			return "Green";
		}
		
	}

	public void setTeamTurn(int i) {
		this.teamTurn = i;
	}

	public boolean isAssassinReleaved() {
		return assassinReleaved;
	}

	public void setAssassinReleaved(boolean assassinReleaved) {
		this.assassinReleaved = assassinReleaved;
	}


	public void setRedWon(int teamWon) {
		this.teamWon = teamWon;
	}
	public int getRedAgents() {
		return redAgents;
	}

	public void setRedAgents(int redAgents) {
		this.redAgents = redAgents;
	}

	public int getBlueAgents() {
		return blueAgents;
	}

	public void setBlueAgents(int blueAgents) {
		this.blueAgents = blueAgents;
	}

	public Location getIndexLocation(int i) {
		return getBoard().getLocation()[i];
		
	}
	
	public String getIndexAgent(int i) {
		return getBoard().getLocation()[i].getPerson();
	}
	
	public boolean gameState(boolean b)
	{
		return gameState;
	}

	public void setGreenAgents(int i) {
		this.greenAgents = i;
	}
	public int getGreenAgents() {

		return greenAgents;
	}
	
	

}
