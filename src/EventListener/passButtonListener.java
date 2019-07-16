package EventListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import gui.GUI;
import code.Game;

/**
 * Check if the clue and count is valid, if so process to team turn.
 *
 * @author kokhaoyo
 *
 */
public class passButtonListener implements ActionListener {

    GUI gui;
    Game game;

    /**
     * constructor for pass button listener
     * @param gui GUI instance running
     * @param game Game instance running
     */
    public passButtonListener(GUI gui, Game game) {
        this.gui = gui;
        this.game = game;
    }

    /**
     * triggered when pass button clicked (ends turn)
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
    		gui.annoy();
        gui.clearPanel();
        game.updateTurn();
        gui.showDialog();
        gui.setupSpyMasterLocation();
        gui.updateSpyMasterTurn();
        gui.packFrame();

    }
}

