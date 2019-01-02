#include <stdio.h>
#include <signal.h>
#include <time.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

void handler_SIGALRM( int signum );
void handler_SIGUSR1( int signum );
void handler_SIGINT( int signum );

int TIMER = 3;
int user_count = 0;

int main()
{
	printf( "Pid and time will be printed every 3 seconds.\n" );
	printf( "Enter ^C to end the program.\n" );

	struct sigaction sa;
	struct sigaction sa_usr;;
	struct sigaction sa_int;
	memset( &sa, 0, sizeof( sa ) );
	memset( &sa_usr, 0, sizeof( sa_usr ) );
	memset( &sa_int, 0, sizeof( sa_int ) );
	sa.sa_handler = handler_SIGALRM;
	sa_usr.sa_handler = handler_SIGUSR1;
	sa_int.sa_handler = handler_SIGINT;
	
	alarm( TIMER );
	if( sigaction( SIGALRM, &sa, 0 ) == -1 )
	{
		printf( "Error handling SIGALRM\n" );
	}
	if( sigaction( SIGUSR1, &sa_usr, 0 ) == -1 )
	{
		printf( "Error handling SIGUSR1\n" );
	} 
	if( sigaction( SIGINT, &sa_int, 0 ) == -1 )
	{
		printf( "Error handling SIGINT\n" );
	}

	while(1)
	{
	}
	
	return 0;
}

void handler_SIGALRM( int signum )
{
	time_t now;
	int id = getpid();
	time( &now );
	printf( "PID: %d | Current Time: %s", id, ctime( &now ) );
	alarm( TIMER );
}

void handler_SIGUSR1( int signum )
{
	printf( "SIGUSR1 caught!\n" );
	user_count++;
}

void handler_SIGINT( int signum )
{
	printf( "SIGINT recieved.\n" );
	printf( "SIGUSR1 was received %d times. Exit now.\n", user_count );
	exit(0);
}
