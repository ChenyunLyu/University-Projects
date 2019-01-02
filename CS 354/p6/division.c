#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <string.h>

void handler_SIGINT( int signum );
void handler_SIGFPE( int signum );

int counter = 0;

int main()
{
	int n1 = 0;
	int n2 = 0;

	struct sigaction sa_int;
	memset( &sa_int , 0 , sizeof( sa_int ) );
	sa_int.sa_handler = handler_SIGINT;

	struct sigaction sa_fpe;
	memset( &sa_fpe , 0 , sizeof( sa_fpe ) );
	sa_fpe.sa_handler = handler_SIGFPE;

	char input1[100];
	char input2[100];

	if( sigaction( SIGINT , &sa_int , 0 ) == -1 )
	{
		printf( "Error handling SIGINT\n" );
	}
	if( sigaction( SIGFPE , &sa_fpe , 0 ) == -1 )
	{
		printf( "Error handling SIGFPE\n" );
	}

	while(1)
	{

		printf( "Enter first integer: " );
		fgets( input1 , 100 , stdin );
		n1 = atoi( input1 );

		printf( "Enter second integer: " );
		fgets( input2 , 100 , stdin );
		n2 = atoi( input2 );

		int output = n1 / n2;
		int rem = n1 % n2;

		printf( "%d / %d is %d with a remainder of %d\n", n1 , n2 , output , rem );
	counter++;
	}
	return 0;
}

void handler_SIGINT( int signum )
{
	printf( "\n" );
	printf( "Total number of operations successfully completed: %d\n" , counter );
	printf( "Program will be terminated.\n" );
	exit(0);
}

void handler_SIGFPE( int signum )
{
	printf( "Error: a division by 0 operation was attempted.\n" );
	printf( "Total number of operations successfully completed: %d\n", counter );
	printf( "The program will be terminated.\n" );
	exit(0);
}
