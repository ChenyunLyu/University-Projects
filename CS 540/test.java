public class test
{
	public static void main( String[] args )
	{
		int[] array = { 1 , 2 , 3 , 4 };
		swap( 1 , 3 , array );
		for( int i = 0 ; i < 4 ; i++ )
		{
			System.out.println( array[i] );
		}
	}
	
	public static void swap( int a , int b , int[] array )
	{
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
}
