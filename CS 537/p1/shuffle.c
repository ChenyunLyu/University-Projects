/* Name: Chenyun Lyu
 * CS 537, Project0 Shuffling
 */

#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <sys/stat.h>
#include <string.h>

char* inputF = NULL;
char* outputF = NULL;

void printUsage( char* argv[] )
{
	printf( "Usage: -i <inputfile> -o <outputfile> \n" );
}

//function for getting the file size of the input file
off_t getFileSize(const char* file )
{
	struct stat info;

	if( stat( file , &info ) == 0 )
	{
		//printf( "File size is : %ld\n" , info.st_size );
		return info.st_size;
	}

	fprintf( stderr, "FSTAT error\n" );
	return -1;
}

//function that counts the number of lines in the input file
int countLines( char* buffer , int size )
{
	int counter = 0;
	for( int i = 0 ; i < size ; i++ )
	{
		if( buffer[i] == '\n' )
			counter++;
	}
	return counter;
}

//function that maps the lines to the char** array
int assignPointer( char** target , char* buffer , int size )
{
	int i = 0;
	int j = 1;
	int bsize = size;
	//printf( "size is %d", bsize);
	while( i < bsize )
	{
		//printf( "i = %d , char = %c \n " , i , buffer[i] );
		if( buffer[i] == '\n' )
		{
			buffer[i] = '\0';
			if( i != ( bsize - 1 ) )
				target[j] = &buffer[i+1];
			//printf( "Buffer %d assigned to array %d\n" , i , j );
			j++;
		}
		i++;
	}
	return j;
}

//output to file
void writeFunc( char** array , int lines , FILE* OF )
{
	int i = 0;
	int j = lines - 1;
	char nl = '\n';
	while( i <= j )
	{
		//printf( "l = %d , %s\n" , i , array[i] );
		fwrite( array[i] , strlen( array[i] ) , 1 , OF );
		fwrite( &nl , sizeof( char ) , 1 , OF );
		i++;
		if( i < j )
		{
			//printf( " l = %d , %s\n" , j , array[j] );
			fwrite( array[j] , strlen( array[j] ) , 1 , OF );
			fwrite( &nl , sizeof( char ) , 1 , OF );
			j--;
		}
		else if( i == j )
		{
			//printf( " l = %d , %s\n" , j , array[j] );
			fwrite( array[j] , strlen( array[j] ) , 1 , OF );
			fwrite( &nl , sizeof( char ) , 1 , OF );
			break;
		}
	}
}


int main( int argc , char* argv[] )
{
	char c;

	while( ( c = getopt( argc, argv , "i:o:h" ) ) != -1 )
	{
		switch( c )
		{
			case 'i':
				inputF = optarg;
				break;
			case 'o':
				outputF = optarg;
				break;
			case 'h':
				printUsage( argv );
				exit( 0 );
			default:
				printUsage( argv );
				exit( 1 );
		}
	}

	if( inputF == NULL || outputF == NULL )
	{
		fprintf( stderr , "Usage: shuffle -i inputfile -o outputfile\n" );
		//printUsage( argv );
		exit( 1 );
	}

	FILE *IF;
	FILE *OF;

	//opens the file and check if the user input is valid	
	IF = fopen( inputF, "r" );
	if( IF == NULL )
	{
		fprintf( stderr , "Error: Cannot open file %s\n" , inputF );
		exit( 1 );
	}

	//get the size of the file and malloc memory
	int size = (int)( getFileSize( inputF ) );

	char* buffer = malloc( sizeof( char ) * size );

	if( fread( buffer , size , 1 , IF ) == -1 )
	{
		fprintf( stderr , "Error: fread error\n" );
		exit(1);
	}
	

	fclose( IF );

	int lines = countLines( buffer , size );

	//malloc memory for the pointer to the pointer array
	char** array = malloc( sizeof( char* ) * lines );
	array[0] = buffer;
	assignPointer( array, buffer ,size );

	//open the outputfile and check if the user input is valid
	OF = fopen( outputF , "w" );
	if( OF == NULL )
	{
		fprintf( stderr , "Error: Cannot open file %s\n" , outputF );
		exit(1);
	}
	
	//write to output file
	writeFunc( array , lines , OF );

	fclose( OF );
}

