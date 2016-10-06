package reversi;

public class GameModel implements IModel
{

	IControllerFromModel controller;

	private int[] boardState = new int[64];					//holds the boardstate
	private int whos_turn = 1; // 1 = w, 2 = b

	public GameModel(IControllerFromModel c)
	{
		this.controller = c;

	}

	public void createBoard()
	{
		for (int i = 0; i <= boardState.length - 1; i++)
		{
			if (i == 27 || i == 36)
			{
				boardState[i] = 1; // 0 = empty, 1 = white, 2 = black
			} else if (i == 28 || i == 35)
			{
				boardState[i] = 2;
			} else
			{
				boardState[i] = 0;
			}
		}
	}

	public void changeTurn()
	{
		if (whos_turn == 1)
		{
			whos_turn = 2;
		} else
		{
			whos_turn = 1;
		}

		
	}

	public int[] getBoard()
	{
		return boardState;

	}

	@Override
	public int getTurn()
	{
		return whos_turn;
	}

	public void updateBoard(int[] new_board)
	{
		this.boardState = new_board;
		controller.displayBoard();
	}


}
