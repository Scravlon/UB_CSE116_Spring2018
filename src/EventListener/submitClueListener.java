package EventListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import code.Game;
import gui.GUI;


/**
 * Check if the clue and count is valid, if so process to team turn.
 * 
 * @author kokhaoyo
 *
 */
public class submitClueListener implements ActionListener {

	 GUI gui;
	 JButton jb;
	 Game g;
	
	 /**
	  * creates listener
	  * @param gui GUI instance running
	  * @param jb Jbutton submit button
	  * @param g Game instance running
	  */
	public submitClueListener(GUI gui, JButton jb, Game g) {
		this.gui = gui;
		this.jb = jb;
		this.g = g;
	}
	

	/**
	 * triggered when submit button clicked
	 * checks whether clue is valid then displays on next panel
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.annoy();
		//CHECK CLUE
		String clu = gui.clue.getText();
		String c = gui.count.getText();
		int coun = 0;
		if(c.length() != 0)
		{
			try 
			{
				coun = Integer.parseInt(c);
				g.setCount(coun);
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(GUI.frame, "Invalid count, not an int");
				return;
			}
		}
		
		if(!g.isLegal(clu))
		{
			JOptionPane.showMessageDialog(GUI.frame, "Invalid clue please try again");
		}
		else if(!g.isLegal(coun))
		{
			JOptionPane.showMessageDialog(GUI.frame, "Invalid count please try again");
		}
		else
		{
			gui.clearPanel();
			gui.outputClueAndCount();
			gui.updateSpyMasterTurn();
			gui.setupLocation();
			gui.packFrame();
		}
	
	}

}
