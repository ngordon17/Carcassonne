package player;

import java.util.ArrayList;
import java.util.List;

import special_tiles.AbstractTile;
import tile_objects.AbstractTileObject;

import meeple.Meeple;

public class Player {
	private int myID;
	private int myTotalNumMeeples;
	private List<Meeple> myMeeples;
	private int myScore;
	
	public Player(int id, int numMeeples) {
		myID = id;
		myTotalNumMeeples = numMeeples;
		myMeeples = new ArrayList<Meeple>();
		myScore = 0;
	}
	
	public int getID() {
		return myID;
	}
	
	public int getNumRemainingMeeples() {
		return myTotalNumMeeples - myMeeples.size();
	}
	
	public boolean hasAnotherMeeple() {
		return getNumRemainingMeeples() > 0;
	}
	
	public Meeple getNewMeeple(AbstractTile tile, AbstractTileObject object) {
		Meeple meeple = new Meeple(Meeple.getColor(myID), tile, object, this);
		myMeeples.add(meeple);
		PlayerPanel.getInstance().refresh();
		return meeple;
	}
	
	public void removeMeeple(Meeple meeple) {
		meeple.remove();
		myMeeples.remove(meeple);
		PlayerPanel.getInstance().refresh();
	}
	
	public int getScore() {
		return myScore;
	}
	
	public void increaseScore(int points) {
		myScore += points;
		PlayerPanel.getInstance().refresh();
	}
	
	public void refresh(boolean gameOver) {
		List<Meeple> completedMeeples = new ArrayList<Meeple>();
		for (Meeple meeple : myMeeples) {
			if (meeple.onCompletedObject() || gameOver) {
				myScore += meeple.getScore();
				completedMeeples.add(meeple);
			}
		}
		for (Meeple meeple : completedMeeples) {
			removeMeeple(meeple);
		}
	}
	

}
