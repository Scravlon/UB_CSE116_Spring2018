package main;

import javax.swing.SwingUtilities;

import gui.GUI;

public class Driver implements Runnable {
	
	//private Board _board;
	//private Game _game;
	
	


	public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	runGUI();  
            }
        });
    }

	public static void runGUI() {
		GUI gui = new GUI();
		gui.Init();
	}

	@Override
	public void run() {
		// overridden
	}

}
