import java.util.*;

class State {
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
		}
		for( int i = 0 ; i < 4 ; i++ )
		{
			successors[i].depth++;
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
					temp.board[i] = temp.board[i+i];
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
		int[][] sort = new int [4][2];
		for( int i = 0 ; i < 4 ; i++ )
		{	
			int num = 0;
			int mult = 1;
			for( int j = 0 ; j < 9 ; j++ )
			{
				num = num + successors[i].board[8-j] * mult;
				mult = mult * 10;
			}
			sort[i][0] = num;
			sort[i][1] = i;
		}

		//Sort the numbers
		for( int i = 0 ; i < 3 ; i++ )
		{
			for( int j = 3 ; j > i ; j-- )
			{
				if( sort[j][0] < sort[j-1][0] )
				{
					int target1 = sort[j][1];
					int target2 = sort[j-1][1];
					
					State temp = new State(successors[target1].board);
					successors[target1].board = successors[target2].board;
					successors[target2].board = temp.board;		

					int store = sort[j][0];
					
					sort[j][0] = sort[j-1][0];
					sort[j-1][0] = store;			
				}
			}
		}
		return successors;
	}

	public void printState(int option) {
		
		// TO DO: print a torus State based on option (flag)
		if(option == 1)
		{
			for( int i = 0 ; i < 8 ; i++ )
			{
				System.out.print( this.board[i] + " " );
			}
			System.out.println( this.board[8] );
		}
		
	}

	public String getBoard() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			builder.append(this.board[i]).append(" ");
		}
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
			for (State successor : successors) {
				successor.printState(option);
			}
		} else {
			State init = new State(board);
			Stack<State> stack = new Stack<>();
			List<State> prefix = new ArrayList<>();
			int goalChecked = 0;
			int maxStackSize = Integer.MIN_VALUE;

			// needed for Part E
			while (true) {				
				stack.push(init);
				while (!stack.isEmpty()) {	
					//TO DO: perform iterative deepening; implement prefix list
					
				}
				
				if (option != 5)
					break;
				
				//TO DO: perform the necessary steps to start a new iteration
			        //       for Part E

			}
		}
	}
}
