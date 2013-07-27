package deck;

import java.util.HashMap;
import java.util.Map;

import special_tiles.TileFactory;
import tiles.BubbleCity;
import tiles.CornerCityWithRoad;
import tiles.StraightRoad;
import tiles.ThreeWayIntersection;

public class DemoDeck extends AbstractDeck {
	
	private static final String DEMO_DECK_NAME = "Demo";

	@Override
	public Map<TileFactory, Integer> getDeckBuilder() {
		Map<TileFactory, Integer> demo = new HashMap<TileFactory, Integer>();
		demo.put(StraightRoad.getFactory(), 5);
		demo.put(CornerCityWithRoad.getFactory(), 5);
		demo.put(BubbleCity.getFactory(), 20);
		demo.put(ThreeWayIntersection.getFactory(), 5);
		return demo;
	}

	@Override
	public String toString() {
		return DEMO_DECK_NAME;
	}
}
