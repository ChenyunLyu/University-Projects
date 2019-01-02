import java.util.Scanner;
import java.io.File;
import java.lang.Exception;
import java.io.FileNotFoundException;

public class hw5
{
    
    public static void main( String args[] )
    {
		int counter = 0;
		int uniquecounter = 0;
		String[] words = new String[100000];
		int[] wc = new int[100000];
		for( int i = 0 ; i < 100000 ; i++ )
		{
				wc[i] = -1;
		}

		Scanner sc2 = null;

		for( int i = 1 ; i <= 363 ; i++ )
		{
			//string temps1 = Integer.toString( i );
			String tempss = i + ".txt";
			while( tempss.length() < 7 )
			{
				tempss = "0" + tempss;
			}
			tempss = "WARC201709\\" + tempss;
			try {
				sc2 = new Scanner(new File(tempss));
			} catch (FileNotFoundException e) {
				e.printStackTrace();  
			}
			while (sc2.hasNextLine()) {
					Scanner s2 = new Scanner(sc2.nextLine());
				while (s2.hasNext()) {
					String s = s2.next();
					//System.out.println(s);
					counter++;
					int tempcounter = 0;
					while( true )
					{
							if( s.equals( words[tempcounter] ) )
							{
								counter ++;
								wc[tempcounter]++ ;
								break;
							}
							//else if( words[tempcounter].equals( null ) )
							//else if( Objects.equals( words[tempcounter] ,  null ) )
							else if( wc[tempcounter] == -1 )
							{
									words[tempcounter] = s;
									wc[tempcounter] = 1;
									uniquecounter++;
									counter++;
									break;
							}
							tempcounter ++;
					}
				}
			}
		}
		
		for( int i = 0 ; i < uniquecounter - 1; i++ )
		{
				for( int j = uniquecounter - 1 ; j > i ; j-- )
				{
						if( wc[j] > wc[j-1] )
						{
								int tempint = wc[j];
								wc[j] = wc[j-1];
								wc[j-1] = tempint;
								
								String tempstr = words[j];
								words[j] = words[j-1];
								words[j-1] = tempstr;
						}
				}
		}
		//for( int i = 0 ; i < 100 ; i++ )
		for( int i = 0 ; i < uniquecounter ; i++ )
		{
			//System.out.println( wc[i] + "    " + words[i] );
			System.out.println( wc[i] );
		}
		
		System.out.println( "count = " + counter );
		System.out.println( "ucount = " + uniquecounter );
	}
}
