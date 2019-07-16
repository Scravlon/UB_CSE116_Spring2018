
package EventListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import gui.GUI;

/**
 * 	Start a new Game 
 * 
 * @author kokhaoyo
 *
 */
public class newGameListener implements ActionListener {

	GUI gui;
	boolean twoPlayerGame;
	/**
	 * constructor for listener
	 * @param gui GUI instance running
	 */
	public newGameListener(GUI gui) {
		this.gui = gui;
	}

	/**
	 * constructor for listener with possible 3 player game
	 * @param gui2 GUI instance running
	 * @param b two player game (true 2 player game, false 3 player game)
	 */
	public newGameListener(GUI gui2, boolean b) {
		this.gui = gui2;
		twoPlayerGame = b;
		gui.setPlayers(b);
	}

	/**
	 * triggered when event happens(new game is selected from menu)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.annoy();
		gui.Quit();
		GUI gui1 = new GUI();
		gui1.setPlayers(twoPlayerGame);
		gui1.Init();
		

	}

}
