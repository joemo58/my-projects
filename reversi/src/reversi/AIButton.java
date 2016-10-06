package reversi;

import javax.swing.JButton;

public class AIButton extends JButton
{
	
	int player = 0;
	public AIButton(String string)
	{
		super(string);
		
	}

	public AIButton(String string, int player)
	{
		super(string);
		setPlayer(player);
	}
	
	
	public int getPlayer(){
		return this.player;
	}
	
	public void setPlayer(int player){
		this.player = player;
	}
	
}
