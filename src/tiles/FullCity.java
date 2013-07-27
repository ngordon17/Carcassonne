package tiles;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import special_tiles.AbstractTile;
import special_tiles.TileFactory;
import tile_objects.AbstractTileObject;
import tile_objects.City;

@SuppressWarnings("serial")
public class FullCity extends AbstractTile {
	private static final String IMAGE_PATH = "src/resources/Full_City.png";
	
	public FullCity(int row, int col) {
		super(IMAGE_PATH, row, col);
	}
	
	private FullCity() {}

	@Override
	public List<AbstractTileObject> buildObjectList() {
		List<AbstractTileObject> objects = new ArrayList<AbstractTileObject>();
		
		Map<Rotation, List<AbstractTileObject.Position>> temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.NORTH, Arrays.asList(AbstractTileObject.Position.CENTER));
		temp.put(Rotation.EAST, Arrays.asList(AbstractTileObject.Position.CENTER));
		temp.put(Rotation.SOUTH, Arrays.asList(AbstractTileObject.Position.CENTER));
		temp.put(Rotation.WEST, Arrays.asList(AbstractTileObject.Position.CENTER));
		objects.add(new City(temp, new Point(TILE_WIDTH / 2, TILE_HEIGHT / 2), true));
		
		return objects;
	}

	@Override
	public AbstractTile manufactureTile(int row, int col) {
		return new FullCity(row, col);
	}
	
	public static TileFactory getFactory() {
		return new TileFactory(new FullCity());
	}

	
	
	
	
}
