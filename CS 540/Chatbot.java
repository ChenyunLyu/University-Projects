import java.util.*;
import java.io.*;

public class Chatbot{
    private static String filename = "./WARC201709_wid.txt";
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                if(sc.hasNextInt()){
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else{
                    sc.next();
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }
    
    public static int cfunction( int a , int b , ArrayList<Integer> corpus )
    {
		int count = 0;
		for( int i = 0 ; i < corpus.size() - 1 ; i++ )
		{
			if( corpus.get(i) == a && corpus.get(i+1) == b )
				count++;
		}
		return count;
	}
    static public void main(String[] args){
        ArrayList<Integer> corpus = readCorpus();
		int flag = Integer.valueOf(args[0]);
        
        if(flag == 100){
			int w = Integer.valueOf(args[1]);
            int count = 0;
            //TODO count occurence of w
            for( int i = 0 ; i < corpus.size() ; i++ )
            {
				if( corpus.get(i) == w )
					count++;
			}
            
            System.out.println(count);
            System.out.println(String.format("%.7f",count/(double)corpus.size()));
        }
        else if(flag == 200){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            //TODO generate
            double input_prob = n1/(double)n2;
            System.out.println( String.format("%.7f",input_prob));
            int[] wcount = new int[4700];
            for( int i = 0 ; i < 4700 ; i++ )
            {
				wcount[i] = 0;
			}
			for( int i = 0 ; i < corpus.size() ; i++ )
			{
				wcount[corpus.get(i)]++;
			}
			//System.out.println( wcount[0] );
			//double[] prob = new double[4700];
			//for( int i = 0 ; i < 4700 ; i++ )
			//{
			//	prob[i] = wcount[i]/(double)corpus.size();
			//}
			//System.out.println( prob[0] );
			int temp = 0;
			int totalCount = 0;
			int previousTotalCount = 0;
			while( input_prob > totalCount/(double)corpus.size() )
			{
				previousTotalCount = totalCount;
				totalCount = totalCount + wcount[temp];
				temp++;
			}
			
			System.out.println( temp-1 );
			System.out.println( String.format("%.7f",previousTotalCount/(double)corpus.size()));
			//if( temp == 4700 )
			//	System.out.println( 1.0000000 );
			//else
			System.out.println( String.format("%.7f",totalCount/(double)corpus.size()) );

        }
        else if(flag == 300){
            int h = Integer.valueOf(args[1]);
            int w = Integer.valueOf(args[2]);
            int count = 0;
            ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            //TODO
			for( int i = 0 ; i < corpus.size()-1; i++ )
			{
				if( corpus.get(i) == h && corpus.get(i+1) == w )
					count++;
			}
			
			for( int i = 0 ; i < 4700 ; i++ )
			{
				for( int j = 0 ; j < corpus.size() ; j++ )
				{
					if( corpus.get(j) == h && corpus.get(j+1) == i )
					{
						//System.out.println("here");
						words_after_h.add( corpus.get(j+1) );
					}
				}
			}
            //output 
            System.out.println(count);
            System.out.println(words_after_h.size());
            System.out.println(String.format("%.7f",count/(double)words_after_h.size()));
        }
        else if(flag == 400){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h = Integer.valueOf(args[3]);
            //TODO
            double input_prob = n1/(double)n2;
            int[][] storage = new int[4700][2];
            for( int i = 0 ; i < 4700 ; i++ )
            {
				storage[i][0] = 0;
				storage[i][1] = 0;
			}
			int currentCount = 0;
			for( int j = 0 ; j < 4700 ; j++ )
			{
				storage[j][0] = currentCount;
				for( int i = 0 ; i < corpus.size()-1 ; i++ )
				{
					if( corpus.get(i) == h && corpus.get(i+1) == j )
						currentCount++;
				}
				storage[j][1] = currentCount;
			}
			
			int chuCount = 0;
			for( int i = 0 ; i < 4700 ; i++ )
			{
				for( int j = 0 ; j < corpus.size()-1 ; j++ )
				{
					if( corpus.get(j) == h && corpus.get(j+1) == i )
					{
						//System.out.println("here");
						chuCount++;
					}
				}
			}
			
			int temp = 0;
			while( input_prob > storage[temp][1]/(double)chuCount )
			{
				temp++;
			}
			
			System.out.println( temp );
			System.out.println( String.format("%.7f",storage[temp][0]/(double)chuCount) );
			System.out.println( String.format("%.7f",storage[temp][1]/(double)chuCount) );

        }
        else if(flag == 500){
            int h1 = Integer.valueOf(args[1]);
            int h2 = Integer.valueOf(args[2]);
            int w = Integer.valueOf(args[3]);
            int count = 0;
            ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
            //TODO
            
            for( int i = 0 ; i < corpus.size()-2; i++ )
			{
				if( corpus.get(i) == h1 && corpus.get(i+1) == h2 && corpus.get(i+2) == w )
					count++;
			}
			
			for( int i = 0 ; i < 4700 ; i++ )
			{
				for( int j = 0 ; j < corpus.size()-2 ; j++ )
				{
					if( corpus.get(j) == h1 && corpus.get(j+1) == h2 && corpus.get(j+2) == i )
					{
						//System.out.println("here");
						words_after_h1h2.add( corpus.get(j+2) );
					}
				}
			}

            //output 
            System.out.println(count);
            System.out.println(words_after_h1h2.size());
            if(words_after_h1h2.size() == 0)
                System.out.println("undefined");
            else
                System.out.println(String.format("%.7f",count/(double)words_after_h1h2.size()));
        }
        else if(flag == 600){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h1 = Integer.valueOf(args[3]);
            int h2 = Integer.valueOf(args[4]);
            //TODO
            double input_prob = n1/(double)n2;
            int[][] storage = new int[4700][2];
            for( int i = 0 ; i < 4700 ; i++ )
            {
				storage[i][0] = 0;
				storage[i][1] = 0;
			}
			int currentCount = 0;
			for( int j = 0 ; j < 4700 ; j++ )
			{
				storage[j][0] = currentCount;
				for( int i = 0 ; i < corpus.size()-2 ; i++ )
				{
					if( corpus.get(i) == h1 && corpus.get(i+1) == h2 && corpus.get(i+2) == j )
						currentCount++;
				}
				storage[j][1] = currentCount;
			}
			if( currentCount == 0 )
			{
				System.out.println( "undefined" );
				System.exit(0);
			}
			
			int chuCount = 0;
			for( int i = 0 ; i < 4700 ; i++ )
			{
				for( int j = 0 ; j < corpus.size()-2 ; j++ )
				{
					if( corpus.get(j) == h1 && corpus.get(j+1) == h2 && corpus.get(j+2) == i )
					{
						//System.out.println("here");
						chuCount++;
					}
				}
			}
			
			//System.out.println( chuCount );
			int temp = 0;
			while( input_prob > storage[temp][1]/(double)chuCount )
			{
				//System.out.println( input_prob + " " + storage[temp][1] );
				temp++;
			}
			
			System.out.println( temp );
			System.out.println( String.format("%.7f",storage[temp][0]/(double)chuCount) );
			System.out.println( String.format("%.7f",storage[temp][1]/(double)chuCount) );
        }
        else if(flag == 700){
            int seed = Integer.valueOf(args[1]);
            int t = Integer.valueOf(args[2]);
            int h1=0,h2=0;

            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);

            if(t == 0){
                // TODO Generate first word using r
                double r = rng.nextDouble();
                if(h1 == 9 || h1 == 10 || h1 == 12){
                    return;
				}

				int[] wcount = new int[4700];
				for( int i = 0 ; i < 4700 ; i++ )
				{
					wcount[i] = 0;
				}
				for( int i = 0 ; i < corpus.size() ; i++ )
				{
					wcount[corpus.get(i)]++;
				}
				int temp = 0;
				int totalCount = 0;
				int previousTotalCount = 0;
				while( r > totalCount/(double)corpus.size() )
				{
					previousTotalCount = totalCount;
					totalCount = totalCount + wcount[temp];
					temp++;
				}
			
				h1 = temp-1;
				System.out.println(h1);

                // TODO Generate second word using r
                r = rng.nextDouble();
                
                int[][] storage = new int[4700][2];
				for( int i = 0 ; i < 4700 ; i++ )
				{
					storage[i][0] = 0;
					storage[i][1] = 0;
				}
				int currentCount = 0;
				for( int j = 0 ; j < 4700 ; j++ )
				{
					storage[j][0] = currentCount;
					for( int i = 0 ; i < corpus.size()-1 ; i++ )
					{
						if( corpus.get(i) == h1 && corpus.get(i+1) == j )
							currentCount++;
					}
					storage[j][1] = currentCount;
				}
			
				int chuCount = 0;
				for( int i = 0 ; i < 4700 ; i++ )
				{
					for( int j = 0 ; j < corpus.size()-1 ; j++ )
					{
						if( corpus.get(j) == h1 && corpus.get(j+1) == i )
						{
							//System.out.println("here");
							chuCount++;
						}
					}
				}
			
				temp = 0;
				while( r > storage[temp][1]/(double)chuCount )
				{
					temp++;
				}
			
				h2 = temp;
                System.out.println(h2);
                
                
            }
            else if(t == 1){
                h1 = Integer.valueOf(args[3]);
                
                // TODO Generate second word using r
                double r = rng.nextDouble();
                int[][] storage = new int[4700][2];
				for( int i = 0 ; i < 4700 ; i++ )
				{
					storage[i][0] = 0;
					storage[i][1] = 0;
				}
				int currentCount = 0;
				for( int j = 0 ; j < 4700 ; j++ )
				{
					storage[j][0] = currentCount;
					for( int i = 0 ; i < corpus.size()-1 ; i++ )
					{
						if( corpus.get(i) == h1 && corpus.get(i+1) == j )
							currentCount++;
					}
					storage[j][1] = currentCount;
				}
			
				int chuCount = 0;
				for( int i = 0 ; i < 4700 ; i++ )
				{
					for( int j = 0 ; j < corpus.size()-1 ; j++ )
					{
						if( corpus.get(j) == h1 && corpus.get(j+1) == i )
						{
							//System.out.println("here");
							chuCount++;
						}
					}
				}
			
				int temp = 0;
				while( r > storage[temp][1]/(double)chuCount )
				{
					temp++;
				}
			
				h2 = temp;
                
                System.out.println(h2);
            }
            else if(t == 2){
                h1 = Integer.valueOf(args[3]);
                h2 = Integer.valueOf(args[4]);
            }

            while(h2 != 9 && h2 != 10 && h2 != 12){
                double r = rng.nextDouble();
                int w  = 0;
                // TODO Generate new word using h1,h2
                int[][] storage = new int[4700][2];
				for( int i = 0 ; i < 4700 ; i++ )
				{
					storage[i][0] = 0;
					storage[i][1] = 0;
				}
				int currentCount = 0;
				for( int j = 0 ; j < 4700 ; j++ )
				{
					storage[j][0] = currentCount;
					for( int i = 0 ; i < corpus.size()-2 ; i++ )
					{
						if( corpus.get(i) == h1 && corpus.get(i+1) == h2 && corpus.get(i+2) == j )
							currentCount++;
					}
					storage[j][1] = currentCount;
				}
				int temp = 0;
				if( currentCount == 0 )
				{
					//Bigram
					storage = new int[4700][2];
					for( int i = 0 ; i < 4700 ; i++ )
					{
						storage[i][0] = 0;
						storage[i][1] = 0;
					}
					currentCount = 0;
					for( int j = 0 ; j < 4700 ; j++ )
					{
						storage[j][0] = currentCount;
						for( int i = 0 ; i < corpus.size()-1 ; i++ )
						{
							if( corpus.get(i) == h1 && corpus.get(i+1) == j )
								currentCount++;
						}
						storage[j][1] = currentCount;
					}
			
					int chuCount = 0;
					for( int i = 0 ; i < 4700 ; i++ )
					{
						for( int j = 0 ; j < corpus.size()-1 ; j++ )
						{
							if( corpus.get(j) == h1 && corpus.get(j+1) == i )
							{
								//System.out.println("here");
								chuCount++;
							}
						}
					}
			
					temp = 0;
					while( r > storage[temp][1]/(double)chuCount )
					{
						temp++;
					}
			
					h2 = temp;
				}
				else
				{
					int chuCount = 0;
					for( int i = 0 ; i < 4700 ; i++ )
					{
						for( int j = 0 ; j < corpus.size()-2 ; j++ )
						{
							if( corpus.get(j) == h1 && corpus.get(j+1) == h2 && corpus.get(j+2) == i )
							{
								//System.out.println("here");
								chuCount++;
							}
						}
					}
			
					//System.out.println( chuCount );
					while( r > storage[temp][1]/(double)chuCount )
					{
						//System.out.println( input_prob + " " + storage[temp][1] );
						temp++;
					}
				}
			
				w = temp;
				System.out.println(w);
                h1 = h2;
                h2 = w;
            }
        }

        return;
    }
}
