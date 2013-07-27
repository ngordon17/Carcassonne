package special_tiles;

import java.util.ArrayList;
import java.util.List;

import tile_objects.AbstractTileObject;

@SuppressWarnings("serial")
public class EmptyTile extends AbstractTile {
	private static final String IMAGE_PATH = "src/resources/Empty_Tile.png";
	
	public EmptyTile(int row, int col) {
		super(IMAGE_PATH, row, col);
	}
	
	@Override
	public List<AbstractTileObject> buildObjectList() {
		return new ArrayList<AbstractTileObject>();
	}

	@Override
	public AbstractTile manufactureTile(int row, int col) {
		return new EmptyTile(row, col);
	}
	
}
