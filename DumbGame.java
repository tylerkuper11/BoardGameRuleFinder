import java.util.*;



public class DumbGame 
{
	int[] board;
	int X=1;
	int O=2;
	int turn=0;
	int die1;
	int die2;
	int pieceCount=3;
	int energy=4;
	int playerEnergy;
	int moveCost=1;
	int attackCost=2;
	int attackTarget;

	public DumbGame()
	{
		board= new int[16];
		//~ for(int i=0;i<pieceCount;i++)
		//~ {
			//~ board[i]=X;
			//~ board[board.length-1-i]=O;
		//~ }
		board[0] = X;
		board[board.length-1]=O;
		playerEnergy = energy;
	}
	
	public int getPlayer()
	{
		if(turn%2==0)return X;
		return  O;
	}
	
	public String getPlayerCharacter()
	{
		if(turn%2==0)return "X";
		return  "O";
	}
	
	public int getDirection(int token)
	{
		if(token==X)return 1;
		else return -1;
	}
	
	public int getLocation(int token)
	{
		for(int i=0;i<board.length;i++) if(board[i]==token)return i;
		return -1;
	}
	//~ public boolean isValid(int[] move)
	
	public void play(boolean useDieOne)
	{
		if(useDieOne) slide(getPlayer());
		else attack(getPlayer());

	}
	
	public boolean slide(int token)
	{
		int currentLocation=getLocation(token);
		int target=currentLocation+getDirection(token);
		if (playerEnergy >= 1)
		{
			if(target<0 || target>=board.length)
			return false;
			board[currentLocation]=0;
			if(board[target]>0)
			{
				//~ if(getDirection(token)>0)board[0]=board[target];
				//~ else board[board.length-1]=board[target];
				return false;
			}
			board[target]=token;
			playerEnergy--;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean attack(int token)
	{
		int currentLocation=getLocation(token);
		if(token == 1) {
			attackTarget = getLocation(token) + 1;
		}else{
			attackTarget = getLocation(token) - 1;
		}
		if (board[attackTarget]!=token && board[attackTarget]>0)
		{
			if (playerEnergy >= attackCost)
			{
				playerEnergy = playerEnergy - attackCost;
				board[attackTarget]=0;
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}
	}
	
	public int getWinner()
	{
		if(board[0]==O)return O;
		if(board[board.length-1]==X)return X;
		return 0;
	}
	
	public String toString()
	{
		String top="+";
		String middle="|";
		for(int p:board)
		{
			top+="-";
			if(p==X)middle+="X";
			else if(p==O)middle+="O";
			else middle+=" ";
		}
		middle+="|\n";
		top+="+\n";
		return top+middle+top;
	}
	
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		DumbGame game=new DumbGame();
		System.out.println(game);
		while(game.getWinner()==0)
		{
			System.out.println(game.getPlayerCharacter()+" turn");
			while (game.playerEnergy >0) {
				System.out.println(game);
				System.out.printf("A move costs %d\n",game.moveCost);
				System.out.printf("An attack costs %d\n",game.attackCost);
				System.out.printf("Your energy is: %d\n", game.energy);
				System.out.printf("Would you like to move or attack?\n");
				System.out.printf("Move is 1\nAttack is 2\n");
				int choice=sc.nextInt();
				game.play(choice==1);
			}
			game.playerEnergy = game.energy;
			game.turn++;	
		}
	}
 
}






