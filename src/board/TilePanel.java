package board;

import javax.swing.JPanel;

import special_tiles.AbstractTile;
import special_tiles.EmptyTile;

@SuppressWarnings("serial")
public class TilePanel extends JPanel {
	
	private AbstractTile myTile;
	private int myRow;
	private int myCol;
	
	public TilePanel(int row, int col) {
		myRow = row;
		myCol = col;
		setTile(new EmptyTile(row, col));
	}
	
	public void setTile(AbstractTile tile) {
		removeAll();
		tile.setCellLocation(myRow, myCol);
		myTile = tile;
		add(myTile);
		revalidate();
	}
	
	public AbstractTile getTile() {
		return myTile;
	}
	
	public void setEmpty() {
		setTile(new EmptyTile(myRow, myCol));
	}
}
	