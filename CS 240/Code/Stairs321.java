public class Stairs321
{
	public static void main( String args[] )
	{
		String s = input( "please enter number of stairs:" );
		int i = Integer.parseInt( s );
		System.out.println( climb( i ) ); 
		
	}
	
	public static int climb( int n )
	{
		if( n < 0 )
			return 0;
		else if( n == 0 )
			return 1;
		else
			return climb( n - 1 ) + climb ( n - 2 ) + climb( n - 3 );
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
