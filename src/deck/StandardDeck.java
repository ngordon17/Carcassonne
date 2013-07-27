package deck;

import java.util.HashMap;
import java.util.Map;

import special_tiles.TileFactory;
import tiles.*;

public class StandardDeck extends AbstractDeck {
	private static final String STANDARD_DECK_NAME = "Standard";
	
	@Override 
	public Map<TileFactory, Integer> getDeckBuilder() {
		Map<TileFactory, Integer> standard = new HashMap<TileFactory,Integer>();
		standard.put(FullCity.getFactory(), 1);
		standard.put(GateCity.getFactory(), 3);
		standard.put(GateCityWithShield.getFactory(), 1);
		standard.put(Gatehouse.getFactory(), 1);
		standard.put(GatehouseWithShield.getFactory(), 2);
		standard.put(CornerCity.getFactory(), 3);
		standard.put(CornerCityWithShield.getFactory(), 2);
		standard.put(CornerCityWithRoad.getFactory(), 3);
		standard.put(CornerCityWithRoadAndShield.getFactory(), 2);
		standard.put(BridgeCity.getFactory(), 1);
		standard.put(BridgeCityWithShield.getFactory(), 2);
		standard.put(ButtCity.getFactory(), 2);
		standard.put(DoubleBubbleCity.getFactory(), 3);
		standard.put(BubbleCity.getFactory(), 5);
		standard.put(BubbleCityWithCurvedRoadOne.getFactory(), 3);
		standard.put(BubbleCityWithCurvedRoadTwo.getFactory(), 3);
		standard.put(BubbleCityWithThreeWayIntersection.getFactory(), 3);
		standard.put(BubbleCityWithStraightRoad.getFactory(), 3);
		standard.put(StraightRoad.getFactory(), 8);
		standard.put(CurvedRoad.getFactory(), 9);
		standard.put(ThreeWayIntersection.getFactory(), 4);
		standard.put(FourWayIntersection.getFactory(), 1);
		standard.put(MonasteryTile.getFactory(), 4);
		standard.put(MonasteryTileWithRoad.getFactory(), 2);
		return standard;
	}

	@Override
	public String toString() {
		return STANDARD_DECK_NAME;
	}
}
