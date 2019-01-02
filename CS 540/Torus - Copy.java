import java.util.*;

class State implements Comparable<State> {
	int[] board;
	State parentPt;
	int depth;

	public State(int[] arr) {
		this.board = Arrays.copyOf(arr, arr.length);
		this.parentPt = null;
		this.depth = 0;
	}

	public State[] getSuccessors() {
		
		// TO DO: get all four successors and return them in sorted order
		State[] successors = new State[4];
		for( int i = 0 ; i < 4 ; i++ )
		{
			successors[i] = new State(this.board);
			successors[i].depth = this.depth + 1;
			successors[i].parentPt = this;
		}

		for( int i = 0 ; i < 9 ; i++ )
		{
			if( this.board[i] == 0 )
			{
				State temp = successors[0];
				//Checking for right movement
				if( ( i + 1 ) % 3 != 0 )
				{
					temp.board[i] = temp.board[i+1];
					temp.board[i+1] = 0;
				}
				else
				{
					temp.board[i] = temp.board[i-2];
					temp.board[i-2] = 0;
				}
				successors[0] = temp;
				temp = successors[1];
				//Checking for left movement
				if( i % 3 != 0 )
				{
					temp.board[i] = temp.board[i-1];
					temp.board[i-1] = 0;
				}
				else
				{
					temp.board[i] = temp.board[i+2];
					temp.board[i+2] = 0;
				}
				successors[1] = temp;
				temp = successors[2];
				//Checking for up movement
				if( i - 3 >= 0 )
				{
					temp.board[i] = temp.board[i-3];
					temp.board[i-3] = 0;
				}
				else
				{
					temp.board[i] = temp.board[i+6];
					temp.board[i+6] = 0;
				}
				successors[2] = temp;
				temp = successors[3];
				//Checking for down movement
				if( i + 3 <= 8 )
				{
					temp.board[i] = temp.board[i+3];
					temp.board[i+3] = 0;
				}
				else
				{
					temp.board[i] = temp.board[i-6];
					temp.board[i-6] = 0;
				}
				successors[3] = temp;
				break;
			}
		}
		//Store the numbers as an int
/*
		int[] sort = new int [4];
		for( int i = 0 ; i < 4 ; i++ )

			int mult = 1;
			for( int j = 0 ; j < 9 ; j++ )
			{
				num = num + successors[i].board[8-j] * mult;
				mult = mult * 10;
			}
			sort[i] = Integer.parseInt( successors[i].getBoardEX() );
		}

		//Sort the numbers
		for( int i = 0 ; i < 3 ; i++ )
		{
			for( int j = 3 ; j > i ; j-- )
			{
				if( sort[j] < sort[j-1] )
				{
					int target1 = j;
					int target2 = j-1;
					

					successors[target1].board = successors[target2].board;
					successors[target2].board = temp.board;		

					int store = sort[j];
					
					sort[j] = sort[j-1];
					sort[j-1] = store;			
				}
			}
		}
*/
		

		
		return successors;
	}

	@Override
	public int compareTo( State ss )
	{
		int sort = this.getBoard().compareToIgnoreCase( ss.getBoard() );
		return sort;
	}
		
/*
	public static Comparator<State> stateComparator = new Comparator<State>()
	{
		public int compare( State s1 , State s2 )
		{
			String c1 = s1.getBoard();
			String c2 = s2.getBoard();

			return c1.compareTo(c2);
		}
	};

*/

	public void printState(int option) {
		
		// TO DO: print a torus State based on option (flag)
		if(option == 1 || option == 2)
		{
			System.out.println( this.getBoard() );
		}
		if( option == 3 )
		{
			System.out.print( this.getBoard() + " parent " );
			
			if( this.parentPt == null )
			{
				System.out.println( "0 0 0 0 0 0 0 0 0" );
			}
			else
			{
				System.out.println( this.parentPt.getBoard() );
			}
		}

		
	}

	public String getBoard() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			builder.append(this.board[i]).append(" ");
		}
		return builder.toString().trim();
	}
	
	public String getBoardEX() {
	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < 9; i++) {
			builder.append(this.board[i]);		}
		return builder.toString().trim();
	}

	public boolean isGoalState() {
		for (int i = 0; i < 9; i++) {
			if (this.board[i] != (i + 1) % 9)
				return false;
		}
		return true;
	}

	public boolean equals(State src) {
		for (int i = 0; i < 9; i++) {
			if (this.board[i] != src.board[i])
				return false;
		}
		return true;
	}
	
	public void cpState( State src )
	{
		this.board = src.board;
		this.parentPt = src.parentPt;
		this.depth = src.depth;
	}
}

public class Torus {

	public static void main(String args[]) {
		if (args.length < 10) {
			System.out.println("Invalid Input");
			return;
		}
		int flag = Integer.valueOf(args[0]);
		//System.out.println( "Checkpoint 1" );
		int[] board = new int[9];
		//System.out.println( "Checkpoint 1" );
		for (int i = 0; i < 9; i++) {
			board[i] = Integer.valueOf(args[i + 1]);
		}
		int option = flag / 100;
		int cutoff = flag % 100;
		if (option == 1) {
			State init = new State(board);
			State[] successors = init.getSuccessors();
			Arrays.sort( successors);
			for (State successor : successors) {
				successor.printState(option);
			}
		} else {
			State init = new State(board);
			Stack<State> stack = new Stack<>();
			List<State> prefix = new ArrayList<>();
			int goalChecked = 0;
			int maxStackSize = Integer.MIN_VALUE;
			int prefixCount = 0;

			// needed for Part E
			int whilecount = 0;
			int maxDepth = 0;
			int loopcount = 0;
			boolean goalFound = false;
			
			int repeatCount = 0;
			
			State goals = null;
			while (true) 
			{				
				stack.push(init);
				while (!stack.isEmpty())
				{	
				//TO DO: perform iterative deepening; implement prefix list
					State temp = stack.pop();
					temp.printState(option);
					if(option == 4 && maxDepth <= cutoff )
					{
						temp.printState(1);
						maxDepth++;
					}
					goalChecked++;
					//System.out.println( "goalcheck = " + goalChecked + " " + ((int)( goalChecked * 10000 / 42689480.0 ))/10000.0+ " %");
					//System.out.println( prefixCount );
					if( temp.isGoalState() == true )
					{
						goals = temp;
						goalFound = true;
						break;
					}
					else
					{
						State[] succ = temp.getSuccessors();
						Arrays.sort( succ );
						for( int i = 0 ; i < 4 ; i++ )
						{
							boolean repeatCheck = false;
							for( int z = 0 ; z < prefixCount ; z++ )
							{
								if( prefix.get(z).equals( succ[i] ) )
								{
									repeatCheck = true;
									repeatCount++;
									break;
	
								}
							}
							
							//for( int j = 0 ; j < prefixCount ; j++ )
							//{
							//	if( prefix.get( j ).equals( succ[i] ) )
							//	{
							//		repeatCheck = true;
									
								//}
							//}
							if( repeatCheck == false && succ[i].depth <= cutoff )
							{	
								stack.push( succ[i] );
								//System.out.println( "looping " + loopcount++ );
								//System.out.println( "Max = " + maxStackSize );
								//System.out.println( "cut = " + cutoff );
								if( stack.size() > maxStackSize )
								{
									//System.out.println( stack.size() );
									maxStackSize = stack.size();
								}
							}
						}
							
					}
					int tempIndex = prefix.indexOf( temp.parentPt );
					if( tempIndex != ( prefixCount - 1 ) && temp.parentPt != null )
					{
						for( int k = tempIndex + 1 ; k <= prefixCount ; k++ )
						{
							prefix.remove( k );
							prefixCount = tempIndex + 1;
						}
					}
					
					//System.out.println( tempIndex + " " + prefixCount );
					prefix.add( prefixCount , temp );
					prefixCount++;
				}
				whilecount++;
				//System.out.println( "Repeated: " + repeatCount );
				repeatCount = 0;
				//for( int z = 0 ; z < prefixCount ; z++ )
				//{
				//	System.out.println( " z = " + z + " " + prefix.get( z ).getBoard() );
					
				//}
				//System.out.println( whilecount++ + " " + prefixCount );
					
					
				
				if (option != 5)
				{
					break;
				}
				else if( option == 5 && goalFound == false )
				{
					System.out.println( "cutoff " + cutoff + " : " + goalChecked );
					cutoff++;
					
					prefix.clear();
					prefixCount = 0;
					//for( int i = 0 ; i < prefixCount ; i++ )
					//{
					//	prefix.remove( i );
					//	prefixCount = 0;
					//}
				}
				else if( option == 5 && goalFound == true )
				{
					for( int i = 0 ; i < prefixCount ; i++ )
					{
						System.out.println( prefix.get(i).getBoard() );
					}
					System.out.println( goals.getBoard() );
					
					System.out.println( "Goal-check " + goalChecked );
					System.out.println( "Max-stack-size " + maxStackSize );
					break;
				}
					
				
				
				//TO DO: perform the necessary steps to start a new iteration
			        //       for Part E

			}
		}
	}
}
