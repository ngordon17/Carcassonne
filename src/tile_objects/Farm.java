package tile_objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import player.Player;
import special_tiles.AbstractTile.Rotation;

public class Farm extends AbstractTileObject {
	private static final String FARM_NAME = "Farm";
	private List<City> myCities;

	public Farm(Map<Rotation, List<Position>> position, Point meeplePlacement, List<City> cities) {
		super(position, meeplePlacement);
		myCities = cities;
	}

	@Override
	public boolean isSameObject(AbstractTileObject other) {
		return other instanceof Farm;
	}
	
	public List<City> getCities() {
		return myCities;
	}

	@Override
	public int calculateScore(Player player) {
		List<AbstractTileObject> connections = traverseConnections();
		if (!getPlayersWithMostMeeples(connections).contains(player)) {
			return 0;
		}
		
		List<City> cities = new ArrayList<City>();
		cities.addAll(myCities);
		for (AbstractTileObject object : connections) {
			Farm farm = (Farm) object;
			cities.addAll(farm.getCities());
		}
		
		List<City> completed = new ArrayList<City>();
		for (City city : cities) {
			if (!city.isCompleted()) {continue;}
			
			boolean flag = false;
			for (City complete : completed) {
				if (complete.traverseConnections().contains(city)) {flag = true;}
			}
			
			if (!flag) {completed.add(city);}
		}
		return completed.size() * 3;
	}
	
	@Override
	public boolean isCompleted() {
		return false;
	}

	@Override
	public String toString() {
		return FARM_NAME;
	}

}
