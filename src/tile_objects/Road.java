package tile_objects;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import player.Player;

import special_tiles.AbstractTile.Rotation;

public class Road extends AbstractTileObject {
	private static final String ROAD_NAME = "Road";

	public Road(Map<Rotation, List<Position>> position, Point meeplePlacement) {
		super(position, meeplePlacement);
	}

	@Override
	public boolean isSameObject(AbstractTileObject other) {
		return other instanceof Road;
	}

	@Override
	public int calculateScore(Player player) {
		List<AbstractTileObject> connections = traverseConnections();
		if (!getPlayersWithMostMeeples(connections).contains(player)) {
			return 0;
		}
		
		return connections.size();
	}

	@Override
	public String toString() {
		return ROAD_NAME;
	}

}
