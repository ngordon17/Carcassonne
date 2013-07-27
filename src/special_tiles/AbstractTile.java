package special_tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import controller.Controller;
import meeple.Meeple;
import tile_objects.AbstractTileObject;
import tile_objects.Farm;
import tile_objects.Road;

@SuppressWarnings("serial")
public abstract class AbstractTile extends JLabel {
	
	public static final int TILE_WIDTH = 80;
	public static final int TILE_HEIGHT = 80;
	private static final float ROTATION_ANGLE = 90;
	private Rotation[] myRotation = {Rotation.NORTH, Rotation.EAST, Rotation.SOUTH, Rotation.WEST};
	private List<AbstractTileObject> myObjects;
	private boolean showMeeplePlacement = false;
	private boolean isDNDEnabled = false;
	private BufferedImage myImage;
	private Meeple myMeeple;
	private String myName;
	private int myRow;
	private int myCol;
	
	public enum Rotation {
		NORTH, EAST, SOUTH, WEST
	}
	
	public AbstractTile(String image_path, int row, int col) {
		super(new ImageIcon(image_path));
		readImage(image_path);
		parseName(image_path);
		setCellLocation(row, col);
		myObjects = buildObjectList();
	}
	
	protected AbstractTile() {}
	
	public abstract List<AbstractTileObject> buildObjectList();
	public abstract AbstractTile manufactureTile(int row, int col);
	
	private void readImage(String image_path) {
		try {
			myImage = ImageIO.read(new File(image_path));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private void parseName(String image_path) {
		myName = image_path.split("/")[2].split("\\.")[0];
	}
	
	public List<AbstractTileObject> getObjects() {
		return myObjects;
	}
	
	public void showMeeplePlacement(boolean isShown) {
		showMeeplePlacement = isShown;
		revalidate();
		repaint(); //required
	}
	
	public void setMeeple(Meeple meeple) {
		myMeeple = meeple;
		revalidate();
		repaint(); //required
	}
	
	public Meeple getMeeple() {
		return myMeeple;
	}
	
	public void removeMeeple() {
		myMeeple = null;
		revalidate();
		repaint(); //required
		
	}
	
	public void setCellLocation(int row, int col) {
		myRow = row;
		myCol = col;
	}
	
	public int getRow() {
		return myRow;
	}
	
	public int getCol() {
		return myCol;
	}
	
	public void enableDND(boolean isEnabled) {
		isDNDEnabled = isEnabled;
	}
	
	public boolean isDNDEnabled() {
		return isDNDEnabled;
	}
	
	public List<Point> getMeeplePoints() {
		List<Point> meeple_points = new ArrayList<Point>();
		for (AbstractTileObject object : myObjects) {
			meeple_points.add(rotatePoint(object.getMeeplePlacement(), false));
		}
		return meeple_points;
	}
	
	public void rotate() {
		Rotation[] copy = {myRotation[0], myRotation[1], myRotation[2], myRotation[3]};
		for (int i = 0; i < myRotation.length; i++) {
			myRotation[i] = copy[(i + 3) % 4];
		}
		myImage = rotateImage(ROTATION_ANGLE);
		setIcon(new ImageIcon(myImage));
	}
	
	private BufferedImage rotateImage(float angle) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(angle), myImage.getWidth() / 2.0, myImage.getHeight() / 2.0);
		transform.preConcatenate(findTranslation(transform, angle));
		BufferedImageOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(myImage, null);
	}
	
	private AffineTransform findTranslation(AffineTransform transform, float angle) {
		Point2D point_in = null, point_out = null;
		if (angle > 0 && angle <= 90) {point_in = new Point2D.Double(0,0);}
		else if (angle >= 270 && angle < 360) {point_in = new Point2D.Double(myImage.getWidth(), 0);}
		point_out = transform.transform(point_in, null);
		double  ytrans = point_out.getY();
		if (angle > 0 && angle <= 90) {point_in = new Point2D.Double(0, myImage.getHeight());}
		else if (angle >= 270 && angle < 360) {point_in = new Point2D.Double(0,0);}
		point_out = transform.transform(point_in,  null);
		double xtrans = point_out.getX();
		AffineTransform tat = new AffineTransform();
		tat.translate(-xtrans, -ytrans);
		return tat;
	}
	
	public void updateAllConnections(List<AbstractTile> tiles) {
		for (AbstractTile tile : tiles) {
			Rotation other_side = getMatchingSide(tile);
			if (other_side == null) {continue;}
			Rotation my_side = getOppositeSide(other_side);
			other_side = tile.getRotatedSide(other_side);
			my_side = getRotatedSide(my_side);
			
			for (AbstractTileObject object : myObjects) {
				if (!object.containsSide(my_side)) {continue;}
				for (AbstractTileObject other_object : tile.getObjects()) {
					if (!other_object.containsSide(other_side)) {continue;}
						object.connect(my_side, other_side, other_object);
						other_object.connect(other_side, my_side, object);
				}
			}
		}
	}
	
	/*
	 * Check to see if tiles current placement is valid. Surrounding_tiles should
	 * be a list of all tiles adjacent to this tile (i.e. north, east, south, west) of
	 * this tile that are non-empty.
	 */
	public boolean validateMove(List<AbstractTile> surrounding_tiles) {
		if (surrounding_tiles.isEmpty()) {return false;} //all surrounding tiles do not contain non-empty tiles
		for (AbstractTile other : surrounding_tiles) {
			if (!validateMove(other)) {return false;}
		}
		return true;
	}
	
	private boolean validateMove(AbstractTile other) {
		Rotation other_side = getMatchingSide(other);
		Rotation my_side = getOppositeSide(other_side);
		other_side = other.getRotatedSide(other_side);
		my_side = getRotatedSide(my_side);
		return other.matchHash(other_side) == matchHash(my_side) && validateMeeplePosition(my_side, other_side, other.getObjects());
	}
	
	
	private boolean validateMeeplePosition(Rotation my_rotation, Rotation other_rotation, List<AbstractTileObject> connected) {
		if (myMeeple == null) {return true;}
		for (AbstractTileObject object : connected) {
			if (myMeeple.getObject().validateConnection(my_rotation, other_rotation, object)) {
				if (object.connectedObjectsOccupied()) {return false;} 
			}
		}
		return true;
	}
	
	
	private int matchHash(Rotation side) {
		final int prime = 31;
		int num_roads = 0;
		int num_cities = 0;
		int num_farms = 0;
		
		for (AbstractTileObject object : myObjects) {
			if (!object.containsSide(side)) {continue;}
			if (object instanceof Road) {num_roads++;}
			else if (object instanceof Farm) {num_farms++;}
			else {num_cities++;}
		}
		
		return ((prime * 1 + num_roads) * prime + num_farms) * prime + num_cities;
	}
	
	private Rotation getOppositeSide(Rotation side) {
		Rotation[] rotations = {Rotation.NORTH, Rotation.EAST, Rotation.SOUTH, Rotation.WEST};
		int index = Arrays.asList(rotations).indexOf(side);
		return rotations[(index + 2) % 4];
	}
	
	private Rotation getRotatedSide(Rotation side) {
		Rotation[] rotations = {Rotation.NORTH, Rotation.EAST, Rotation.SOUTH, Rotation.WEST};
		int index = Arrays.asList(rotations).indexOf(side);
		return myRotation[index];
	}
	
	//return other tile's matching side, doesnt account for other tile's rotations
	private Rotation getMatchingSide(AbstractTile tile) {
		if (tile.getRow() == myRow - 1) {return Rotation.SOUTH;}
		else if (tile.getRow() == myRow + 1) {return Rotation.NORTH;}
		else if (tile.getCol() == myCol - 1) {return Rotation.EAST;}
		else if (tile.getCol() == myCol + 1) {return Rotation.WEST;}
		return null;
	}
	
	@Override
	public String toString() {
		return myName;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
			
		if (showMeeplePlacement) {
			g.setColor(Color.RED);
			for (Point point : getMeeplePoints()) {
				g.fillOval(point.x - 4, point.y - 4, 8, 8);
			}
		}
		
		if (myMeeple != null) {
			int x = myMeeple.getObject().getMeeplePlacement().x;
            int y = myMeeple.getObject().getMeeplePlacement().y;
            Point p = new Point();
            p.setLocation(x, y);
            p = rotatePoint(p, false);
            g.drawImage(myMeeple.getBufferedImage(), p.x - myMeeple.getBufferedImage().getWidth() / 2, p.y - myMeeple.getBufferedImage().getHeight() / 2, this);
            if (!showMeeplePlacement && Controller.isShowMeepleScore()) {
            	g.drawString(Integer.toString(myMeeple.getScore()), x, y);
            }
		}
	}
	
	/*
	public void debug() {
		System.out.println("\n \n \n debug");
		for (AbstractTileObject object : myObjects) {
			System.out.println(object.toString() + ": " + object.calculateScore());
		}
	} */

	public boolean hasMeepleAt(Point point) {
		if (myMeeple == null) {return false;}
		return myMeeple.getObject().getMeeplePlacement().equals(rotatePoint(point, true));
	}

	public boolean isShowingMeeplePlacement() {
		return showMeeplePlacement;
	}

	public AbstractTileObject getObjectAt(Point point) {
		
		for (AbstractTileObject object : myObjects) {
			if (object.getMeeplePlacement().equals(rotatePoint(point, true))) {
				return object;
			}
		}
		return null;
	}
	
	private int getNumRotations() {
		 return Arrays.asList(myRotation).indexOf(Rotation.NORTH);
	}
	
	
	private Point rotatePoint(Point point, boolean unrotate) {
		int numRotations = getNumRotations();
		if (unrotate) {numRotations = 4 - getNumRotations();}
		
		double radians = Math.toRadians(90 * numRotations);
		double x = point.x;
		double y= point.y;
		
		x = x - TILE_WIDTH / 2.0;
		y = y - TILE_HEIGHT / 2.0;
		
		double xPrime= Math.cos(radians) * x - Math.sin(radians) * y + TILE_WIDTH / 2.0;
		double yPrime = Math.sin(radians) * x + Math.cos(radians) * y + TILE_HEIGHT / 2.0;
		
		Point rotated = new Point();
		rotated.setLocation(xPrime, yPrime);
		return rotated;
		
		
	}
}
