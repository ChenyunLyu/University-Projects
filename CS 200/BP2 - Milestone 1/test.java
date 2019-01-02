import java.util.ArrayList;

public class test
{
	public static void main( String args[] )
	{
		String userInput = "  'Hi,  I  am ! a    big-fun robot !1  bye !";
		userInput = "I said goodbye";
		ArrayList<String> l = separatePhrases( userInput );
		for (String str : l) { 		      
           System.out.println(str); 		
		}
		System.out.println( args.length );
		for (String str : args) { 		      
           System.out.println(str); 		
		}
		//System.out.println( userInput.charAt(userInput.length()-1) );
	}
	public static ArrayList<String> separatePhrases(String userInput) {
		if (userInput == null)
			return null;
		else if( userInput.length() == 0 )
			return new ArrayList<String>();
		else
		{
			ArrayList<String> result = new ArrayList<String>();
			userInput = userInput.toLowerCase();
			
			//replace invalid characters
			for( int i = 0 ; i < userInput.length() ; i++ )
				if( !( (userInput.charAt(i) >= 'a' && userInput.charAt(i) <= 'z') || (userInput.charAt(i) >= '0' || userInput.charAt(i) <= '9') ) )
					if( !( userInput.charAt(i) == '?' || userInput.charAt(i) == '!' || userInput.charAt(i) == ',' || userInput.charAt(i) == '.'  || userInput.charAt(i) == '\'' ) )
						userInput = userInput.replace( userInput.charAt(i) , ' ' );
						
			//remove extra whitespace
			userInput = userInput.trim().replaceAll( " +" , " " );
			//System.out.println( userInput );
			String temp = "";
			for( int i = 0 ; i < userInput.length() ; i++ )
			{
				if( userInput.charAt(i) == '?' || userInput.charAt(i) == '!' || userInput.charAt(i) == ',' || userInput.charAt(i) == '.' )
				{
					temp = temp.trim().replaceAll( " +" , " " );
					if( temp.length() != 0 )
						result.add(temp);
					temp = "";
				}
				else
					temp = temp + userInput.charAt(i);
			}
			if( temp.length() != 0 )
				result.add(temp);
			
			//System.out.println( userInput );
			return result;
		}
    }
		
}
