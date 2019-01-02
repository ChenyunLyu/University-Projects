#include <signal.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main( int argc, char* argv[] )
{
	char c;
	int command;
	int id;
	while( (c = getopt(argc,argv,"u:i")) != -1 )
	{
		switch(c)
		{
			case 'u':
				command = 10;
				break;
			case 'i':
				command = 2;
				break;
		}
	}

	if( argc != 3 )
	{
		printf( "Usage: <signal type> <pid>\n" );
		exit(0);
	}

	id = atoi( argv[2] );

	if( command == 10 )
		kill( id , SIGUSR1 );
	if( command == 2 )
		kill( id , SIGINT );

	//printf("c = %d, id = %d\n" , command, id );


}
