package deck;

import java.util.Map;

import special_tiles.TileFactory;

public abstract class AbstractDeck {
	
	public abstract Map<TileFactory, Integer> getDeckBuilder();
	public abstract String toString();

}
