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
import tile_objects.Road;

@SuppressWarnings("serial")
public class BubbleCityWithCurvedRoadOne extends AbstractTile {
	private static final String IMAGE_PATH = "src/resources/Bubble_City_With_Curved_Road_One.png";

	public BubbleCityWithCurvedRoadOne(int row, int col) {
		super(IMAGE_PATH, row, col);
	}
	
	private BubbleCityWithCurvedRoadOne() {}
	
	@Override
	public List<AbstractTileObject> buildObjectList() {
		List<AbstractTileObject> objects = new ArrayList<AbstractTileObject>();
		
		Map<Rotation, List<AbstractTileObject.Position>> temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.NORTH, Arrays.asList(AbstractTileObject.Position.CENTER));
		City city1 = new City(temp, new Point(TILE_WIDTH / 2, TILE_HEIGHT / 8), false);
		objects.add(city1);
		
		temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.SOUTH, Arrays.asList(AbstractTileObject.Position.CENTER));
		temp.put(Rotation.WEST, Arrays.asList(AbstractTileObject.Position.CENTER));
		objects.add(new Road(temp, new Point(TILE_WIDTH / 2, TILE_HEIGHT / 2 - 3)));
	
		temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.EAST, Arrays.asList(AbstractTileObject.Position.LEFT, AbstractTileObject.Position.CENTER, AbstractTileObject.Position.RIGHT));
		temp.put(Rotation.SOUTH, Arrays.asList(AbstractTileObject.Position.LEFT));
		temp.put(Rotation.WEST, Arrays.asList(AbstractTileObject.Position.RIGHT));
		objects.add(new Farm(temp, new Point(TILE_WIDTH * 2 / 3, TILE_HEIGHT * 2 / 3), Arrays.asList(city1)));
		
		
		temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.SOUTH, Arrays.asList(AbstractTileObject.Position.RIGHT));
		temp.put(Rotation.WEST, Arrays.asList(AbstractTileObject.Position.LEFT));
		objects.add(new Farm(temp, new Point(TILE_WIDTH / 4, TILE_HEIGHT * 7 / 8), new ArrayList<City>()));
		
		return objects;
	}

	@Override
	public AbstractTile manufactureTile(int row, int col) {
		return new BubbleCityWithCurvedRoadOne(row, col);
	}
	
	public static TileFactory getFactory() {
		return new TileFactory(new BubbleCityWithCurvedRoadOne());
	}
}
