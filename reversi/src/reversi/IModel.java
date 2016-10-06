package reversi;

public interface IModel {

	void createBoard();
	void changeTurn();
	int[] getBoard();
	int getTurn();
	public void updateBoard(int[] new_board);
}
