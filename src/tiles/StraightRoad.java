package tiles;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import special_tiles.AbstractTile;
import special_tiles.TileFactory;
import tile_objects.*;

@SuppressWarnings("serial")
public class StraightRoad extends AbstractTile {
	private static final String IMAGE_PATH = "src/resources/Straight_Road.png";
	
	public StraightRoad(int row, int col) {
		super(IMAGE_PATH, row, col);
	}
	
	private StraightRoad() {}
	
	@Override
	public List<AbstractTileObject> buildObjectList() {
		List<AbstractTileObject> objects = new ArrayList<AbstractTileObject>();
		
		Map<Rotation, List<AbstractTileObject.Position>> temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.NORTH, Arrays.asList(AbstractTileObject.Position.CENTER));
		temp.put(Rotation.SOUTH, Arrays.asList(AbstractTileObject.Position.CENTER));
		objects.add(new Road(temp, new Point(TILE_WIDTH / 2, TILE_HEIGHT / 2)));
		
		temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.NORTH, Arrays.asList(AbstractTileObject.Position.LEFT));
		temp.put(Rotation.SOUTH, Arrays.asList(AbstractTileObject.Position.RIGHT));
		temp.put(Rotation.WEST, Arrays.asList(AbstractTileObject.Position.LEFT, AbstractTileObject.Position.CENTER, AbstractTileObject.Position.RIGHT));
		objects.add(new Farm(temp, new Point(TILE_WIDTH / 4, TILE_HEIGHT / 2), new ArrayList<City>()));
		
		temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.NORTH, Arrays.asList(AbstractTileObject.Position.RIGHT));
		temp.put(Rotation.EAST, Arrays.asList(AbstractTileObject.Position.LEFT, AbstractTileObject.Position.CENTER, AbstractTileObject.Position.RIGHT));
		temp.put(Rotation.SOUTH, Arrays.asList(AbstractTileObject.Position.LEFT));
		objects.add(new Farm(temp, new Point(TILE_WIDTH * 3 / 4, TILE_HEIGHT / 2), new ArrayList<City>()));
		
		return objects;
	}

	@Override
	public AbstractTile manufactureTile(int row, int col) {
		return new StraightRoad(row, col);
	}
	
	public static TileFactory getFactory() {
		return new TileFactory(new StraightRoad());
	}

}
