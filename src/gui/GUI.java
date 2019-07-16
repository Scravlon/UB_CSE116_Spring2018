 package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.glass.events.KeyEvent;

import EventListener.locationButtonListener;
import EventListener.newGameListener;
import EventListener.passButtonListener;
import EventListener.submitClueListener;
import code.Board;
import code.Game;
import code.Location;

public class GUI {
	
	public static JFrame frame;
	private JPanel shiftingPanel;
	private JPanel infoPanel;
	private JPanel clueAndCount;
	public JTextField clue;
	public JTextField count;
	private Board board;
	private Game game;
	public Boolean spyMasterTurn; //True if it is spyMaster's Turn, otherwise not
	private boolean twoPlayers = true;
	
	
	/*
		Initiate the game
	}*/
	public void Init() {
		game = new Game(twoPlayers);
		board = new Board(twoPlayers);
		frame = new JFrame("Codename");
		game.setUp(board);
		spyMasterTurn = true;
		
		createMenu();
		setupSpyMasterLocation();
		setupInfo();
		//setupLocation();
		//frame.pack();
		frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
		showDialog();

	}
	
	/**
	 * Update the number of Players
	 * 
	 * @param b True if this is a two players game, False if this is a three players game
	 */
	public void setPlayers(boolean b) {
		twoPlayers = b;
	}
	/**
	 * Update the GUI frame
	 */
	public void packFrame() {
		
		//frame.pack();
		frame.revalidate();
		frame.repaint();
		
	}
	
	/**
	 * Setup the Info panel.
	 * Team's turn
	 * 		If spymaster's turn, include input for the counts and clues and a button to check if it is valid.
	 *  	If team's turn, include button to skip this round and the clue provided by the spy Master.
	 * 
	 * UNDER CONSTRUCTION
	 */
	public void setupInfo() {
		if(!game.CheckWin())
		{
			this.endGame();
		}
		if(spyMasterTurn) {
			infoPanel = new JPanel();
			JLabel jl = new JLabel(game.teamTurn()+ " Turn!");
			JButton jb12 = new JButton("Submit");
			jb12.addActionListener(new submitClueListener(this,jb12, game));
			infoPanel.add(jl);
			infoPanel.add(jb12);
			this.addClueAndCount();
			frame.add(infoPanel, BorderLayout.SOUTH);
		} else {
			
			// TEAM TURN
			infoPanel = new JPanel();			
			JLabel jl = new JLabel(game.teamTurn()+ " Turn!");
			infoPanel.add(jl);
			//JButton jb12 = new JButton("Submit");
			//give up
			//jb.addActionListener();
			JButton pass = new JButton("Pass");
			pass.addActionListener(new passButtonListener(this,game));
			infoPanel.add(pass);
			//infoPanel.add(jb12);
			frame.add(infoPanel, BorderLayout.SOUTH);
		}
	}
	/**
	 * Create a MenuBar with Start New Game and Exit Item in the Main Frame
	 */
	public void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
				
		JMenuItem startItem = new JMenuItem("New 2-Team Game");
		startItem.setMnemonic(KeyEvent.VK_N);
		startItem.setToolTipText("Start a New 2-Team Game");
		startItem.addActionListener(new newGameListener(this, true));

		JMenuItem startItem3 = new JMenuItem("New 3-Team Game");
		startItem3.setMnemonic(KeyEvent.VK_N);
		startItem3.setToolTipText("Start a New 3-Team Game Game");
		startItem3.addActionListener(new newGameListener(this, false));
		
		JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        fileMenu.add(startItem);
        fileMenu.add(startItem3);
        fileMenu.add(eMenuItem);
		menuBar.add(fileMenu);
		
		frame.setJMenuBar(menuBar);
	}
	/**
	 *  Remove every elements in the panel
	 */
	public void clearPanel() {
		frame.remove(shiftingPanel);
		frame.remove(infoPanel);
		frame.remove(clueAndCount);
	}
	
	/**
	 * Setup the environment for the Team's Spy Master, JLabels with Person name and Role. If the person 
	 * is revealed, display only the role assigned
	 */
	
	public void setupSpyMasterLocation() {
		shiftingPanel = new JPanel();
		shiftingPanel.setLayout(new GridLayout(6 ,5));
		
		Location[] loc = game.getBoard().getLocation();
		for (int i =0; i<25; i++) {
			JLabel jl;
			if(loc[i].getRevealed()) {
				//System.out.println(i + "is REVLIELED");
				jl = new JLabel(loc[i].getPerson());
				
				if(loc[i].getPerson().equals("Red Agent")) {
					jl.setForeground(Color.RED);
				} else if(loc[i].getPerson().equals("Blue Agent")) {
					jl.setForeground(Color.BLUE);
				} else if(loc[i].getPerson().equals("Innocent Bystander")) {
					jl.setForeground(Color.YELLOW);
				} else if(loc[i].getPerson().equals("Green Agent")) {
					jl.setForeground(Color.GREEN);
				} else {
					jl.setForeground(Color.PINK);

				}

			} else {
				jl = new JLabel("<html>" + loc[i].getCodename() + "<br>" + 
						loc[i].getPerson() + "</html>");
				//System.out.println(i + "is NOT REVLIELED");

			}
			
			
			jl.setBorder(BorderFactory.createLineBorder(Color.black));

			shiftingPanel.add(jl);
		}
		frame.add(shiftingPanel, BorderLayout.NORTH);
	}
	
	/**Sets up the clue and count text boxes on the clueAndCount JPanel
	 * 
	 */
	public void addClueAndCount()
	{	
		clueAndCount = new JPanel();
		JLabel clueLabel = new JLabel("CLUE:");
		JLabel countLabel = new JLabel("COUNT:");
		clueLabel.setForeground(Color.red);
		countLabel.setForeground(Color.red);
		clue = new JTextField();
		clue.setColumns(10);
		count = new JTextField();
		count.setColumns(10);
		clueAndCount.add(clueLabel);
		clueAndCount.add(clue);
		clueAndCount.add(countLabel);
		clueAndCount.add(count);
		if (game.teamTurn().equals("Blue")) 
		{
			clueLabel.setForeground(Color.blue);
			countLabel.setForeground(Color.blue);
		}
		frame.add(clueAndCount, BorderLayout.CENTER);
	}
	
	/**
	 * Output the Clue and Count
	 */
	public void outputClueAndCount()
	{
		String clu = clue.getText();
		String coun = count.getText();
		clue = new JTextField();
		clue.setColumns(10);
		count = new JTextField();
		count.setColumns(10);
		clue.setText(clu);
		clue.setEditable(false);
		this.count.setText(coun);
		this.count.setEditable(false);
		clueAndCount = new JPanel();
		clueAndCount.add(clue);
		clueAndCount.add(count);
		frame.add(clueAndCount, BorderLayout.CENTER);
	}
	
	
	/**
	 * updates count display
	 */
	public void updateCount()
	{
		count.setText(Integer.toString(game.getCount()));
	}
	
	/**
	 * Pop a Dialog Message to notify players which team's turn
	 */
	public void showDialog() {
		JOptionPane.showMessageDialog(frame, game.teamTurn()+ " Team's turn!");

	}
	
	/**
	 * Setup Locations for team turn
	 */
	public void setupLocation() {

		shiftingPanel = new JPanel();
		
		
		shiftingPanel.setLayout(new GridLayout(5,5));
		Location[] loc = game.getBoard().getLocation();
		for (int i =0; i<25; i++) {
			//JButton jl = new JButton("<html>" + loc[i].getCodename() + "<br>" + 
				//	loc[i].getPerson().getPerson_role() + "</html>"); //JLabel or JButton
			 //JLabel or JButton
			JButton jl;
			if(loc[i].getRevealed()) {
				//System.out.println(i + "is REVLIELED");
				jl = new JButton(loc[i].getPerson());
				jl.setEnabled(false);
				
			} else {
				jl = new JButton(loc[i].getCodename());
				//System.out.println(i + "is NOT REVLIELED");

			}
			jl.addActionListener(new locationButtonListener(jl, i, game, this));
			shiftingPanel.add(jl);
		}
		frame.add(shiftingPanel,BorderLayout.NORTH);
	}

	/**
	 * clears the frame
	 */
	public void Quit() {
		frame.dispose();
	}
	
	/**
	 * Update Spy Master Turn, called after a button is click.
	 */
	public void updateSpyMasterTurn() {
		if(spyMasterTurn) {
			spyMasterTurn = false;
		} else {
			spyMasterTurn =  true;
		}
		setupInfo();
	}
	
	
	/**
	 * ends the game
	 */
	public void endGame()
	{
		this.clearPanel();
		String teamWinner = "";
		if(game.teamWon() == 0)
		{
			teamWinner = "Red Team";
		}
		else if(game.teamWon() == 1)
		{
			teamWinner = "Blue Team";
		} else if(game.teamWon() == 2){
			teamWinner = "Green Team";
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JLabel endScreen = new JLabel("GAME OVER " + teamWinner + " won!", JLabel.CENTER);
		ImageIcon icon = new ImageIcon(new ImageIcon("src/gui/jpg.jpg").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
		

		JLabel gif = new JLabel(icon);
		
		endScreen.setSize(100, 100);
		panel.add(endScreen);
		panel.add(gif);

		frame.add(panel);
	}
	
	/**
	 * creates very annoying sound 
	 */
	public void annoy()
	{
		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			final MidiChannel[] mc = synth.getChannels();
			Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
			synth.loadInstrument(instr[0]);
			mc[0].noteOn(127,600);
			try { Thread.sleep(200); } 
			catch(Exception e) {};
			}
		catch(Exception e) { }
	}
	
}
