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
import tile_objects.Farm;

@SuppressWarnings("serial")
public class CornerCity extends AbstractTile { 
	private static final String IMAGE_PATH = "src/resources/Corner_City.png";

	public CornerCity(int row, int col) {
		super(IMAGE_PATH, row, col);
	}
	
	private CornerCity() {}
	
	@Override
	public List<AbstractTileObject> buildObjectList() {
		List<AbstractTileObject> objects = new ArrayList<AbstractTileObject>();
		
		Map<Rotation, List<AbstractTileObject.Position>> temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.NORTH, Arrays.asList(AbstractTileObject.Position.CENTER));
		temp.put(Rotation.WEST, Arrays.asList(AbstractTileObject.Position.CENTER));
		City city1 = new City(temp, new Point(TILE_WIDTH / 4, TILE_HEIGHT / 4), false);
		objects.add(city1);
		
		temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.EAST, Arrays.asList(AbstractTileObject.Position.LEFT, AbstractTileObject.Position.CENTER, AbstractTileObject.Position.RIGHT));
		temp.put(Rotation.SOUTH, Arrays.asList(AbstractTileObject.Position.RIGHT, AbstractTileObject.Position.CENTER, AbstractTileObject.Position.LEFT));
		objects.add(new Farm(temp, new Point(TILE_WIDTH * 3 / 4, TILE_HEIGHT * 3 / 4), Arrays.asList(city1)));
		
		return objects;
	}

	@Override
	public AbstractTile manufactureTile(int row, int col) {
		return new CornerCity(row, col);
	}
	
	public static TileFactory getFactory() {
		return new TileFactory(new CornerCity());
	}
}
