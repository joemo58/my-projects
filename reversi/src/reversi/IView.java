package reversi;

public interface IView
{
	public void displayBoard();

	public void changeTurnDisplay(int turn);

	public void displayScores(int p1Score, int p2Score);
}
