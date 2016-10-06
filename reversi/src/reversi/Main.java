package reversi;

import javax.swing.SwingUtilities;

public class Main
{

	public static void main(String[] args)
	{
		Controller c = new Controller();
		GameModel m = new GameModel(c);
		BoardSquare v = new BoardSquare(c);
		c.setViewAndModel(v, m);
		
		
		m.createBoard();			//creates data variables
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				v.create();			//creates UI
			}
		});
		
		
	}
}
