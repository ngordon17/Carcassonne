package special_tiles;

public class TileFactory {
	private AbstractTile myTile;

	public TileFactory(AbstractTile tile) {
		myTile = tile;
	}
	
	public AbstractTile manufactureTile(int row, int col) {
		return myTile.manufactureTile(row, col);
	}
}