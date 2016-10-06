package reversi;

public class Controller implements IControllerFromModel, IControllerFromView
{
	IModel model;
	IView view;

	public void setViewAndModel(BoardSquare v, GameModel m)
	{
		model = m;
		view = v;
	}

	public int[] getBoardState()
	{
		return model.getBoard();
	}

	public int getTurn()
	{
		return model.getTurn();
	}

	public void changeTurn()
	{
		model.changeTurn();
	}

	public void displayBoard()
	{
		view.displayBoard();
	}

	public void changeTurnDisplay(int turn)
	{
		view.changeTurnDisplay(turn);
	}

	@Override
	public int squareClicked(int index, int player, boolean AI)
	{
		int max_count = 0;
		int new_count = 0;
		int max_xoffset = 0;
		int max_yoffset = 0;
		int[] boardState = model.getBoard();			//get board state from model

		System.out.println("current player = " + player);
		// logic for when piece is clicked
		
		if (player == getTurn()) 			// if it's there go
		{
			if (boardState[index] == 0)		//if square is empty
			{
				for (int xoffset = -1; xoffset <= 1; xoffset++)
				{
					for (int yoffset = -1; yoffset <= 1; yoffset++)		//check in evry direction
					{
						//if (xoffset != 0 && yoffset != 0){
							new_count = countPieces(index, player, xoffset, yoffset, false);
							//basically checks if player can move or not using that square
							if (new_count >= max_count)
							{
								max_count = new_count; // move with the highest
														// score
								max_xoffset = xoffset;
								max_yoffset = yoffset;
							}
						//}
					}
				}
			}

		} else								//if not their turn
		{
			System.out.println("It's not your turn!");
		}

		System.out.println("max = " + max_count);
		// System.out.print(" "+max_xoffset);
		// System.out.print(" " +max_yoffset);

		if (max_count == 0)
		{
			System.out.println("No move for that square!");
		} 
		else if (AI == false)
		{
			countPieces(index, player, max_xoffset, max_yoffset, true); //capture using route to take max no. pieces
			// change turn either now or back in the view
			displayBoard();
			changeTurn();
			changeTurnDisplay(getTurn());
			System.out.println("------------------Next turn----------------");
			return 0; // pieces
		}
		
		return max_count;
	}

	public int countPieces(int index, int player, int xoffset, int yoffset, boolean capture)
	{

		int[] board = model.getBoard();
		int counter = 0;

		int currentSquare = 0;
		yoffset = yoffset * 8;
		int targetSquareIndex = index + xoffset + yoffset;

		System.out.println("index searched: " + targetSquareIndex);

		while (targetSquareIndex >= 0 && targetSquareIndex < 64)
		{ // in range of the board up/down
			currentSquare = board[targetSquareIndex]; // move to next square

			if (currentSquare == 0) // if square is empty
			{
				counter = 0;
				break;

			} else 					//if square not empty:
			{
				
				if (currentSquare != player)		//is oppositions piece
				{		
					int column = targetSquareIndex % 8;
					int row = (targetSquareIndex - column) / 8;
					
					if (column < 0 || column > 7){
						System.out.println("off da grid");
						break;
					}
					else if (row < 0 || row > 7){
						System.out.println("off da grid");
						break;
					}

					else {
						counter++;
						targetSquareIndex = targetSquareIndex + xoffset + yoffset; // mmove to nxt piece
					}
				} else if (currentSquare == player)			//if own players piece
				{ // take each piece gone past
					for (int a = 0; a <= counter; a++)
					{
						targetSquareIndex = targetSquareIndex - xoffset - yoffset;
						if (capture == true)
						{
							board[targetSquareIndex] = player; // take the piece
						}
					}
					model.updateBoard(board); // apply to boardState
					return counter;
				}
			}
		}		
		if(targetSquareIndex < 0 || targetSquareIndex >= 64){
			counter = 0;
			System.out.println("Square search = out of bounds");
		}
		
		System.out.println("count = " + counter);
		//System.out.println("square out of bounds");		//not within 0-63 index
		return counter;
	}
	
	public void checkScores(){
		int[] board = model.getBoard();
		int p1Score = 0;
		int p2Score = 0;
		boolean full = true;
		
		for (int i = 0; i < board.length; i++){
			if (board[i] == 1){
				p1Score++;
			} 
			else if (board[i] == 2) {
				p2Score++;
			}
			else {
				full = false;
			}
		} 
		
		if (full == true){
			view.displayScores(p1Score, p2Score);
		}
	}

}