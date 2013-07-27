package meeple;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import player.Player;
import special_tiles.AbstractTile;
import tile_objects.AbstractTileObject;

public class Meeple {

	private final static String RED_MEEPLE_PATH = "src/resources/Red_Meeple.png";
	private final static String YELLOW_MEEPLE_PATH = "src/resources/Yellow_Meeple.png";
	private final static String GREEN_MEEPLE_PATH = "src/resources/Green_Meeple.png";
	private final static String BLUE_MEEPLE_PATH = "src/resources/Blue_Meeple.png";
	private final static String BLACK_MEEPLE_PATH = "src/resources/Black_Meeple.png";
	private final static Map<Color, String> COLOR_MAP = buildColorMap();		
	private Color myColor;
	private AbstractTileObject myTileObject;
	private AbstractTile myTile;
	private Player myPlayer;

	public enum Color {
		RED, YELLOW, GREEN, BLUE, BLACK;
	}
	
	public Meeple(Color color, AbstractTile tile, AbstractTileObject object, Player player) {
		myColor = color;
		myTileObject = object;
		myTileObject.setMeeple(this);
		myTile = tile;
		myTile.setMeeple(this);
		myPlayer = player;
	}
	
	public Player getPlayer() {
		return myPlayer;
	}
	
	private static Map<Color, String> buildColorMap() {
		Map<Color, String> color_map = new HashMap<Color, String>();
		color_map.put(Color.RED, RED_MEEPLE_PATH);
		color_map.put(Color.YELLOW, YELLOW_MEEPLE_PATH);
		color_map.put(Color.GREEN, GREEN_MEEPLE_PATH);
		color_map.put(Color.BLUE, BLUE_MEEPLE_PATH);
		color_map.put(Color.BLACK, BLACK_MEEPLE_PATH);
		return color_map;	
	}
	
	public static BufferedImage getBufferedImage(Color color) {
		try {
			BufferedImage image = ImageIO.read(new File(COLOR_MAP.get(color)));
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public  BufferedImage getBufferedImage() {
		try {
			BufferedImage image = ImageIO.read(new File(COLOR_MAP.get(myColor)));
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Color getColor(int playerID) {
		return (new ArrayList<Color>(COLOR_MAP.keySet())).get(playerID - 1);
	}
	
	public AbstractTileObject getObject() {
		return myTileObject;
	}
	
	public AbstractTile getTile() {
		return myTile;
	}
	
	public void remove() {
		if (myTile != null) {myTile.removeMeeple();}
		if (myTileObject != null) {myTileObject.removeMeeple();}
	}
	
	public int getScore() {
		return myTileObject.calculateScore(myPlayer);
	}
	
	public boolean onCompletedObject() {
		return myTileObject.isCompleted();
	}

}
