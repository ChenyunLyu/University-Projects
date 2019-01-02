import java.util.*;

public class successor {
    public static class JugState {
        int[] Capacity = new int[]{0,0,0};
        int[] Content = new int[]{0,0,0};
        public JugState(JugState copyFrom)
        {
            this.Capacity[0] = copyFrom.Capacity[0];
            this.Capacity[1] = copyFrom.Capacity[1];
            this.Capacity[2] = copyFrom.Capacity[2];
            this.Content[0] = copyFrom.Content[0];
            this.Content[1] = copyFrom.Content[1];
            this.Content[2] = copyFrom.Content[2];
        }
        public JugState()
        {
        }
        public JugState(int A,int B, int C)
        {
            this.Capacity[0] = A;
            this.Capacity[1] = B;
            this.Capacity[2] = C;
        }
        public JugState(int A,int B, int C, int a, int b, int c)
        {
            this.Capacity[0] = A;
            this.Capacity[1] = B;
            this.Capacity[2] = C;
            this.Content[0] = a;
            this.Content[1] = b;
            this.Content[2] = c;
        }
 
        public void printContent()
        {
            System.out.println(this.Content[0] + " " + this.Content[1] + " " + this.Content[2]);
        }
 
        public ArrayList<JugState> getNextStates(){
            ArrayList<JugState> successors = new ArrayList<>();
            
             // TODO add all successors to the list           
            int[] cap = this.Capacity;
            int[] con = this.Content;
            
            //Function for emptying if the jug is not empty
            for( int i = 0 ; i < 3 ; i++ )
            {
				if( con[i] != 0 )
				{
					JugState newJug = new JugState( this );
					newJug.Content[i] = 0; //Emptying the selecting jug
					successors.add( newJug );
				}
			}
            
			// Function for pouring if the jug is not already full
            for( int i = 0 ; i < 3 ; i++ )
            {
				if( con[i] != cap[i] )
				{
					JugState newJug = new JugState( this );
					newJug.Content[i] = newJug.Capacity[i]; //Fill the selected jug
					successors.add( newJug );	
				}
			}
			
			//Function for pouring water from one jug to another
			for( int i = 0 ; i < 3 ; i++ )
			{
				for( int j = 0 ; j < 3 ; j++ )
				{
					//Only pour when i is difference from j, the contents of i is not empty, the target jug is not full
					if( i != j && con[i] != 0 && con[j] != cap[j] )
					{
						JugState newJug = new JugState( this );
						int maxdifference = cap[j] - con[j]; //Determine what is the max amount of water can be poured
						int difference = con[i]; //Determine what is the max amount of water i can provide
						if( difference > maxdifference )
						{
							difference = maxdifference; //j cannot exceed its capacity
						}
						newJug.Content[i] = newJug.Content[i] - difference; //Remove water from i
						newJug.Content[j] = newJug.Content[j] + difference; //Add water into j
						successors.add( newJug );
					}
				}
			}
			
			

            return successors;
        }
    }

    public static void main(String[] args) {
        if( args.length != 6 )
        {
            System.out.println("Usage: java successor [A] [B] [C] [a] [b] [c]");
            return;
        }

        // parse command line arguments
        JugState a = new JugState();
        a.Capacity[0] = Integer.parseInt(args[0]);
        a.Capacity[1] = Integer.parseInt(args[1]);
        a.Capacity[2] = Integer.parseInt(args[2]);
        a.Content[0] = Integer.parseInt(args[3]);
        a.Content[1] = Integer.parseInt(args[4]);
        a.Content[2] = Integer.parseInt(args[5]);

        // Implement this function
        ArrayList<JugState> asist = a.getNextStates();

        // Print out generated successors
        for(int i=0;i< asist.size(); i++)
        {
            asist.get(i).printContent();
        }

        return;
    }
}
