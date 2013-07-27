package board;

import board.TilePanel;

import java.awt.Dimension;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import special_tiles.AbstractTile;
import special_tiles.EmptyTile;
import special_tiles.TileFactory;
import tiles.StraightRoad;

@SuppressWarnings("serial")
public class CarcassoneBoard extends JPanel {
	private int myBoardSize;
	private int myNumTiles = 30;
	private static final TileFactory myStartingTileFactory = StraightRoad.getFactory();
	private AbstractTile myStartingTile;
	private TilePanel[][] myTiles;
	private static JScrollPane myScrollPane;
	private static CarcassoneBoard myInstance;
	
	private CarcassoneBoard() {
		reset(myNumTiles);
	}
	
	public static CarcassoneBoard getInstance() {
		if (myInstance == null) {
			myInstance = new CarcassoneBoard();
		}
		return myInstance;
	}
	
	public static void setScrollPane(JScrollPane scrollPane) {
		myScrollPane = scrollPane;
	}
	
	public static JScrollPane getScrollPane() {
		return myScrollPane;
	}
	
	public static JViewport getViewPort() {
		return myScrollPane.getViewport();
	}
	
	public void reset(int num_tiles) {
		removeAll();
		myNumTiles = num_tiles;
		myBoardSize = (int) Math.ceil(num_tiles / 2.0);
		
		setLayout(new GridLayout(myBoardSize, myBoardSize, 0, -5));

		setPreferredSize(new Dimension(myBoardSize * AbstractTile.TILE_WIDTH, myBoardSize * AbstractTile.TILE_HEIGHT + myBoardSize));	
		myStartingTile = myStartingTileFactory.manufactureTile((int) Math.ceil(myBoardSize / 2.0 - 1), (int) Math.ceil(myBoardSize / 2.0 - 1));
		
		myTiles = new TilePanel[myBoardSize][myBoardSize];
		for (int i = 0; i < myBoardSize; i++) {
			for (int j = 0; j < myBoardSize; j++) {
				myTiles[i][j] = new TilePanel(i, j);
				add(myTiles[i][j]);
			}
		}
		myTiles[myStartingTile.getRow()][myStartingTile.getCol()].setTile(myStartingTile);
		
		centerView();
		revalidate();
		repaint();
	}
	
	public void centerView() {
		if (myScrollPane == null) {return;}
		JScrollBar horizontal = myScrollPane.getHorizontalScrollBar();
		JScrollBar vertical = myScrollPane.getVerticalScrollBar();
		horizontal.setValue((horizontal.getMaximum() + horizontal.getMinimum() - horizontal.getVisibleAmount()) / 2);
		vertical.setValue((vertical.getMaximum() + vertical.getMinimum() - vertical.getVisibleAmount())/ 2);
		vertical.setUnitIncrement(5);
		horizontal.setUnitIncrement(5);
	}
	
	/*
	 * Get surrounding tiles that are not EmptyTile instances
	 */
	public List<AbstractTile> getSurroundingTiles(AbstractTile tile) {
		List<AbstractTile> surrounding_tiles = new ArrayList<AbstractTile>();
		int row = tile.getRow();
		int col = tile.getCol();
		surrounding_tiles.addAll(getTile(row + 1, col));
		surrounding_tiles.addAll(getTile(row - 1, col));
		surrounding_tiles.addAll(getTile(row, col + 1));
		surrounding_tiles.addAll(getTile(row, col - 1));
		return surrounding_tiles;
	}
	
	public List<AbstractTile> getMonasteryTiles(AbstractTile tile) {
		List<AbstractTile> surrounding_tiles = new ArrayList<AbstractTile>();
		int row = tile.getRow();
		int col = tile.getCol();
		surrounding_tiles.addAll(getTile(row + 1, col));
		surrounding_tiles.addAll(getTile(row - 1, col));
		surrounding_tiles.addAll(getTile(row, col + 1));
		surrounding_tiles.addAll(getTile(row, col - 1));
		surrounding_tiles.addAll(getTile(row + 1, col + 1));
		surrounding_tiles.addAll(getTile(row - 1, col - 1));
		surrounding_tiles.addAll(getTile(row + 1, col - 1));
		surrounding_tiles.addAll(getTile(row - 1, col + 1));
		return surrounding_tiles;
	}
	
	/*
	 * Get tile if it is not an EmptyTile and is on the board...return tile as list of size 1. 
	 * If it is empty tile or not on board return empty list.
	 */
	private List<AbstractTile> getTile(int row, int col) {
		List<AbstractTile> tile_list = new ArrayList<AbstractTile>();	
		if (row < 0 || row >= myBoardSize || col < 0 || col >= myBoardSize 
		   || myTiles[row][col].getTile() instanceof EmptyTile) {return tile_list;}
		tile_list.add(myTiles[row][col].getTile());
		return tile_list;
	}
}
