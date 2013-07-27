package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import board.CarcassoneBoard;
import board.TilePanel;
import player.Player;
import player.PlayerPanel;
import special_tiles.AbstractTile;
import view.View;
import deck.AbstractDeck;
import deck.StandardDeck;
import deck.TileDeck;

public class Controller {
	public static final int MAX_PLAYERS = 5;
	public static final int MAX_MEEPLES = 9;
	
	//GAME SETTINGS -> subject to change after game starts....
	private int myNumPlayers = 2;
	private int myNumMeeples = 6;
	private AbstractDeck myDeckBuilder = new StandardDeck();
	private boolean isMusicEnabled = true;
	private boolean isSoundFXEnabled = true;
	private static boolean showMeepleScore = true;
	
	
	//OTHER
	private CarcassoneBoard myBoard;
	private TileDeck myDeck;
	private PlayerPanel myPlayerPanel;
	private List<Player> myPlayers;
	private AbstractTile myCurrentTile;
	private Player myCurrentPlayer;
	private boolean hasCompletedLastTurn = true;
	private boolean myGameOver = false;
	
	public Controller() {
		myBoard = CarcassoneBoard.getInstance();
		myDeck = TileDeck.getInstance();
		myPlayerPanel = PlayerPanel.getInstance();
	}
	
	private void initPlayers() {
		myPlayers = new ArrayList<Player>();
		for (int i = 1; i <= myNumPlayers; i++) {
			myPlayers.add(new Player(i, myNumMeeples));
		}
		myCurrentPlayer = myPlayers.get(0);
		hasCompletedLastTurn = true;  //set to true to allow first player to get tile
	}
	
	public void newGame() {
		myGameOver = false;
		myDeck.reset(myDeckBuilder);
		myBoard.reset(myDeck.getDeckSize());
		initPlayers();	
		myPlayerPanel.reset(myPlayers);
		myCurrentTile = null;
	}
	
	public Player getCurrentPlayer() {
		return myCurrentPlayer;
	}
	
	public boolean submit() {
		if (myCurrentTile == null) {return false;}
		List<AbstractTile> surrounding_tiles = myBoard.getSurroundingTiles(myCurrentTile);
		if (!myCurrentTile.validateMove(surrounding_tiles)) {return false;}
		myCurrentTile.updateAllConnections(surrounding_tiles);
		myCurrentTile.enableDND(false);
		myCurrentTile.showMeeplePlacement(false);
		//myCurrentTile.debug();
		myCurrentTile = null;
		myBoard.revalidate();
		myBoard.repaint();
		hasCompletedLastTurn = true;
		
		checkForEndOfGame();
		int index = myPlayers.indexOf(myCurrentPlayer);
		int next = (index + 1) % myPlayers.size();
		myCurrentPlayer = myPlayers.get(next);
		refreshPlayers();
		
		return true;
	}
	
	//returns boolean indicating to view whether or not to exit program or start new game
	public void endOfGame(View view) {
		if (myGameOver) {
			
			int maxScore = -1;
			List<Player> winners = new ArrayList<Player>();
			for (Player player : myPlayers) {
				if (player.getScore() > maxScore) {
					winners = new ArrayList<Player>();
					maxScore = player.getScore();
					winners.add(player);			
				}
				else if (player.getScore() == maxScore) {
					winners.add(player);
				}
			}
			
			String message = "";
			if (winners.size() == 1) {
				message = "Player " + winners.get(0).getID() + " Wins!";
			}
			if (winners.size() > 1) {
				message = "It's a Tie! Players ";
				for (Player player : winners) {
					message = message + player.getID() + ", ";
				}
				message = message.substring(0, message.length() - 2);
				message = message + "Win!";
			}
			
			
			int decision = JOptionPane.showConfirmDialog(view, message + ". New Game (Y) or Quit (N)?", "Game Over", JOptionPane.YES_NO_OPTION);
			if (decision == 0) {
				newGame();
			}
			else {
				view.dispose();
			}
		}
	}
	
	private void checkForEndOfGame() {
		if (!myDeck.hasNextTile()) {
			myGameOver = true;
		}
	}
	
	private void refreshPlayers() {
		for (Player player : myPlayers) {
			player.refresh(myGameOver);
		}
	}
	
	public boolean getNextTile() {
		if (!hasCompletedLastTurn) {return false;}
		myCurrentTile = myDeck.getNextTile();
		hasCompletedLastTurn = false;
		return true;
	}
	
	public void skipTurn(View view) {
		if (JOptionPane.showConfirmDialog(view, "Are you sure you want to skip your turn?") == 0) {
			if (myCurrentTile != null && myCurrentTile.getMeeple() != null) {
				myCurrentPlayer.removeMeeple(myCurrentTile.getMeeple());
			}
			if (myCurrentTile != null) {
				myDeck.skipTurn(myCurrentTile); 
				
				TilePanel parent = (TilePanel) myCurrentTile.getParent();
				if (parent != null) {
					parent.setEmpty();
					parent.revalidate();
					parent.repaint();
				}
				
				myCurrentTile = null;		
			}
			
			int index = myPlayers.indexOf(myCurrentPlayer);
			int next = (index + 1) % myPlayers.size();
			myCurrentPlayer = myPlayers.get(next);
			hasCompletedLastTurn = true;
		}
	}
	
	
	public void saveGame() {
		
	}
	
	public void setDeck(AbstractDeck deckBuilder) {
		myDeckBuilder = deckBuilder;
	}
	
	public void setNumPlayers(int numPlayers) {
		myNumPlayers = numPlayers;
	}
	
	public int getNumPlayers() {
		return myNumPlayers;
	}
	
	public void setNumMeeples(int numMeeples) {
		myNumMeeples = numMeeples;
	}
	
	public int getNumMeeples() {
		return myNumMeeples;
	}
	
	public void showMeepleScore(boolean showScore) {
		showMeepleScore = showScore;
		myBoard.revalidate();
		myBoard.repaint();
	}
	
	public static boolean isShowMeepleScore() {
		return showMeepleScore;
	}
	
	public void enableMusic(boolean isEnabled) {
		isMusicEnabled = isEnabled;
	}
	
	public void enableSoundFX(boolean isEnabled) {
		isSoundFXEnabled = isEnabled;
	}
}
