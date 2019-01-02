import java.util.*;
import java.util.stream.*;

public class Ice
{
	
	static int[] data = { 118, 151, 121,  96, 110, 117,                     //1855~
				   132, 104, 125, 118, 125, 123, 110, 127, 131,  99, //1860s
				   126, 144, 136, 126,  91, 130,  62, 112,  99, 161, //1870s
				    78, 124, 119, 124, 128, 131, 113,  88,  75, 111, //1880s
				    97, 112, 101, 101,  91, 110, 100, 130, 111, 107, //1890s
				   105,  89, 126, 108,  97,  94,  83, 106,  98, 101, //1900s
				   108,  99,  88, 115, 102, 116, 115,  82, 110,  81, //1910s
				    96, 125, 104, 105, 124, 103, 106,  96, 107,  98, //1920s
				    65, 115,  91,  94, 101, 121, 105,  97, 105,  96, //1930s
				    82, 116, 114,  92,  98, 101, 104,  96, 109, 122, //1940s
				   114,  81,  85,  92, 114, 111,  95, 126, 105, 108, //1950s
				   117, 112, 113, 120,  65,  98,  91, 108, 113, 110, //1960s
				   105,  97, 105, 107,  88, 115, 123, 118,  99,  93, //1970s
				    96,  54, 111,  85, 107,  89,  87,  97,  93,  88, //1980s
				    99, 108,  94,  74, 119, 102,  47,  82,  53, 115, //1990s
				    21,  89,  80, 101,  95,  66, 106,  97,  87, 109, //2000s
				    57,  87, 117,  91,  62,  65};                    //2010~2016
	static double[] data2 = new double[162];
	static int n = data.length;
	static int flag = 0;
	static double arg1 = 0;
	static double arg2 = 0;
				   
	public static void main( String[] args )
	{
		flag = Integer.valueOf(args[0]);
		if( args.length > 1 )
			arg1 = Double.valueOf(args[1]);
		if( args.length > 2 )
			arg2 = Double.valueOf(args[2]);
		
		if( flag == 100 )
		{
			for( int i = 1855 ; i <= 2016 ; i++ )
			{
				System.out.println( i + " " + data[translate(i)] );
			}
		}
		
		if( flag == 200 )
		{
			System.out.println( data.length );
			double mean = mean();
			System.out.println( String.format("%.2f",mean) );
			double stdv = stdv();
			System.out.println( String.format("%.2f",stdv) );
		}
		
		if( flag == 300 )
		{
			double mse = MSE( arg1, arg2);
			System.out.println( String.format("%.2f",mse) );
		}
		
		if( flag == 400 )
		{
			double mse1 = lrMSE1( arg1, arg2 );
			double mse2 = lrMSE2( arg1, arg2 );
			System.out.println( String.format("%.2f",mse1) );
			System.out.println( String.format("%.2f",mse2) );
		}
		
		if( flag == 500 )
		{
			int T = Integer.valueOf(args[2]);
			double b1 = 0;
			double b2 = 0;
			
			int loop = 0;
			
			while( loop < T )
			{
				loop++;
				double tempb1 = b1 - arg1 * lrMSE1( b1 , b2 );
				double tempb2 = b2 - arg1 * lrMSE2( b1 , b2 );
				b1 = tempb1;
				b2 = tempb2;
				System.out.print( loop + " " );
				System.out.print( String.format("%.2f",b1) + " " );
				System.out.print( String.format("%.2f",b2) + " " );
				double temp = MSE( b1, b2 );
				System.out.println( String.format("%.2f",temp));
			}
		}
		
		if( flag == 600 )
		{
			double xmean = 0;
			double ymean = 0;
			
			for( int i = 1855; i <= 2016 ; i++ )
			{
				xmean = xmean + i;
			}
			xmean = xmean / n;
			
			ymean = mean();
			
			double b1 = 0;
			double b0 = 0;
			
			double up = 0;
			double down = 0;
			
			for( int i = 1855 ; i <= 2016 ; i++ )
			{
				up = up + (i - xmean) * (data[translate(i)] - ymean);
			}
			for( int i = 1855 ; i <= 2016 ; i++ )
			{
				down = down + (i - xmean)*(i - xmean);
			}
			
			b1 = up / down;
			b0 = ymean - b1*xmean;
			double mse = MSE( b1 , b0 );
			System.out.println( String.format("%.2f",b0) + " " + String.format("%.2f",b1) + " " + String.format("%.2f",mse) );
		}
		
		if( flag == 700 )
		{
			int pYear = Integer.valueOf( args[1] );
			double xmean = 0;
			double ymean = 0;
			
			for( int i = 1855; i <= 2016 ; i++ )
			{
				xmean = xmean + i;
			}
			xmean = xmean / 162;
			
			ymean = mean();
			
			double b1 = 0;
			double b0 = 0;
			
			double up = 0;
			double down = 0;
			
			for( int i = 1855 ; i <= 2016 ; i++ )
			{
				up = up + (i - xmean) * (data[translate(i)] - ymean);
			}
			for( int i = 1855 ; i <= 2016 ; i++ )
			{
				down = down + (i - xmean)*(i - xmean);
			}
			
			b1 = up / down;
			b0 = ymean - b1*xmean;
			double mse = MSE( b1 , b0 );
			double estimate = b0 + b1*pYear;
			System.out.println( String.format("%.2f",estimate) );
			
		
		}
		
		if( flag == 800 )
		{
			double xmean = 0;
			double stdvx = 0;
			for( int i = 1855; i <= 2016 ; i++ )
			{
				xmean = xmean + i;
			}
			xmean = xmean / n;
			
			for( int i = 1855 ; i <= 2016 ; i++ )
			{
				stdvx = stdvx + ( i - xmean )*( i - xmean );
			}
			stdvx = stdvx/(n-1);
			stdvx = Math.sqrt(stdvx);
			
			for( int i = 0 ; i < 162 ; i++ )
			{
				data2[i] = (reverseTrans(i) - xmean)/stdvx;
			}
			
			int T = Integer.valueOf(args[2]);
			double b1 = 0;
			double b2 = 0;
			
			int loop = 0;
			
			while( loop < T )
			{
				loop++;
				double tempb1 = b1 - arg1 * lrMSE1alt( b1 , b2 , data2 );
				double tempb2 = b2 - arg1 * lrMSE2alt( b1 , b2 , data2 );
				b1 = tempb1;
				b2 = tempb2;
				System.out.print( loop + " " );
				System.out.print( String.format("%.2f",b1) + " " );
				System.out.print( String.format("%.2f",b2) + " " );
				double temp = MSEalt( b1, b2 , data2 );
				System.out.println( String.format("%.2f",temp));
			}
			
		}
		
		if( flag == 900 )
		{
			double xmean = 0;
			double stdvx = 0;
			for( int i = 1855; i <= 2016 ; i++ )
			{
				xmean = xmean + i;
			}
			xmean = xmean / n;
			
			for( int i = 1855 ; i <= 2016 ; i++ )
			{
				stdvx = stdvx + ( i - xmean )*( i - xmean );
			}
			stdvx = stdvx/(n-1);
			stdvx = Math.sqrt(stdvx);
			
			for( int i = 0 ; i < 162 ; i++ )
			{
				data2[i] = (reverseTrans(i) - xmean)/stdvx;
			}
			
			int T = Integer.valueOf(args[2]);
			double b1 = 0;
			double b2 = 0;
			
			int loop = 0;
			
			while( loop < T )
			{
				loop++;
				double tempb1 = b1 - arg1 * lrMSE1alt3( b1 , b2 , data2 );
				double tempb2 = b2 - arg1 * lrMSE2alt3( b1 , b2 , data2 );
				b1 = tempb1;
				b2 = tempb2;
				System.out.print( loop + " " );
				System.out.print( String.format("%.2f",b1) + " " );
				System.out.print( String.format("%.2f",b2) + " " );
				double temp = MSEalt( b1, b2 , data2 );
				System.out.println( String.format("%.2f",temp));
			}
			
		}
	}
	
	public static int translate( int year )
	{
		return (year-1855);
	}
	
	public static int reverseTrans( int i )
	{
		return (i+1855);
	}
	
	public static double mean()
	{
		double sum = (double)IntStream.of(data).sum();
		double mean = sum/n;
		return mean;
	}
	
	public static double stdv()
	{
		double total = 0;
		double mean = mean();
		for( int i = 0 ; i < n ; i++ )
		{
			total = total + (data[i] - mean)*(data[i] - mean);
		}
		total = total/n;
		total = Math.sqrt(total);
		return total;
	}
	
	public static double MSE( double a1, double a2 )
	{
		double total = 0;
		for( int i = 0 ; i < n ; i++ )
		{
			total = total + (a1 + a2 * reverseTrans(i) - data[i])*(a1 + a2 * reverseTrans(i) - data[i]);
		}
		total = total / n;
		return total;
	}
	
	public static double lrMSE1( double a1, double a2 )
	{
		double total = 0;
		for( int i = 0 ; i < n ; i++ )
		{
			total = total + (a1 + a2 * reverseTrans(i) - data[i]);
		}
		total = total / n * 2;
		return total;
	}
	
	public static double lrMSE2( double a1, double a2 )
	{
		double total = 0;
		for( int i = 0 ; i < n ; i++ )
		{
			total = total + (a1 + a2 * reverseTrans(i) - data[i]) * reverseTrans(i);
		}
		total = total / n * 2;
		return total;
	}
	
	public static double MSEalt( double a1, double a2 , double[] d2 )
	{
		double total = 0;
		for( int i = 0 ; i < n ; i++ )
		{
			total = total + (a1 + a2 * d2[i] - data[i])*(a1 + a2 * d2[i] - data[i]);
		}
		total = total / n;
		return total;
	}
	
	public static double lrMSE1alt( double a1, double a2 , double[] d2 )
	{
		double total = 0;
		for( int i = 0 ; i < n ; i++ )
		{
			total = total + (a1 + a2 * d2[i] - data[i]);
		}
		total = total / n * 2;
		return total;
	}
	
	public static double lrMSE2alt( double a1, double a2 , double[] d2)
	{
		double total = 0;
		for( int i = 0 ; i < n ; i++ )
		{
			total = total + (a1 + a2 * d2[i] - data[i]) * d2[i];
		}
		total = total / n * 2;
		return total;
	}
	
	public static double lrMSE1alt3( double a1, double a2 , double[] d2 )
	{
		double total = 0;
		Random rng = new Random();
		for( int i = 0 ; i < n ; i++ )
		{
			int r = rng.nextInt( 162 );
			total = total + 2*(a1 + a2 * d2[r] - data[r]);
		}
		total = total / n * 2;
		return total;
	}
	
	public static double lrMSE2alt3( double a1, double a2 , double[] d2)
	{
		double total = 0;
		Random rng = new Random();
		for( int i = 0 ; i < n ; i++ )
		{
			int r = rng.nextInt( 162 );
			total = total + 2*(a1 + a2 * d2[r] - data[r]) * d2[r];
		}
		total = total / n * 2;
		return total;
	}
	
}
