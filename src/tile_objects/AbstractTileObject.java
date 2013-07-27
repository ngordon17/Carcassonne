package tile_objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import player.Player;

import meeple.Meeple;

import special_tiles.AbstractTile;

public abstract class AbstractTileObject {
	
	private Map<AbstractTile.Rotation, List<Position>> myPosition;
	private List<AbstractTileObject> myConnectedObjects;
	private Meeple myMeeple;
	private Point myMeeplePlacement;
	
	/*				   NORTH
	 * 		      Left Center Right 
	 * 				 ____________
	 * 		Right  |			 | Left
	 * WEST Center |	TILE	 | Center EAST
	 * 	    Left   |			 | Right
	 * 			   |_____________|
	 * 			  Right Center Left
	 *            		SOUTH
	 * 
	 */
	public enum Position {
		LEFT, CENTER, RIGHT;
	}
	
	public AbstractTileObject(Map<AbstractTile.Rotation, List<Position>> position, Point meeplePlacement) {
		myPosition = position;
		myConnectedObjects = new ArrayList<AbstractTileObject>();
		myMeeplePlacement = meeplePlacement;
	}
	
	public abstract boolean isSameObject(AbstractTileObject other);
	public abstract int calculateScore(Player player);
	public abstract String toString();
	
	public boolean isCompleted() {
		List<AbstractTileObject> connections = traverseConnections();
		for (AbstractTileObject object : connections) {
			if (!object.isClosed()) {return false;}
		}
		return true;
	}
	
	private boolean isClosed() {
		return myPosition.size() == myConnectedObjects.size();
	}
	public void setMeeple(Meeple meeple) {
		myMeeple = meeple;
	}
	
	public void removeMeeple() {
		myMeeple = null;
	}
	
	public boolean hasMeeple() {
		return myMeeple != null;
	}
	
	public Point getMeeplePlacement() {
		return myMeeplePlacement;
	}
	
	public boolean connectedObjectsOccupied() {
		List<AbstractTileObject> connections = traverseConnections();
		for (AbstractTileObject connection : connections) {
			if (connection.hasMeeple()) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Traverses the linked list of tile object nodes and returns the connected tile objects as 
	 * a list so that the score for these connected objects can be more easily calculated. 
	 * Visited is a list of visited tile object so that we do not traverse the same object twice.
	 */
	
	protected List<AbstractTileObject> traverseConnections() {
		return new ArrayList<AbstractTileObject>(traverseConnections(new HashSet<AbstractTileObject>()));
	}
	
	private Set<AbstractTileObject> traverseConnections(Set<AbstractTileObject> visited) {
		if (!visited.contains(this)) {visited.add(this);}
		for (AbstractTileObject object : myConnectedObjects) {
			if (visited.contains(object)) {continue;}
			visited.addAll(object.traverseConnections(visited));
		}
		return visited;
	}
	
	/*
	 * Rotation is the "rotated" side of the tile we are connecting to the other tile and other_rotation is the
	 * "rotated" side of the tile we are connecting to. Other is the tile that we are connecting to. 
	 * An example of a "rotated" side is if a tile is rotated 90 degrees clockwise, then its "rotated" northern side is WEST.
	 */
	public boolean connect(AbstractTile.Rotation rotation, AbstractTile.Rotation other_rotation, AbstractTileObject other) {
		if (!validateConnection(rotation, other_rotation, other)) {return false;}
		myConnectedObjects.add(other);
		return true;
	}
	
	public boolean validateConnection(AbstractTile.Rotation rotation, AbstractTile.Rotation other_rotation, AbstractTileObject other) {
		if (!isSameObject(other) || !containsSide(rotation) || !other.containsSide(other_rotation)) {return false;}
		List<Position> position = getPositionForSide(rotation);
		List<Position> other_position = other.getPositionForSide(other_rotation);		
		if (!((position.contains(Position.CENTER) && other_position.contains(Position.CENTER))
			|| (position.contains(Position.LEFT) && other_position.contains(Position.RIGHT)) 
			|| (position.contains(Position.RIGHT) && other_position.contains(Position.LEFT)))) {return false;}
		return true;		
	}
	
	public boolean containsSide(AbstractTile.Rotation side) {
		return myPosition.containsKey(side);
	}
	
	private List<Position> getPositionForSide(AbstractTile.Rotation side) {
		return myPosition.get(side);
	}

	private Meeple getMeeple() {
		return myMeeple;
	}
	
	protected List<Player> getPlayersWithMostMeeples(List<AbstractTileObject> connections) {
		Map<Player, Integer> meeplesPerPlayer = new HashMap<Player, Integer>();
		
		for (AbstractTileObject object : connections) {
			if (!object.hasMeeple()) {continue;}
			Meeple meeple = object.getMeeple();
			Player player = meeple.getPlayer();
			if (!meeplesPerPlayer.containsKey(player)) {
				meeplesPerPlayer.put(player, 0);
			}
			meeplesPerPlayer.put(player, meeplesPerPlayer.get(player) + 1);
		}
		
		int maxMeeples = 0;
		List<Player> players = new ArrayList<Player>();
		
		for (Player player : meeplesPerPlayer.keySet()) {
			if (meeplesPerPlayer.get(player) == maxMeeples) {
				players.add(player);
			}
			else if (meeplesPerPlayer.get(player) >= maxMeeples) {
				players = new ArrayList<Player>();
				players.add(player);
				maxMeeples = meeplesPerPlayer.get(player);
			}
		}
		return players;
	}



}
