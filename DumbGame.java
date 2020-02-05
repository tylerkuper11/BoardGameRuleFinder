import java.util.*;

public class DumbGame 
{
	public static void main(String[] args)
	{
		gameBoard();
		
    }

	public static void gameBoard()
	{
		int row,col;
		int board[][] = new int[6][8];
		for(row=0; row<board.length;row++){
			for(col=0; col<board[row].length;col++){
				board[row][col]=0;
			}
		}
		Player player1 = new Player();
		Player player2 = new Player();
		
		for(row=0; row<board.length;row++){
			System.out.println();
		for(col=0; col<board[row].length;col++){
			System.out.print(board[row][col]);
		
		}
		}
	}
	

	private int die; // Create the die
	
	public void roll() 
	{
            die = (int)(Math.random()*6) + 1;
    }
    

   
 
}






