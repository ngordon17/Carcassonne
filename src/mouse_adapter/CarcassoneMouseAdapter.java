package mouse_adapter;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import special_tiles.AbstractTile;
import special_tiles.EmptyTile;
import board.CarcassoneBoard;
import board.TilePanel;
import controller.Controller;
import deck.TileDeck;

public class CarcassoneMouseAdapter extends MouseAdapter {
	private Controller myController;
	private JLayeredPane myLayeredPane;
	private TilePanel myClickedPanel;
	private AbstractTile myTile;
	
	public CarcassoneMouseAdapter(Controller controller, JLayeredPane layeredPane) {
		myController = controller;
		myLayeredPane = layeredPane;
	}
	
	private void reset() {
		if (myTile != null) {
			myLayeredPane.remove(myTile);
			myLayeredPane.revalidate();
			myLayeredPane.repaint();
		}
		myTile = null;
		myClickedPanel = null;
	}
	
	public Point translatePoint(Component component, MouseEvent e) {		
		return SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), component);
	}
	
	public boolean containsPoint(Component component, MouseEvent e) {
		Point translated = translatePoint(component, e );
		return translated.getX() > 0 && translated.getY() > 0
			&& translated.getX() < component.getWidth() && translated.getY() < component.getHeight();
	}
	
	public boolean containsPoint(Component component, Point p, int radius, MouseEvent e) {
		Point translated = translatePoint(component.getParent(), e);
		double transX = translated.getX();
		double transY = translated.getY();
		return transX < p.x + radius && transX > p.x - radius && transY > p.y - radius && transY < p.y + radius;	
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		CarcassoneBoard board = CarcassoneBoard.getInstance();
		TileDeck deck = TileDeck.getInstance();
		TilePanel clickedPanel = null;
		
		if (containsPoint(deck, event)) {
			clickedPanel = deck;
		}
		else if (containsPoint(CarcassoneBoard.getViewPort(), event)) {
			Component component = board.getComponentAt(translatePoint(board, event));
			if (!(component instanceof TilePanel)) {return;} //user clicked in between tiles
			clickedPanel = (TilePanel) component;		
		}
	
		if (clickedPanel == null || clickedPanel.getTile() instanceof EmptyTile) {
			return;
		}
		AbstractTile tile = clickedPanel.getTile();
		
		if (tile.isShowingMeeplePlacement()) {
			for (Point point : tile.getMeeplePoints()) {
				//OFF BY BORDER FOR BOARD...
				if (containsPoint(tile, point, 4, event)) {
					if (tile.hasMeepleAt(point)) {
						myController.getCurrentPlayer().removeMeeple(tile.getMeeple());		
					}
					else {
						tile.setMeeple(myController.getCurrentPlayer().getNewMeeple(tile, tile.getObjectAt(point)));
					}
					reset();
					return;
				}
			}
		}
		if (tile.isDNDEnabled()) {
			tile.rotate();
		}
		reset();	
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		CarcassoneBoard board = CarcassoneBoard.getInstance();
		TileDeck deck = TileDeck.getInstance();
		
		if (containsPoint(deck, event)) {
			myClickedPanel = deck;
			myTile = myClickedPanel.getTile();
		}
		else if  (containsPoint(CarcassoneBoard.getViewPort(), event)) {
			Component component = board.getComponentAt(translatePoint(board, event));
			if (component instanceof TilePanel) { //to make sure hasn't clicked in between tiles
				myClickedPanel = (TilePanel) component;
				myTile = myClickedPanel.getTile();
			}
		}
		
		if (myTile == null || myTile instanceof EmptyTile || !myTile.isDNDEnabled()) {
			reset();
			return;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent event) {
		if (myTile == null) {
			reset();
			return;
		}
		myClickedPanel.setEmpty();
		try {
			myLayeredPane.add(myTile, JLayeredPane.DRAG_LAYER);
		} catch (IllegalArgumentException e) {
			//TODO: deal with this? gives error for some unknown reason, but doesnt effect anything? ignore...dumb error cus jswing sucks
		}	
		int x = translatePoint(myLayeredPane, event).x - myTile.getWidth() / 2;
		int y = translatePoint(myLayeredPane, event).y - myTile.getHeight() / 2;		
		myTile.setLocation(x, y);
		myLayeredPane.revalidate();
		myLayeredPane.repaint();	
	}
	
	@Override 
	public void mouseReleased(MouseEvent event) {
		if (myTile == null) {
			return;
		}
		
		CarcassoneBoard board = CarcassoneBoard.getInstance();
		myLayeredPane.remove(myTile);
		
		TilePanel dropped = null;
		if (containsPoint(CarcassoneBoard.getViewPort(), event)) {
			dropped = (TilePanel) board.getComponentAt(translatePoint(board, event));
		}
		if (dropped == null || !(dropped.getTile() instanceof EmptyTile)) {
			//reset tile to original spot
			myClickedPanel.setTile(myTile);
			reset();
			return;
		}
		dropped.setTile(myTile);
		reset();
	}
}
