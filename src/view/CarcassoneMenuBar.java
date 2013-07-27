package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import controller.Controller;
import deck.AbstractDeck;
import deck.DemoDeck;
import deck.StandardDeck;

@SuppressWarnings("serial")
public class CarcassoneMenuBar extends JMenuBar {
	private Controller myController;
	
	public CarcassoneMenuBar(Controller controller) {
		myController = controller;
		
		initFileMenu();
		initGameSettingsMenu();
		initAudioSettingsMenu();
	}	
	
	public void initFileMenu() {
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new NewGameAction());
		newGame.setEnabled(true);
		fileMenu.add(newGame);
		
		JMenuItem saveGame = new JMenuItem("Save");
		saveGame.addActionListener(new SaveGameAction());
		saveGame.setEnabled(false);
		fileMenu.add(saveGame);
			
		add(fileMenu);			
	}
	
	public void initGameSettingsMenu() {
		JMenu gameSettingsMenu = new JMenu("Game Settings");
		
		gameSettingsMenu.add(makePlayersMenu());
		gameSettingsMenu.add(makeMeeplesMenu());
		gameSettingsMenu.add(makeMeepleScoreMenu());
		gameSettingsMenu.add(makeDeckMenu());
		
		add(gameSettingsMenu);
	}
	
	private JMenu makePlayersMenu() {
		JMenu playersMenu = new JMenu("Players");
		
		for (int i = 1; i <= Controller.MAX_PLAYERS; i++) {
			JMenuItem item = new JMenuItem(Integer.toString(i));
			item.addActionListener(new SetPlayersAction(i));
			item.setEnabled(true);
			playersMenu.add(item);			
		}
		return playersMenu;
	}
	
	private JMenu makeMeeplesMenu() {
		JMenu meeplesMenu = new JMenu("Meeples");
		
		for (int i = 1; i <= Controller.MAX_MEEPLES; i++) {
			JMenuItem item = new JMenuItem(Integer.toString(i));
			item.addActionListener(new SetMeeplesAction(i));
			item.setEnabled(true);
			meeplesMenu.add(item);		
		}
		return meeplesMenu;
	}
	
	private JMenu makeMeepleScoreMenu() {
		JMenu meepleScoreMenu = new JMenu("Show Meeple Scores");
		
		JMenuItem on = new JMenuItem("On");
		on.addActionListener(new ShowMeepleScoreAction(true));
		on.setEnabled(true);
		meepleScoreMenu.add(on);
		
		JMenuItem off = new JMenuItem("Off");
		off.addActionListener(new ShowMeepleScoreAction(false));
		off.setEnabled(true);
		meepleScoreMenu.add(off);
		
		return meepleScoreMenu;
	}

	private JMenu makeDeckMenu() {
		JMenu deckMenu = new JMenu("Decks");
		
		JMenuItem demoDeck = new JMenuItem((new DemoDeck()).toString());
		demoDeck.addActionListener(new SetDeckAction(new DemoDeck()));
		demoDeck.setEnabled(true);
		deckMenu.add(demoDeck);
		
		JMenuItem standardDeck = new JMenuItem((new StandardDeck()).toString());
		standardDeck.addActionListener(new SetDeckAction(new StandardDeck()));
		standardDeck.setEnabled(true);
		deckMenu.add(standardDeck);
	
		return deckMenu;
	}
	
	public void initAudioSettingsMenu() {
		JMenu audioSettingsMenu = new JMenu("Audio Settings");
		audioSettingsMenu.add(makeMusicSettingsMenu());
		audioSettingsMenu.add(makeSoundFXSettingsMenu());
		add(audioSettingsMenu);	
	}
	
	private JMenu makeMusicSettingsMenu() {
		JMenu musicSettingsMenu = new JMenu("Music Settings");
		
		JMenuItem musicOn = new JMenuItem("On");
		musicOn.addActionListener(new EnableMusicAction(true));
		musicOn.setEnabled(true);
		musicSettingsMenu.add(musicOn);
		
		JMenuItem musicOff = new JMenuItem("Off");
		musicOff.addActionListener(new EnableMusicAction(false));
		musicOff.setEnabled(true);
		musicSettingsMenu.add(musicOff);

		return musicSettingsMenu;	
	}
	
	private JMenu makeSoundFXSettingsMenu() {
		JMenu soundFXSettingsMenu = new JMenu("Sound FX Settings");
		
		JMenuItem soundFXOn = new JMenuItem("On");
		soundFXOn.addActionListener(new EnableSoundFXAction(true));
		soundFXOn.setEnabled(true);
		soundFXSettingsMenu.add(soundFXOn);
		
		JMenuItem soundFXOff = new JMenuItem("Off");
		soundFXOff.addActionListener(new EnableSoundFXAction(false));
		soundFXOff.setEnabled(true);
		soundFXSettingsMenu.add(soundFXOff);
		
		return soundFXSettingsMenu;
	}
	
	private class NewGameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			myController.newGame();
			//myController.newGame();
		}
	}
	
	private class SaveGameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			myController.saveGame();
		}
	}
	
	private class SetPlayersAction implements ActionListener {
		private int myNumPlayers;	
		
		public SetPlayersAction(int i) {
			myNumPlayers = i;
		}
		
		public void actionPerformed(ActionEvent e) {
			myController.setNumPlayers(myNumPlayers);
		}
	}
	
	private class SetMeeplesAction implements ActionListener {
		private int myNumMeeples;
		
		public SetMeeplesAction(int i) {
			myNumMeeples = i;
		}
		
		public void actionPerformed(ActionEvent e) {
			myController.setNumMeeples(myNumMeeples);
		}
	}
	
	
	private class SetDeckAction implements ActionListener {
		private AbstractDeck myDeckBuilder;
		
		public SetDeckAction(AbstractDeck deckBuilder) {
			myDeckBuilder = deckBuilder;
		}
		
		public void actionPerformed(ActionEvent e) {
			myController.setDeck(myDeckBuilder);
		}
	} 
	
	private class ShowMeepleScoreAction implements ActionListener {
		private boolean myEnabled;
		
		public ShowMeepleScoreAction(boolean isEnabled) {
			myEnabled = isEnabled;
		}
		
		public void actionPerformed(ActionEvent e) {
			myController.showMeepleScore(myEnabled);
		}
	}
	
	private class EnableMusicAction implements ActionListener {
		private boolean myEnabled;
		
		public EnableMusicAction(boolean isEnabled) {
			myEnabled = isEnabled;
		}
		
		public void actionPerformed(ActionEvent e) {
			myController.enableMusic(myEnabled);
		}
	}
	
	private class EnableSoundFXAction implements ActionListener {
		private boolean myEnabled;
		
		public EnableSoundFXAction(boolean isEnabled) {
			myEnabled = isEnabled;
		}
		
		public void actionPerformed(ActionEvent e) {
			myController.enableSoundFX(myEnabled);
		}
	}

}
