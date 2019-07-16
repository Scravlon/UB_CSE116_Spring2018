package EventListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import code.Game;
import gui.GUI;

/**
 * Handle when the locations are clicked
 * 
 * @author kokhaoyo
 *
 */
public class locationButtonListener implements ActionListener {

	JButton jbut;
	int locationIndex;
	Game game;
	GUI gui;
	

	/**
	 * constructions listener for location button
	 * @param jl jbutton that will be clicked
	 * @param i int tells the index of specific location on grid
	 * @param game Game instance running
	 * @param gui GUI instance running
	 */
	public locationButtonListener(JButton jl, int i, Game game, GUI gui) {
		
		this.jbut = jl;
		this.locationIndex = i;
		this.game = game;
		this.gui = gui;
		// TODO Auto-generated constructor stub
		
	}

	/**
	 * triggers when button clicked and depending on what is on the location responds correctly
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		/*jbut.setText(game.getIndexAgent(locationIndex));
		
		//CHECK LEGAL NEEDED HERE
		//if(legal).......
		game.releavedAction(locationIndex);
		jbut.setEnabled(false);
		*/
		gui.annoy();
		game.releavedAction(locationIndex);
		if(game.CheckWin()) {
			gui.showDialog();
			gui.clearPanel();
		}
		if(game.continueTurn()) {
			gui.updateCount();
			gui.outputClueAndCount();
			gui.setupLocation();
			gui.setupInfo();
		}
		else if (!game.CheckWin())
		{
			gui.endGame();
			JOptionPane.showMessageDialog(GUI.frame, "GAME OVER");
		}
		else {
		gui.setupSpyMasterLocation();
		gui.updateSpyMasterTurn();
		}
		gui.packFrame();
		System.out.println("Console Log");
		System.out.println("=====================================");
		System.out.println(game.getIndexLocation(locationIndex).getCodename() + " is Clicked. It is " + 
		game.getIndexAgent(locationIndex));
		System.out.println(game.teamTurn() + " Turn  |  Game State: " + game.CheckWin() + " | "
				+ "Continue Turn: " + game.continueTurn());
		System.out.println("Count: " + game.getCount() + ", Blue Agents Left: " + game.getBlueAgents() 
		+", Red Agents Left: " + game.getRedAgents()
		+ " , Green Agents Left: " + game.getGreenAgents());;
		System.out.println(game.teamLose());
		System.out.println("=====================================");
		System.out.println();
		//System.out.println(game.getIndexLocation(locationIndex).getRevealed());
		
	}

}
