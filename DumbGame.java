import java.util.*;
import java.util.ArrayList; 


public class DumbGame 
{
	int[] board;
	int X=1;
	int O=2;
	int turn=0;
	int die1;
	int die2;
	int pieceCount=3;
	int xCount=pieceCount;
	int oCount=pieceCount;
	int energy=4;
	int playerEnergy;
	int moveCost=1;
	int attackCost=2;
	int attackTarget;
	ArrayList<Integer> xPieces = new ArrayList<Integer>();
	ArrayList<Integer> oPieces = new ArrayList<Integer>();	
	ArrayList<Integer> xLoc = new ArrayList<Integer>();	
	ArrayList<Integer> oLoc = new ArrayList<Integer>();	

	public DumbGame()
	{
		board= new int[10];
		
		for(int i=0;i<pieceCount;i++)
		{
			xPieces.add(X);
			xLoc.add(i);
			board[i]=xPieces.get(i);
		}
		for(int i=0;i<pieceCount;i++)
		{
			oPieces.add(O);
			oLoc.add(board.length-1-i);
			board[board.length-1-i]=oPieces.get(i);
		}
		xCount=pieceCount;
		oCount=pieceCount;
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
	
	
	//~ public int getLocation(int token, int piece)
	//~ {
		//~ for(int i=0;i<board.length;i++)
		//~ {
			//~ if(token==X){
				//~ if(board[i]==xPieces.get(piece))return i;
			//~ }else if(token==O){
				//~ if(board[i]==oPieces.get(piece))return i;
			//~ }
		//~ }
		//~ return -1;
		
	//~ }
	
	public int countToken(int token)
	{
		int count=0;
		for(int i=0;i<board.length;i++)
			if(board[i]==token)
				count++;
		return count;
	}
	
	public int getLocation(int token, int piece)
	{
		for(int i=0;i<board.length;i++)
		{
			if(board[i]==token)piece--;
			if(piece==-1) return i;
		}
		return -1;
		if (token==X) 
		{
			return xLoc.get(piece);
		} else {
			return oLoc.get(piece);
		}
	}
	//~ public boolean isValid(int[] move)
	
	public void play(boolean useDieOne, int piece)
	{
		if(useDieOne) slide(getPlayer(),piece);
		else attack(getPlayer(), piece);

	}
	
	public boolean slide(int token, int piece)
	{
		int currentLocation=getLocation(token,piece);
		int target=currentLocation+getDirection(token);
		if(token==X){
			if(xPieces.get(piece)==0){
				return false;
			}
		}else if(token==O){
			if(oPieces.get(piece)==0){
				return false;
			}
		}
		if (playerEnergy >= 1)
		{
			if(target<0 || target>=board.length)
			return false;
			if(board[target]>0)
			{
				return false;
			}
			board[currentLocation]=0;
			board[target]=token;
			if (token==X)
			{
				xLoc.set(piece,currentLocation+getDirection(token));
			}else{
				oLoc.set(piece,currentLocation+getDirection(token));
			}
			playerEnergy--;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean attack(int token, int piece)
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
				if(token==X){
					playerEnergy = playerEnergy - attackCost;
					board[attackTarget]=0;
					return true;
				} else if(token==O){
					playerEnergy = playerEnergy - attackCost;
					board[attackTarget]=0;
					return true;
				}
			} else {
				return false;
			}
		}else{
			return false;
		}
	}
	
	//~ public boolean attack(int token, int piece)
	//~ {
		//~ int currentLocation=getLocation(token,piece);
		//~ if(token == X) 
		//~ {
			//~ if(playerEnergy>=attackCost)
			//~ {
				//~ attackTarget = getLocation(token,piece) + 1;
				//~ for(int i=0;i<oCount;i++) 
				//~ {
					//~ if(board[attackTarget]==oLoc.get(i))
					//~ {
						//~ oPieces.set(i, 0);
						//~ board[attackTarget] = 0;
						//~ oCount--;
						//~ playerEnergy=playerEnergy-attackCost;
						//~ return true;
					//~ }
				//~ }
			//~ }else{
				//~ return false;
			//~ }
		//~ } else if(token==O){
			//~ if(playerEnergy>=attackCost)
			//~ {
				//~ attackTarget = getLocation(token,piece) - 1;
				//~ for(int i=0;i<xCount;i++) 
				//~ {
					//~ if(board[attackTarget]==xLoc.get(i))
					//~ {
						//~ xPieces.set(i, 0);
						//~ board[attackTarget] = 0;
						//~ xCount--;
						//~ playerEnergy=playerEnergy-attackCost;
						//~ return true;
					//~ }
				//~ }
			//~ }else{
				//~ return false;
			//~ }
		//~ } else {
			//~ return false;
		//~ }
		//~ return false;
	//~ }
	
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
		Scanner pc=new Scanner(System.in);
		DumbGame game=new DumbGame();
		
		
		System.out.println(game);
		while(game.getWinner()==0)
		{
			System.out.println(game.getPlayerCharacter()+" turn");
			System.out.printf("+---------------------------+\n");
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






