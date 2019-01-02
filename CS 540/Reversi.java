import java.util.*;
import java.lang.Exception;


class State {
    char[] board;
    char[][] boardm;
    

    public State(char[] arr) {
        this.board = Arrays.copyOf(arr, arr.length);
        this.boardm = new char[4][4];
        boardm[0][0] = board[0];
        boardm[0][1] = board[1];
        boardm[0][2] = board[2];
        boardm[0][3] = board[3];
        
        boardm[1][0] = board[4];
        boardm[1][1] = board[5];
        boardm[1][2] = board[6];
        boardm[1][3] = board[7];
        
        boardm[2][0] = board[8];
        boardm[2][1] = board[9];
        boardm[2][2] = board[10];
        boardm[2][3] = board[11];
        
        boardm[3][0] = board[12];
        boardm[3][1] = board[13];
        boardm[3][2] = board[14];
        boardm[3][3] = board[15];
    }
    public State(char[][] arr) {
        this.boardm = new char[4][4];
        for( int i = 0 ; i < 4 ; i++ )
        {   for( int j = 0 ; j < 4 ; j++ )
			{
				boardm[i][j] = arr[i][j];
			}
		}
	}

    public int getScore() {

        // TO DO: return game theoretic value of the board
        int score = 0;
        int p1s = 0;
        int p2s = 0;
        for( int i = 0 ; i < 4 ; i++ )
		{   for( int j = 0 ; j < 4 ; j++ )
			{
				if( this.boardm[i][j] == '1' ) {p1s++;}
				else if( this.boardm[i][j] == '2' ) {p2s++;}
			}
		}
		
		if( p1s > p2s )
		{
			score = 1;
		}
		else if( p1s < p2s )
		{
			score = -1;
		}
		else if( p1s == p2s )
		{
			score = 0;
		}
		
        return score;
        //return 1;
    }
    
    public boolean isTerminal() {
    	
        // TO DO: determine if the board is a terminal node or not and return boolean
        boolean is_terminal = false;
        if( this.getSuccessorsCount( '1' ) == 0 && this.getSuccessorsCount( '2' ) == 0 )
        {
			is_terminal = true;
		}

        return is_terminal;
    }
    
    public boolean check1( int a , int b , char p1 , char p2 )
    {
		if( boardm[a+1][b-1] == p2 )
		{
			if( boardm[a+2][b-2] == '0' )
			{
				this.boardm[a+1][b-1] = p1;
				this.boardm[a+2][b-2] = p1;
				
				return true;
			}
			else if( a+3 < 4 && this.boardm[a+2][b-2] == p2 && this.boardm[a+3][b-3] == '0' )
			{
				this.boardm[a+1][b-1] = p1;
				this.boardm[a+2][b-2] = p1;
				this.boardm[a+3][b-3] = p1;
				return true;
			}
		}
		return false;
	}
    
    public boolean checkdir( int a , int b , char p1 , char p2, int dir )
    {
		int[] adirA = {999, 1 , 1 , 1 , 0 ,999, 0 , -1, -1, -1 };
		int[] bdirA = {999,-1 , 0 , 1 ,-1 ,999, 1 , -1, 0 ,  1 };
		int adir = adirA[dir];
		int bdir = bdirA[dir];
		if( boardm[a+adir][b+bdir] == p2 )
		{
			//System.out.println( "a="+a+" b="+b);
			//System.out.println( "a+2dir="+a+2*adir+" b="+b+2*bdir);
			//System.out.println( p1 + " "+boardm[a+(2*adir)][b+(2*bdir)]);
			if( boardm[a+(2*adir)][b+(2*bdir)] == p1 )
			{
				//System.out.println( "a+dir="+a+adir+" b+dir="+b+bdir);
				this.boardm[a][b] = p1;
				this.boardm[a+adir][b+bdir] = p1;
				this.boardm[a+(2*adir)][b+(2*bdir)] = p1;
				//System.out.println("dir = " + dir);
				return true;
			}
			//System.out.println( "adir="+adir+" bdir="+bdir );
			else if( a+(3*adir) >= 0 && b+(3*bdir) >= 0 && a+(3*adir) < 4 && b+(3*bdir) < 4 && this.boardm[a+(2*adir)][b+(2*bdir)] == p2 && this.boardm[a+(3*adir)][b+(3*bdir)] == p1 )
			{
				this.boardm[a][b] = p1;
				this.boardm[a+adir][b+bdir] = p1;
				this.boardm[a+(2*adir)][b+(2*bdir)] = p1;
				//System.out.println("dir = " + dir);
				return true;
			}
		}
		return false;
	}

    public State[] getSuccessors(char player) {

        // TO DO: get all successors and return them in proper order
        State[] successors = new State[16];
        char[][] boards = this.boardm;
        //State original = new State(this.boardm);

        int sucC = 0;
        char inverseP;
        if( player == '1' )
        {
			inverseP = '2';
		}
		else if( player == '2' )
		{
			inverseP = '1';
		}
        else
        {
			System.out.println( "getSuccessors: wrong player input" );
			return successors;
		}
        

		for( int i = 0 ; i < 4 ; i++ )
		{   for( int j = 0 ; j < 4 ; j++ )
			{
				State temp = new State( boards );
				//System.out.print( "boards = " );	
				/*
				for( int x = 0 ; x < 4 ; x++ )
				{   for( int y = 0 ; y < 4 ; y++ )
					{
						System.out.print( this.boardm[x][y] );					
					}

				}
				* */
				//System.out.println(  );	
				//temp.printState( 1 , '1' );
				
				if( temp.boardm[i][j] == '0' )
				{
					if( i < 2 && j < 2 )
					{
						boolean checkn1 = temp.checkdir(i,j,player,inverseP,6);
						boolean checkn2 = temp.checkdir(i,j,player,inverseP,3);
						boolean checkn3 = temp.checkdir(i,j,player,inverseP,2);
						if( checkn1||checkn2||checkn3 )
						{
							//System.out.println("C1");
							//System.out.println("i="+i+" j="+j);
							
							successors[sucC] = temp;
							sucC++;
						}
					}
					else if( i < 2 && j >= 2 )
					{
						boolean checkn1 = temp.checkdir(i,j,player,inverseP,4);
						boolean checkn2 = temp.checkdir(i,j,player,inverseP,1);
						boolean checkn3 = temp.checkdir(i,j,player,inverseP,2);
						if( checkn1||checkn2||checkn3 )
						{
							//System.out.println("C2");
							successors[sucC] = temp;
							sucC++;
						}
					}
					else if( i >= 2 && j < 2 )
					{
						boolean checkn1 = temp.checkdir(i,j,player,inverseP,8);
						boolean checkn2 = temp.checkdir(i,j,player,inverseP,6);
						boolean checkn3 = temp.checkdir(i,j,player,inverseP,9);
						if( checkn1||checkn2||checkn3 )
						{
							//System.out.println("C3");
							successors[sucC] = temp;
							sucC++;
						}
					}
					else if( i >= 2 && j >= 2 )
					{
						boolean checkn1 = temp.checkdir(i,j,player,inverseP,4);
						boolean checkn2 = temp.checkdir(i,j,player,inverseP,7);
						boolean checkn3 = temp.checkdir(i,j,player,inverseP,8);
						if( checkn1||checkn2||checkn3 )
						{
							//System.out.println("C4");
							successors[sucC] = temp;
							sucC++;
						}
					}
				}
			}
		}

		//System.out.println( "sucC = " + sucC );
		if( sucC == 0 && !this.isTerminal() )
		{
			successors[0] = new State( boardm );
		}
        return successors;
    }
    
    public int getSuccessorsCount(char player) {

        // TO DO: get all successors and return them in proper order
        State[] successors = new State[16];
        char[][] boards = this.boardm;
        //State original = new State(this.boardm);

        int sucC = 0;
        char inverseP;
        if( player == '1' )
        {
			inverseP = '2';
		}
		else if( player == '2' )
		{
			inverseP = '1';
		}
        else
        {
			System.out.println( "getSuccessorsCount: wrong player input" );
			return -1;
		}
        

		for( int i = 0 ; i < 4 ; i++ )
		{   for( int j = 0 ; j < 4 ; j++ )
			{
				State temp = new State( boards );
				//System.out.print( "boards = " );	
				/*
				for( int x = 0 ; x < 4 ; x++ )
				{   for( int y = 0 ; y < 4 ; y++ )
					{
						System.out.print( this.boardm[x][y] );					
					}

				}
				* */
				//System.out.println(  );	
				//temp.printState( 1 , '1' );
				
				if( temp.boardm[i][j] == '0' )
				{
					if( i < 2 && j < 2 )
					{
						if( temp.checkdir(i,j,player,inverseP,6)||temp.checkdir(i,j,player,inverseP,3)||temp.checkdir(i,j,player,inverseP,2) )
						{
							//System.out.println("C1");
							//System.out.println("i="+i+" j="+j);
							
							successors[sucC] = temp;
							sucC++;
						}
					}
					else if( i < 2 && j >= 2 )
					{
						if( temp.checkdir(i,j,player,inverseP,4)||temp.checkdir(i,j,player,inverseP,1)||temp.checkdir(i,j,player,inverseP,2) )
						{
							//System.out.println("C2");
							successors[sucC] = temp;
							sucC++;
						}
					}
					else if( i >= 2 && j < 2 )
					{
						if( temp.checkdir(i,j,player,inverseP,8)||temp.checkdir(i,j,player,inverseP,6)||temp.checkdir(i,j,player,inverseP,9) )
						{
							//System.out.println("C3");
							successors[sucC] = temp;
							sucC++;
						}
					}
					else if( i >= 2 && j >= 2 )
					{
						if( temp.checkdir(i,j,player,inverseP,4)||temp.checkdir(i,j,player,inverseP,7)||temp.checkdir(i,j,player,inverseP,8) )
						{
							//System.out.println("C4");
							successors[sucC] = temp;
							sucC++;
						}
					}
				}
			}
		}

		//System.out.println( "sucC = " + sucC );
        return sucC;
    }
 
    public void printState(int option, char player) {

        // TO DO: print a State based on option (flag)
        if( option == 1 )
        {
			for( int i = 0 ; i < 4 ; i++ )
			{   for( int j = 0 ; j < 4 ; j++ )
				{
					System.out.print( this.boardm[i][j] );					
				}

			}
			System.out.println();
		}
		if( option == 2 )
		{
			if( this.isTerminal() )
			{
				System.out.println( this.getScore() );
			}
			else
			{
				System.out.println( "non-terminal" );
			}
		}

    }

    public String getBoard() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            builder.append(this.board[i]);
        }
        return builder.toString().trim();
    }

    public boolean equals(State src) {
        for (int i = 0; i < 16; i++) {
            if (this.board[i] != src.board[i])
                return false;
        }
        return true;
    }
    public int totalSucC( char p1  )
    {
		char p2 = '0';
		int c = 0;
		if( p1 == '1' )
			p2 = '2';
		else
			p2 = '1';
		State[] s = this.getSuccessors( p1 );
		for( int i = 0 ; s[i] != null ; i++ )
		{
			c = c+ s[i].totalSucC( p2 );
			c++;
		}
		return c;
	}
}

class Minimax {
	static int callCount = 0;
	
	public static int max_value(State curr_state) {
		
        // TO DO: implement Max-Value of the Minimax algorithm
        //System.out.println( "here max!" );
        callCount++;
	
		
        if( curr_state.isTerminal() )
        {
			//System.out.println( "max ok!" );
			return curr_state.getScore();
		}
		else
		{
			//System.out.println( "here max!" );
			
			int r = -1;
			State[] temp = curr_state.getSuccessors( '1' );
			for( int i = 0 ; temp[i] != null ; i++ )
			{
				//System.out.println( "max" );
				//quickPrint( temp[i] );
				//System.out.println( "v="+temp[i].getScore() );
				r = Math.max( r, min_value( temp[i] ) );
				//if( temp[i].getSuccessorsCount( '1' ) == 0 )
				//{
				//	callCount--;
				//}
			}
			//System.out.println( "max ok!" );
			return r;
		}
        //return 1;

	}
	
	public static int min_value(State curr_state ) {
		
        // TO DO: implement Min-Value of the Minimax algorithm
		//System.out.println( "here min!" );
        
        //try{ curr_state.isTerminal() ;
		//} catch (Exception e){
			//System.out.println( "isTerminal Error" );
			//e.printStackTrace();
		//}
		callCount++;
        if( curr_state.isTerminal() )
        {
			//System.out.println( "min ok!" );
			return curr_state.getScore();
		}
		else
		{
			
			int r = 1;

			State[] temp = curr_state.getSuccessors( '2' );
			for( int i = 0 ; temp[i] != null ; i++ )
			{
				//System.out.println( "min" );
				//quickPrint( temp[i] );
				//System.out.println( "v="+temp[i].getScore() );
				r = Math.min( r, max_value( temp[i] ) );
				//if( temp[i].getSuccessorsCount( '2' ) == 0 )
				//{
				//	callCount--;
				//}
			}
			//System.out.println( "min ok!" );
			return r;
		}
        //return 1;

	}
	
	public static int max_value_with_pruning(State curr_state, int alpha, int beta) {
	    
        // TO DO: implement Max-Value of the alpha-beta pruning algorithm
        callCount++;
	
		
        if( curr_state.isTerminal() )
        {
			//System.out.println( "max ok!" );
			return curr_state.getScore();
		}
		else
		{
			//System.out.println( "here max!" );
			
			
			State[] temp = curr_state.getSuccessors( '1' );
			for( int i = 0 ; temp[i] != null ; i++ )
			{
				alpha = Math.max( alpha, min_value_with_pruning( temp[i],alpha,beta ) );
				if( alpha >= beta )
				{
					return beta;
				}
			}
			//System.out.println( "max ok!" );
			return alpha;
		}
        
        //return 1;

	}
	
	public static int min_value_with_pruning(State curr_state, int alpha, int beta) {
	    
        callCount++;
        if( curr_state.isTerminal() )
        {
			//System.out.println( "min ok!" );
			return curr_state.getScore();
		}
		else
		{
			
			int r = 1;

			State[] temp = curr_state.getSuccessors( '2' );
			for( int i = 0 ; temp[i] != null ; i++ )
			{
				beta = Math.min( beta, max_value_with_pruning( temp[i] , alpha ,beta ) );
				if( alpha >= beta )
				{
					return beta;
				}
			}
			//System.out.println( "min ok!" );
			return beta;
		}
        //return 1;

	}
	
	public static int run(State curr_state, char player ) {

        // TO DO: run the Minimax algorithm and return the game theoretic value
        if( player == '1' )
			return max_value( curr_state );
		else
			return min_value( curr_state );

	}
	
	public static int run_with_pruning(State curr_state, char player) {
	    
        // TO DO: run the alpha-beta pruning algorithm and return the game theoretic value
        if( player == '1' )
			return max_value_with_pruning( curr_state , -2 , 2 );
		else
			return min_value_with_pruning( curr_state , -2 , 2 );

	}
	public static int getCallCount()
	{
		return callCount;
	}
	
	private static void quickPrint( State s )
	{
		for( int i = 0 ; i < 4 ; i++ )
			for( int j = 0 ; j < 4 ; j++ )
				System.out.print(s.boardm[i][j]);
		System.out.println();	
	}
}

public class Reversi {
    public static void main(String args[]) {
        if (args.length != 3) {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }
        int flag = Integer.valueOf(args[0]);
        char[] board = new char[16];
        for (int i = 0; i < 16; i++) {
            board[i] = args[2].charAt(i);
        }
        int option = flag / 100;
        char player = args[1].charAt(0);
        if ((player != '1' && player != '2') || args[1].length() != 1) {
            System.out.println("Invalid Player Input");
            return;
        }
        char inverseP = '0';
        if( player == '1' )
        {
			inverseP = '2';
		}
		else if( player == '2' )
		{
			inverseP = '1';
		}
        State init = new State(board);
        //init.printState(option, player);
        if( option == 1 )
        {
			State[] succ = init.getSuccessors( player );
			for( int i = 0 ; succ[i] != null ; i++ )
			{
				succ[i].printState( option, player );
			}
		}
		else if( option == 2 )
		{
			init.printState( option , player );
		}
		else if( option == 3 )
		{
			Minimax mm = new Minimax();
			System.out.println( mm.run( init , player ) );
			System.out.println( mm.getCallCount() );
		}
		else if( option == 4 )
		{
			int best = 0;
			State[] store;
			Minimax mm = new Minimax();
			if( player == '1' )
			{
				best = -1;
				store = init.getSuccessors('1');
				if( init.isTerminal() )
				{}
				else
				{
					//best = mm.run( init , player );
					for( int i = 0 ; store[i] != null ; i++ )
					{
						//System.out.println( "score="+store[i].getScore() );
						//System.out.println( "best="+best );
						best = Math.max( best , mm.min_value( store[i] ) );
						//System.out.println( "b=" + best );
					}
					for( int i = 0 ; store[i] != null ; i++ )
					{
						if( mm.min_value( store[i] ) == best )
						{
							store[i].printState( 1 , player );
							break;
						}
					}
					
				}
			}
			else if( player == '2' )
			{
				best = 1;
				store = init.getSuccessors('2');
				if( init.isTerminal() )
				{}
				else
				{
					//best = mm.run( init , player );
					for( int i = 0 ; store[i] != null ; i++ )
					{
						//System.out.println( "score="+store[i].getScore() );
						//System.out.println( "best="+best );
						best = Math.min( best , mm.max_value(store[i]) );
					}
					for( int i = 0 ; store[i] != null ; i++ )
					{
						if( mm.max_value( store[i] ) == best )
						{
							store[i].printState( 1 , player );
							break;
						}
					}
					
				}
			}
		}
		else if( option == 5 )
		{
			Minimax mm = new Minimax();
			System.out.println( mm.run_with_pruning( init , player ) );
			System.out.println( mm.getCallCount() );
		}
		else if( option == 6 )
		{
			int best = 0;
			State[] store;
			Minimax mm = new Minimax();
			if( player == '1' )
			{
				best = -1;
				store = init.getSuccessors('1');
				if( init.isTerminal() )
				{}
				else
				{
					//best = mm.run( init , player );
					for( int i = 0 ; store[i] != null ; i++ )
					{
						//System.out.println( "score="+store[i].getScore() );
						//System.out.println( "best="+best );
						best = Math.max( best , mm.min_value_with_pruning( store[i],-2,2 ) );
						//System.out.println( "b=" + best );
					}
					for( int i = 0 ; store[i] != null ; i++ )
					{
						if( mm.min_value_with_pruning( store[i],-2,2 ) == best )
						{
							store[i].printState( 1 , player );
							break;
						}
					}
					
				}
			}
			else if( player == '2' )
			{
				best = 1;
				store = init.getSuccessors('2');
				if( init.isTerminal() )
				{}
				else
				{
					//best = mm.run( init , player );
					for( int i = 0 ; store[i] != null ; i++ )
					{
						//System.out.println( "score="+store[i].getScore() );
						//System.out.println( "best="+best );
						best = Math.min( best , mm.max_value_with_pruning(store[i],-2,2) );
					}
					for( int i = 0 ; store[i] != null ; i++ )
					{
						if( mm.max_value_with_pruning( store[i],-2,2 ) == best )
						{
							store[i].printState( 1 , player );
							break;
						}
					}
					
				}
			}
		}
		//int aa = -1;
		//int bb = 0;
		//aa = Math.max( aa , bb );
		//System.out.println( "aa="+aa );
		//System.out.println( "total="+init.totalSucC( player ) );
    }
}
