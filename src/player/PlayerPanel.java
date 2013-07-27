package player;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import meeple.Meeple;

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel {
	private List<Player> myPlayers;
	private static PlayerPanel myInstance;
	
	private PlayerPanel() {
		myPlayers = new ArrayList<Player>(Arrays.asList(new Player(1, 6), new Player(2, 6), new Player(3, 6)));
		reset(myPlayers);
	}
	
	public static PlayerPanel getInstance() {
		if (myInstance == null) {
			myInstance = new PlayerPanel();
		}
		return myInstance;
	}
	
	public void reset(List<Player> players) {
		myPlayers = players;
		removeAll();
		setLayout(new BorderLayout());

		JPanel meeplePanel = new JPanel();
		meeplePanel.setLayout(new GridLayout(1, players.size()));
		JPanel playerPanel = new JPanel();
		
		int numCols = (int) Math.ceil(players.size() / 3);
		playerPanel.setLayout(new GridLayout(3, numCols));
		
		for (Player player : players) {
			
			JLabel meeple = new JLabel(getPlayerInfo(player), new ImageIcon(Meeple.getBufferedImage(Meeple.getColor(player.getID()))), 0);
			meeplePanel.add(meeple);
			
			JLabel pLabel = new JLabel("Player " + player.getID() + ": " + player.getScore() + " ");
			playerPanel.add(pLabel);
		}
		
		add(meeplePanel, BorderLayout.CENTER);
		add(playerPanel, BorderLayout.WEST);	
		revalidate();
		repaint();
	}
	
	public void refresh() {
		reset(myPlayers);
	}
	
	public String getPlayerInfo(Player player) {
		return "x" + player.getNumRemainingMeeples() + " (Player " + player.getID() + ")";
	}
	

}
