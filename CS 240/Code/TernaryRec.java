import java.lang.*;

public class TernaryRec
{
	public static void main( String args[] )
	{
		String s = input( "please enter number of stairs:" );
		int i = Integer.parseInt( s );
		System.out.println( f( i ) ); 
		
	}
	
	public static int f( int n )
	{
		if( n <= 0 )
			return 0;
		else if( n == 0 )
			return 0;
		else if( n == 1 )
			return 0;
		else
			return (2 * f( n - 1 )) + (int)Math.pow(3 , n - 1);
	}
	
	static String input(String prompt)
	{	String	inputLine = "";
		System.out.print(prompt);
		try
		{	inputLine = (new java.io.BufferedReader(
							new java.io.InputStreamReader(System.in))).readLine();}
		catch (Exception e)
		{	String	err = e.toString();
			System.out.println(err);
			inputLine = "";
		}
		return	inputLine;
	}
	
	static String inputString(String prompt)
	{	return	input(prompt);	}
	static String input()
	{	return	input("");	}
}
