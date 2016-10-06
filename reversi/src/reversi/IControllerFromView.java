package reversi;

public interface IControllerFromView
{
	//void displayBoard(BoardSquare[] sqrArray);
	int[] getBoardState();
	int getTurn();
	int squareClicked(int player, int color, boolean AI);
	public void changeTurn();
	void checkScores();
}
