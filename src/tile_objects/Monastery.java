package tile_objects;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import board.CarcassoneBoard;

import player.Player;

import special_tiles.AbstractTile;
import special_tiles.AbstractTile.Rotation;

public class Monastery extends AbstractTileObject {
	private static final String MONASTERY_NAME = "Monastery";
	private AbstractTile myTile;

	public Monastery(Point meeplePlacement, AbstractTile tile) {
		super(new HashMap<Rotation, List<Position>>(), meeplePlacement);
		myTile = tile;
	}

	@Override
	public boolean isSameObject(AbstractTileObject other) {
		return other instanceof Monastery;
	}
	
	@Override
	public boolean isCompleted() {
		CarcassoneBoard board = CarcassoneBoard.getInstance();
		return board.getMonasteryTiles(myTile).size() == 8;
	}

	@Override
	public int calculateScore(Player player) {
		CarcassoneBoard board = CarcassoneBoard.getInstance();
		return board.getMonasteryTiles(myTile).size() + 1;
	}

	@Override
	public String toString() {
		return MONASTERY_NAME;
	}

}
