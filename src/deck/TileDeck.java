package deck;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;

import special_tiles.AbstractTile;
import special_tiles.TileFactory;

import board.TilePanel;

@SuppressWarnings("serial")
public class TileDeck extends TilePanel {
	private static Map<TileFactory, Integer> myDeckBuilder;
	private Stack<AbstractTile> myDeck;
	private static TileDeck myInstance;

	
	private TileDeck() {
		super(-1, -1);
		reset(new DemoDeck());
	}
	
	public static TileDeck getInstance() {
		if (myInstance == null) {
			myInstance = new TileDeck();
		}
		return myInstance;
	}
	
	private void createDeck() {
		myDeck = new Stack<AbstractTile>();
		for (TileFactory factory : myDeckBuilder.keySet()) {
			for (int i = 0; i < myDeckBuilder.get(factory); i++) {
				myDeck.push(factory.manufactureTile(-1, -1));
			}
		}
		shuffleDeck();
	}
	
	private void shuffleDeck() {
		Collections.shuffle(myDeck);
	}
	
	public AbstractTile getNextTile() {
		if (!hasNextTile()) {return null;}
		AbstractTile next = myDeck.pop();
		next.enableDND(true);
		next.showMeeplePlacement(true);
		setTile(next);
		revalidate();
		repaint();
		return next;
	}
	
	public boolean hasNextTile() {
		return !myDeck.isEmpty();
	}
	
	public void skipTurn(AbstractTile tile) {
		setEmpty(); //remove tile from ui and set to empty tile
		myDeck.add(tile);
		shuffleDeck();
	}
	
	public int getDeckSize() {
		int size = 0;
		for (TileFactory factory : myDeckBuilder.keySet()) {
			size += myDeckBuilder.get(factory);
		}
		return size;
	}
	
	public int getNumRemainingTiles() {
		return myDeck.size();
	}
	
	public void reset(AbstractDeck deck) {
		myDeckBuilder = deck.getDeckBuilder();
		removeAll();
		setLayout(new BorderLayout());
		setEmpty(); //empty tile sets automatically;
		createDeck();
		revalidate();
	}
	
}
