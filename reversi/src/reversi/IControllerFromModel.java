package reversi;

public interface IControllerFromModel
{

	int getTurn();
	void displayBoard();
	void changeTurnDisplay(int i);
	int squareClicked(int index, int player, boolean AI);
}
