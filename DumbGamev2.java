import java.util.*;



public class DumbGamev2 
{
	int[] board;
	int X=1;
	int O=2;
	int turn=0;
	int pieceCount=3;
	int energy=4;
	int playerEnergy;
	int move = 1;
	int attack = 2;
	int rest = 3;
	int moveCost=1;
	int attackCost=2;
	int restCost=1;
	int restUses=1;
	int playerRestUses=0;
	int xCount=pieceCount;
	int oCount=pieceCount;
	int attackTarget;

	public DumbGamev2 clone()
	{
		DumbGamev2 game=new DumbGamev2();
		for(int i=0;i<board.length;i++)game.board[i]=board[i];
		game.turn=turn;
		game.playerEnergy=playerEnergy;
		game.playerRestUses=playerRestUses;
		game.xCount=xCount;
		game.oCount=oCount;
		return game;
	}

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
		playerRestUses = restUses;
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
			for(int i=0;i<board.length;i++){
				if(board[i]==token){
					count++;
					if(count==piece+1){
						break;
					}
				}
			}
		}else{
			for(int i=0;i<board.length;i++){
				if(board[board.length-i-1]==token){
					count++;
					if(count==piece+1){
						break;
					}
				}
			}
		}
		if(count!=piece+1) count =0;
		
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
			return 0;
		}else{
			for(int i=board.length-1;i>=0;i--)
			{
				if(board[i]==token)piece--;
				if(piece==-1) return i;
			}
			return 0;
		}
	}
	
	public int[][] possibleMoves()
	{
		int[][] validMoves = new int[9][2];
		int count = 0;
		int listR = 0;
		if(getPlayer()==X){
			for(int i=0;i<board.length;i++)
			{
				if(board[i]==X){
					if(board[i+1]==O&&playerEnergy>=attackCost){
						validMoves[listR][0]= count;
						validMoves[listR][1]= attack;
						listR++;
					}
					if(board[i+1]==0&&playerEnergy>=moveCost){
						validMoves[listR][0]=count;
						validMoves[listR][1]=move;
						listR++;
					}
					if(playerEnergy>=1){
						validMoves[listR][0]=count;
						validMoves[listR][1]=rest;
						listR++;
					}
					count++;
				}
			}
		}
		if(getPlayer()==O){
			for(int i=board.length-1;i>0;i--)
			{
				if(board[i]==O){
					if(board[i-1]==X&&playerEnergy>=attackCost){
						validMoves[listR][0]= count;
						validMoves[listR][1]= attack;
						listR++;
					}
					if(board[i-1]==0&&playerEnergy>=moveCost){
						validMoves[listR][0]=count;
						validMoves[listR][1]=move;
						listR++;
					}
					if(playerEnergy>=1){
						validMoves[listR][0]=count;
						validMoves[listR][1]=rest;
						listR++;
					}
					count++;
				}
						
			}
		}
		int[][] out=new int[listR][2];
		for(int i=0;i<out.length;i++)out[i]=validMoves[i];
		
		for(int[] row: out)
			System.out.println(Arrays.toString(row));
		return out;
	}
	
	public DumbGamev2 play(int type, int piece)
	{
		DumbGamev2 game=clone();
		game.private_play(type,piece);
		return game;
	}
	
	private void private_play(int useDieOne,int piece)
	{
		if(useDieOne==1) System.out.println(slide(getPlayer(),piece));
		else if (useDieOne==2) attack(getPlayer(),piece);
		else rest(getPlayer(),piece);

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
			if(board[target]>0)
			{
				//~ if(getDirection(token)>0)board[0]=board[target];
				//~ else board[board.length-1]=board[target];
				return false;
			}
			board[currentLocation]=0;
			board[target]=token;
			playerEnergy--;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean attack(int token,int piece)
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
		if(token == X) {
			attackTarget = getLocation(token,piece) + 1;
		}else if(token == O){
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
	public boolean rest(int token, int piece)
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
		if(playerRestUses==0)return false;
		playerEnergy=playerEnergy-restCost;
		playerRestUses--;
		return true;
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
			System.out.printf("+------------------------------------+\n");
			System.out.printf("| "+game.getPlayerCharacter()+"'s turn                           |\n");
			System.out.printf("| A move costs %d energy              |\n",game.moveCost);
			System.out.printf("| An attack costs %d energy           |\n",game.attackCost);
			System.out.printf("| Resting costs %d energy             |\n",game.restCost);
			System.out.printf("| (YOU CAN ONLY REST ONCE PER TURN)  |\n");
			System.out.printf("+------------------------------------+\n");
			while (game.playerEnergy >0) {
				System.out.println(game.possibleMoves());
				System.out.println(game);
				System.out.printf("Your energy is: %d\n", game.playerEnergy);
				System.out.printf("Which piece would you like to move?\n");
				if(game.getPlayer() == game.X) System.out.printf("Enter a number piece from 0-%d\n", game.xCount-1);
				else System.out.printf("Enter a number piece from 0-%d\n", game.oCount-1);	
				int pieceChoice=pc.nextInt();
				System.out.printf("Would you like to move, attack, or rest?\n");
				System.out.printf("Move is 1\nAttack is 2\nRest is 3\n");
				int moveChoice=sc.nextInt();
				game=game.play(moveChoice,pieceChoice);
			}
			game.playerEnergy = game.energy;
			game.playerRestUses = game.restUses;
			game.turn++;		
		}
		if(game.getWinner()== game.X) System.out.println("X Wins!!");
		else System.out.println("O Wins!!");
	}
 
}


