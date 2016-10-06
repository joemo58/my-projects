package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle.Control;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


//VIEW				//needs monitor/synchronized

public class BoardSquare extends ColorButton implements IView
{

	static IControllerFromView controller;

	BoardSquare p1SqrArray[] = new BoardSquare[64];		//array to display board state - p1
	BoardSquare p2SqrArray[] = new BoardSquare[64];		//array to display board state - p2
	JLabel playerLabel1 = new JLabel();
	JLabel playerLabel2 = new JLabel();

	// in monitor private int player = 0; // 1 = w, 2 = black
	// in monitorprivate int color = 2; // 0 = empty, 1=w, 2=b
	// in monitorprivate int index = 0;

	public BoardSquare(IControllerFromView c)
	{
		super(45, 45, Color.GREEN, 1, Color.BLACK);
		this.controller = c;
	}

	public BoardSquare(int player, int index)
	{
		super(45, 45, Color.GREEN, 1, Color.BLACK);
		monitor.setPlayer(player); // assign black/white to the square
		monitor.setIndex(index);
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				//BoardSquare currentSquare = (BoardSquare)e.getSource();
				System.out.println("----index of clicked: " + monitor.getIndex() + "-----");
				monitor.squareClicked( monitor.getIndex(), monitor.getPlayer(), false);	//how does monitor ref?
				displayBoard();
			}
		});
	}
	
	static class Monitor
	{
		private int player = 0; // 1 = w, 2 = black
		private int color = 2; // 0 = empty, 1=w, 2=b
		private int index = 0;

		public synchronized int getIndex() { return index; }
		public synchronized int getPlayer() {return player; }
		public synchronized int getColor() { return color; }
		public synchronized int squareClicked(int index, int player, boolean AI){return controller.squareClicked(index, player, AI);}
		public synchronized void setPlayer(int player) { this.player = player; }
		public synchronized void setIndex(int index) { this.index = index; }
		public synchronized void setColor(int color) { this.color = color; }	//color set by model
		
		public synchronized int checkDirections() //checks wach direction
		{
		return index;
		}
		
	}
	
	class AIButtonClick implements ActionListener {			//action listener for AI button
		
		//variables and constructor for which AI button it was
		@Override
		public void actionPerformed(ActionEvent e)
		{
			AIButton buttonClicked = (AIButton) e.getSource();
			int maxCount = 0;
			int finalIndex = 0;
			int i = 0;
			
			if (buttonClicked.getPlayer() == 1){
				System.out.println("player1 button clicked");
				for (i = 0; i < 63; i++){
					int newCount = monitor.squareClicked(i, buttonClicked.getPlayer(), true);
					if (newCount >= maxCount){		//if more than square before with max moves
						maxCount = newCount;
						finalIndex = i;				//change default index
					}
				}
				monitor.squareClicked(finalIndex, buttonClicked.getPlayer(), false);
			}
			
			else if (buttonClicked.getPlayer() == 2){
				System.out.println("player2 button clicked");
				for (i = 0; i < 63; i++){
					int newCount = monitor.squareClicked(i, buttonClicked.getPlayer(), true);
					if (newCount > maxCount){
						maxCount = newCount;
						finalIndex = i;
					}
				}
				monitor.squareClicked(finalIndex, buttonClicked.getPlayer(), false);
			}
			
		}
		
	}
	
	private Monitor monitor = new Monitor();


	@Override
	protected void paintComponent(Graphics g)
	{

		// super.paintComponent(g); // Not used
		if (borderColor != null)
		{
			g.setColor(borderColor);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (drawColor != null)
		{
			g.setColor(drawColor);
			g.fillRect(borderSize, borderSize, getWidth() - borderSize * 2, getHeight() - borderSize * 2);
		}

		if (monitor.getColor() == 1)
		{
			g.setColor(Color.WHITE);
			g.fillOval(1, 1, getWidth() - borderSize * 4, getHeight() - borderSize * 4);
			g.setColor(Color.BLACK);
			g.drawOval(1, 1, getWidth() - borderSize * 4, getHeight() - borderSize * 4);
			validate();
		}

		if (monitor.getColor() == 2)
		{
			g.setColor(Color.BLACK);
			g.fillOval(1, 1, getWidth() - borderSize * 4, getHeight() - borderSize * 4);
			g.setColor(Color.WHITE);
			g.drawOval(1, 1, getWidth() - borderSize * 4, getHeight() - borderSize * 4);
			validate();
		}

	}
	
 
	
	
	public void displayBoard()				//p1 upright, p2 upside down
	{
		int[] boardState = controller.getBoardState();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				for (int a = 0; a < p1SqrArray.length; a++)
				{
					if( p1SqrArray[a] != null){
						p1SqrArray[a].monitor.setColor(boardState[a]);
						p1SqrArray[a].repaint();
					}
				}
				
				for (int j = p2SqrArray.length - 1; j >= 0; j--) // add backwards
				{
					if(p1SqrArray[j] != null){
						p2SqrArray[j].monitor.setColor(boardState[j]);
						//p2SqrArray[j].revalidate();
						p2SqrArray[j].repaint();					
					}
				}
			
				
			}
		});
		
		controller.checkScores();			//if board full, check and print the scores
		
		//display the board after updating board state in controller
	}
		
		
	public void changeTurnDisplay(int turn)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {	
				if (turn == 1)
				{
					playerLabel1.setText("White Player - Click place to put piece");
					playerLabel2.setText("Black Player - Not your turn");
					repaint();
				} else
				{
					playerLabel2.setText("Black Player - Click place to put piece");
					playerLabel1.setText("White Player - Not your turn");
					repaint();
				}
			}
		});
	}
	

	public void displayScores(int p1Score, int p2Score)
	{
		String winner = "";
		if (p1Score > p2Score){
			winner = "White Wins";
		} 
		else if (p1Score < p2Score){
			winner = "Black Wins";
		}
		else {
			winner = "Draw";
		}
		
		String st = (winner + " " + p1Score + ":" + p2Score);
		JOptionPane.showMessageDialog(null, st);
		
	}


	public void create()
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String txt;
		
				JFrame frame1 = new JFrame();
				JPanel panel1 = new JPanel();
				JPanel board1 = new JPanel();
		
				if (controller.getTurn() == 1)
				{
					txt = "- Click place to put piece ";
				} else
				{
					txt = "-Not your turn";
				}
		
				playerLabel1.setText("White Player " + txt);
				AIButton AIButton1 = new AIButton("Greedy AI (play white)", 1);
				AIButton1.addActionListener(new AIButtonClick());
		
				frame1.setLayout(new BorderLayout(5, 5));
				frame1.setTitle("Reversi - White Player");
				frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame1.add(panel1);
		
				panel1.setLayout(new BorderLayout(5, 5));
				panel1.add(playerLabel1, BorderLayout.NORTH);
				panel1.add(board1, BorderLayout.CENTER);
				panel1.add(AIButton1, BorderLayout.SOUTH);
		
				int[] boardState = controller.getBoardState(); // return the boardState
																// from model
		
				board1.setLayout(new GridLayout(8, 8));
				for (int i = 0; i < p1SqrArray.length; i++)
				{
					p1SqrArray[i] = new BoardSquare(1, i);
					p1SqrArray[i].monitor.setColor(boardState[i]);
					board1.add(p1SqrArray[i]);
				}
		
				JFrame frame2 = new JFrame();
				JPanel panel2 = new JPanel();
				JPanel board2 = new JPanel();
		
				if (controller.getTurn() == 2)
				{
					txt = "- Click place to put piece ";
				} else
				{
					txt = "-Not your turn";
				}
		
				playerLabel2.setText("Black Player" + txt);
				AIButton AIButton2 = new AIButton("Greedy AI (play black)", 2);
				AIButton2.addActionListener(new AIButtonClick());
		
				frame2.setLayout(new BorderLayout(5, 5));
				frame2.setTitle("Reversi - Black Player");
				frame2.setDefaultCloseOperation(frame2.EXIT_ON_CLOSE);
				frame2.add(panel2);
		
				panel2.setLayout(new BorderLayout(5, 5));
				panel2.add(playerLabel2, BorderLayout.NORTH);
				panel2.add(board2, BorderLayout.CENTER);
				panel2.add(AIButton2, BorderLayout.SOUTH);
		
				board2.setLayout(new GridLayout(8, 8));
		
				for (int j = p2SqrArray.length - 1; j >= 0; j--) // add backwards
				{
					p2SqrArray[j] = new BoardSquare(2, j);
					p2SqrArray[j].monitor.setColor(boardState[j]);
					board2.add(p2SqrArray[j]);
				}
		
				frame1.pack();
				frame1.setLocationRelativeTo(null);
				frame1.setVisible(true);
				frame2.pack();
				frame2.setLocationRelativeTo(null);
				frame2.setVisible(true);
			}
		});
	}

}