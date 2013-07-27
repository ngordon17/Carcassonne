package tile_objects;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import player.Player;

import special_tiles.AbstractTile.Rotation;

public class City extends AbstractTileObject {
	private static final String CITY_NAME = "City";
	private boolean hasShield;

	public City(Map<Rotation, List<Position>> position, Point meeplePlacement, boolean shielded) {
		super(position, meeplePlacement);
		hasShield = shielded;
	}

	@Override
	public boolean isSameObject(AbstractTileObject other) {
		return other instanceof City;
	}
	
	public boolean hasShield() {
		return hasShield;
	}

	@Override
	public int calculateScore(Player player) {
		int score = 0;
		List<AbstractTileObject> connections = traverseConnections();
		
		if (!getPlayersWithMostMeeples(connections).contains(player)) {
			return score;
		}
		
		for (AbstractTileObject object : connections) {
			if (((City) object).hasShield()) {score += 2;}
			else {score += 1;}
		}	
		if (isCompleted()) {score *= 2;}
		return score;
	}
	
	@Override
	public String toString() {
		return CITY_NAME;
	}

	
}
