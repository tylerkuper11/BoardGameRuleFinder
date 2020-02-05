
public class DumbGame 
{
	public static void main(String[] args)
	{
		gameBoard();
		
    }

	public static void gameBoard()
	{
		int row,col;
		char[][] boardGame = new char[7][11];
		for(row=0; row<boardGame.length;row++){
			for(col=0; col<boardGame[row].length;col++){
				if(row==0)
					boardGame[row][col]=(char)('A'+col);
				else if(col==0)
					boardGame[row][col]=(char)('A'+row);
				else
					boardGame[row][col]='*';
			}
		}

		for(row=0; row<boardGame.length;row++){
			System.out.println();
		for(col=0; col<boardGame[row].length;col++){
			System.out.print(boardGame[row][col]);
		}
		}
	}
	
	
}

