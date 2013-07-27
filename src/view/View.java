package view;

import board.CarcassoneBoard;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import mouse_adapter.CarcassoneMouseAdapter;
import player.PlayerPanel;
import controller.Controller;
import deck.TileDeck;

@SuppressWarnings("serial")
public class View extends JFrame {
	private static final String TITLE = "Carcassone by Nick Gordon";
	private static final String LOGO_PATH = "src/resources/Carcassone_Logo.png";
	private Controller myController;
	private CarcassoneMenuBar myMenuBar;
	private JButton mySkipButton;
	private JButton mySubmitButton;
	private JButton myNextTileButton;
	
	public View(Controller controller) {
		setTitle(TITLE);
		setIconImage(new ImageIcon(LOGO_PATH).getImage()); //set image for minimization icon
		myController = controller;
		
		initMenuBars();
		initPanels();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		pack();
		
		myController.newGame();
	}
	
	public void initMenuBars() {
		myMenuBar = new CarcassoneMenuBar(myController);
		setJMenuBar(myMenuBar);
	}
	
	public void initPanels() {
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(new BorderLayout());
		
		layeredPane.add(makePlayerPanel(), BorderLayout.NORTH);
		layeredPane.add(makeBoardPanel(), BorderLayout.CENTER);
		layeredPane.add(makeLowerPanel(), BorderLayout.SOUTH);
		
		CarcassoneMouseAdapter adapter = new CarcassoneMouseAdapter(myController, layeredPane);
		layeredPane.addMouseListener(adapter);
		layeredPane.addMouseMotionListener(adapter);
		
		CarcassoneBoard.getScrollPane().addMouseListener(adapter);
		CarcassoneBoard.getScrollPane().addMouseMotionListener(adapter);
		
		add(layeredPane);
		myController.newGame();	
		this.setPreferredSize(new Dimension(1250, 750));
	}
	
	private JPanel makePlayerPanel() {
		return PlayerPanel.getInstance();
	}
	
	private JScrollPane makeBoardPanel() {
		JScrollPane scrollPane = new JScrollPane(CarcassoneBoard.getInstance());		
		CarcassoneBoard.setScrollPane(scrollPane);
		return scrollPane;
	}
	
	private JPanel makeLowerPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(makeDeckPanel(), BorderLayout.WEST);
		panel.add(makeButtonPanel(), BorderLayout.EAST);
		return panel;
	}
	
	private JPanel makeDeckPanel() {
		return TileDeck.getInstance();
	}

	private JPanel makeButtonPanel() {
		JPanel panel = new JPanel();
		
		myNextTileButton = new JButton("Next Tile");
		myNextTileButton.addActionListener(new NextTileAction());
		panel.add(myNextTileButton);
		
		mySkipButton = new JButton("Skip");
		mySkipButton.addActionListener(new SkipAction(this));
		panel.add(mySkipButton);
		
		mySubmitButton = new JButton("Submit");
		mySubmitButton.addActionListener(new SubmitAction(this));
		panel.add(mySubmitButton);
		
		enableButtons();
		return panel;
	}
	
	private void enableButtons() {
		myNextTileButton.setEnabled(true);
		mySkipButton.setEnabled(true);
		mySubmitButton.setEnabled(true);
	}
	
	private class NextTileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!myController.getNextTile()) {
				JOptionPane.showMessageDialog(CarcassoneBoard.getInstance(), "Player must submit last move. Please submit and try again!", "Not Your Turn", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	private class SkipAction implements ActionListener {
		private View myView;
		
		public SkipAction(View view) {
			myView = view;
		}
		public void actionPerformed(ActionEvent e) {
			myController.skipTurn(myView);
		}
	}
	
	private class SubmitAction implements ActionListener {
		private View myView;
		
		public SubmitAction(View view) {
			myView = view;
		}
		
		public void actionPerformed(ActionEvent e) {
			if (!myController.submit()) {
				JOptionPane.showMessageDialog(CarcassoneBoard.getInstance(), "This move is not possible. Please try again", "Illegal Move", JOptionPane.PLAIN_MESSAGE);
			}
			myController.endOfGame(myView);
		}
	}
}
