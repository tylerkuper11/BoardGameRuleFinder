import java.util.*;



public class DumbGamev2 
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
	int xCount=pieceCount;
	int oCount=pieceCount;
	int attackTarget;

	public DumbGamev2()
	{
		board= new int[16];
		for(int i=0;i<pieceCount;i++)
		{
			board[i]=X;
			board[board.length-1-i]=O;
		}
		//~ board[0] = X;
		//~ board[board.length-1]=O;
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
	
	public int countToken(int token,int piece)
	{
		int count=0;
		if(token==X){
			for(int i=0;i<board.length;i++)
				if(board[i]==token)
					count++;
					if(count==piece+1){
						return count;
					}
		}else{
			for(int i=0;i<board.length;i++)
				if(board[board.length-i-1]==token)
					count++;
					if(count==piece+1){
						return count;
					}
		}
		return count;
		
	}
	
	public int getLocation(int token,int piece)
	{
		if(token==X){
			for(int i=0;i<board.length;i++)
			{
				if(board[i]==token)piece--;
				if(piece==-1) return i;
			}
			return -1;
		}else{
			for(int i=board.length-1;i>=0;i--)
			{
				if(board[i]==token)piece--;
				if(piece==-1) return i;
			}
			return -1;
		}
	}
	//~ public boolean isValid(int[] move)
	
	public void play(boolean useDieOne,int piece)
	{
		if(useDieOne) System.out.println(slide(getPlayer(),piece));
		else attack(getPlayer(),piece);

	}
	
	public boolean slide(int token,int piece)
	{
		if(token==X){
			if(countToken(token,piece)==0){
				return false;
			}
		}else if(token==O){
			if(countToken(token,piece)==0){
				return false;
			}
		}
		int currentLocation=getLocation(token,piece);
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
	
	public boolean attack(int token,int piece)
	{
		int currentLocation=getLocation(token,piece);
		if(token == 1) {
			attackTarget = getLocation(token,piece) + 1;
		}else{
			attackTarget = getLocation(token,piece) - 1;
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
		xCount=0;
		for(int i=0;i<board.length;i++)
			if(board[i]==X)
				xCount++;
		if(xCount==0) return O;
		oCount=0;
		for(int i=0;i<board.length;i++)
			if(board[i]==O)
				oCount++;
		if(oCount==0) return X;	
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
		Scanner pc=new Scanner(System.in);
		DumbGamev2 game=new DumbGamev2();
		System.out.println(game);
		while(game.getWinner()==0)
		{
			System.out.printf("+---------------------------+\n");
			System.out.printf("| "+game.getPlayerCharacter()+"'s turn                  |\n");
			System.out.printf("| A move costs %d energy     |\n",game.moveCost);
			System.out.printf("| An attack costs %d energy  |\n",game.attackCost);
			System.out.printf("+---------------------------+\n");
			while (game.playerEnergy >0) {
				System.out.println(game);
				System.out.printf("Your energy is: %d\n", game.playerEnergy);
				System.out.printf("Which piece would you like to move?\n");
				if(game.getPlayer() == game.X) System.out.printf("Enter a number piece from 0-%d\n", game.xCount-1);
				else System.out.printf("Enter a number piece from 0-%d\n", game.oCount-1);	
				int pieceChoice=pc.nextInt();
				System.out.printf("Would you like to move or attack?\n");
				System.out.printf("Move is 1\nAttack is 2\n");
				int moveChoice=sc.nextInt();
				game.play(moveChoice==1,pieceChoice);
			}
			game.playerEnergy = game.energy;
			game.turn++;		
		}
	}
 
}
