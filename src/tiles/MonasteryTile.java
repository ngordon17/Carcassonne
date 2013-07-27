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
import tile_objects.Monastery;

@SuppressWarnings("serial")
public class MonasteryTile extends AbstractTile {
	private static final String IMAGE_PATH = "src/resources/Monastery.png";

	public MonasteryTile(int row, int col) {
		super(IMAGE_PATH, row, col);
	}
	
	private MonasteryTile() {}
	
	@Override
	public List<AbstractTileObject> buildObjectList() {
		List<AbstractTileObject> objects = new ArrayList<AbstractTileObject>();
		
		Map<Rotation, List<AbstractTileObject.Position>> temp = new HashMap<Rotation, List<AbstractTileObject.Position>>();
		temp.put(Rotation.NORTH, Arrays.asList(AbstractTileObject.Position.LEFT, AbstractTileObject.Position.CENTER, AbstractTileObject.Position.RIGHT));
		temp.put(Rotation.EAST, Arrays.asList(AbstractTileObject.Position.LEFT, AbstractTileObject.Position.CENTER, AbstractTileObject.Position.RIGHT));	
		temp.put(Rotation.SOUTH, Arrays.asList(AbstractTileObject.Position.LEFT, AbstractTileObject.Position.CENTER, AbstractTileObject.Position.RIGHT));
		temp.put(Rotation.WEST, Arrays.asList(AbstractTileObject.Position.LEFT, AbstractTileObject.Position.CENTER, AbstractTileObject.Position.RIGHT));
		objects.add(new Farm(temp, new Point(TILE_WIDTH / 8, TILE_HEIGHT / 8), new ArrayList<City>()));
		
		objects.add(new Monastery(new Point(TILE_WIDTH / 2, TILE_HEIGHT / 2), this));
		return objects;
	}

	@Override
	public AbstractTile manufactureTile(int row, int col) {
		return new MonasteryTile(row, col);
	}
	
	public static TileFactory getFactory() {
		return new TileFactory(new MonasteryTile());
	}
			
			

}
