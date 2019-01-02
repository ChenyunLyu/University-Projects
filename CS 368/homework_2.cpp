/* ******************************************************
 * Name:
 * Wisc ID:
 * OS:
 * IDE (or text editor):
 * Compiler:
 * How long did the assignment take you to complete in minutes:
 * What other resources did you consult (copy and paste links below:
*/ // ******************************************************

#include <iostream>
#include <iomanip>
#include <string>
#include <fstream>

using namespace std;

const int MAX_CLASS_SIZE = 100;
const int MAX_NUMBER_OF_ASSIGNMENTS = 100;

// do not change these prototypes.  Add your code to the function definitions below
void Read_Grade_File(string names[MAX_CLASS_SIZE][2], int scores[MAX_CLASS_SIZE][MAX_NUMBER_OF_ASSIGNMENTS], int *number_of_students, int *number_of_assignments, const string input_filename);
void Format_Case_Of_Names(string names[MAX_CLASS_SIZE][2], const int number_of_students);
void Compute_Total_And_Percent(int scores[MAX_CLASS_SIZE][MAX_NUMBER_OF_ASSIGNMENTS], int total[], float percent[], int number_of_students, int number_of_assignments);
void Write_Formatted_Grades(string names[MAX_CLASS_SIZE][2], int total[], float percent[], const int number_of_students, const string output_filename);

// There is no need to change main. However you are encouraged to write code
// to write data to the console to check the correctness of each of your functions as
// as you work.
int main() {
	string input_filename="unformatted_grades.txt";
	string output_filename="formatted_grades.txt";
	string names[MAX_CLASS_SIZE][2];
	int scores[MAX_CLASS_SIZE][MAX_NUMBER_OF_ASSIGNMENTS];
	int number_of_students=0;
	int number_of_assignments=0;
	int total[MAX_CLASS_SIZE];
	float percent[MAX_CLASS_SIZE];

	Read_Grade_File(names, scores, &number_of_students, &number_of_assignments, input_filename);
	cout<<number_of_students<<endl;
	Format_Case_Of_Names( names, number_of_students);
	Compute_Total_And_Percent( scores, total, percent, number_of_students, number_of_assignments);
	Write_Formatted_Grades(names, total, percent, number_of_students, output_filename);
	return 0;
}

// Add your code below to define these functions
// remember to add comments to each function to describe the
// 1) purpose, 2) input, and 3) output of the functions

//read the given file and store the data into corresponding parameter
//@param names      2D-array to store the first and last name of the students
//@param scores		2D-array to store the scores of the assignments
//@param number_of_students      stores the total number of students in class
//@param number_of_assignments   stores the total nubmer of assignments
//@param input_filename 		 the file to be read
void Read_Grade_File(string names[MAX_CLASS_SIZE][2], int scores[MAX_CLASS_SIZE][MAX_NUMBER_OF_ASSIGNMENTS], int *number_of_students, int *number_of_assignments, const string input_filename)
{
	//open file
	ifstream infile;
	infile.open(input_filename);
	
	//read number of students
	string NOS,temp; //temp is used to store tempory data read from file
	infile>>NOS;
	if( NOS == "number_of_students" )
	{
		infile>>temp;
		*number_of_students = stoi( temp );
	}
	else
	{
		cout<<"read file format incorrect"<<endl;
		exit(1);
	}
	
	//read number of assignments
	string NOA;
	infile>>NOA;
	if( NOA == "number_of_assignments" )
	{
		infile>>temp;
		*number_of_assignments = stoi( temp );
	}
	else
	{
		cout<<"read file format incorrect"<<endl;
		exit(1);
	}
	//skip through the 3 key words we wont need
	infile>>temp>>temp>>temp;
	//skip through the max points of each assignment we wont be reading
	for( int i = 0 ; i < *number_of_assignments ; i++ )
	{
		infile>>temp;
	}
	//read each line and store them into names and scores
	for( int i = 0 ; i < *number_of_students ; i++ )
	{
		infile>>temp>>temp;
		names[i][0] = temp;
		infile>>temp;
		names[i][1] = temp;
		for( int j = 0 ; j < *number_of_assignments ; j++ )
		{
			infile>>temp;
			scores[i][j] = stoi( temp );
		}
	}
	/*
	//test loop
	for( int i = 0 ; i < *number_of_students ; i++ )
	{
		for( int j = 0 ; j < *number_of_assignments ; j++ )	
		{
			cout<<scores[i][j]<<" ";
		}	
		cout<<endl;
	}
	* */
	infile.close();
}

//format the names of the students so that the first letter of the string is upper and the rest is lower
//@param names 			2D-array to contains the names of the students
//@param number_of students    the nubmer of total students in class
void Format_Case_Of_Names(string names[MAX_CLASS_SIZE][2], const int number_of_students)
{
	
	//go through every name in the array and make every first letter upper and the rest lower
	for( int i = 0 ; i < number_of_students ; i++ )
	{
		for( int j = 0 ; j < names[i][0].length() ; j++ )
		{
			if( j == 0 )
			{
				names[i][0][j] = toupper( names[i][0][j] );
			}
			else
				names[i][0][j] = tolower( names[i][0][j] );
		}
		
		for( int j = 0 ; j < names[i][1].length() ; j++ )
		{
			if( j == 0 )
			{
				names[i][1][j] = toupper( names[i][1][j] );
			}
			else
				names[i][1][j] = tolower( names[i][1][j] );
		}
	}
	cout<<int(20.0/3*10+0.5)/10.0<<endl;
}

//computer the total score and percentage score for each student
//@param scores					raw score for each student
//@param total					store the total points for each student
//@param peprcent				store the percentage points for each student
//@param number_of_students		nubmer of students in class
//@param number_of_assignments	number of total assignments
void Compute_Total_And_Percent(int scores[MAX_CLASS_SIZE][MAX_NUMBER_OF_ASSIGNMENTS], int total[], float percent[], int number_of_students, int number_of_assignments)
{
	for( int i = 0 ; i < number_of_students ; i++ )
	{
		int sum = 0;
		for( int j = 0 ; j < number_of_assignments ; j++ )
		{
			sum = sum + scores[i][j];
		}
		total[i] = sum;
		//percent[i] = int(float(total[i])/number_of_assignments*100+0.5)/10.0;
		percent[i] = float(total[i])/number_of_assignments*10;
		cout<<percent[i]<<endl;

	}
}

//
//@param names
//@param total
//@param percent
//@param number_of_students
//@param output_filename
void Write_Formatted_Grades(string names[MAX_CLASS_SIZE][2], int total[], float percent[], const int number_of_students, const string output_filename)
{
	ofstream outfile;
	outfile.open(output_filename);
	
	//the nubmer of spaces before total and average score should be printed
	int total_spacing = 20;
	int average_spacing = 2;
	int max_percent_length = 5; // 100.0
	
	for( int i = 0 ; i < number_of_students ; i++ )
	{
		int current_space = 0;
		current_space = names[i][0].length() + names[i][1].length() + to_string(total[i]).length();
		outfile<<names[i][1]<<", "<<names[i][0];
		for( int j = 0 ; j < total_spacing - current_space ; j++ )
		{
			outfile<<" ";
		}
		outfile<<total[i];
		//cout<<percent[i]<<endl;
		outfile<<"  ";
		if( percent[i] == 100 )
		{
			outfile<<"100.0"<<endl;
		}
		else if( percent[i] >= 10 )
		{
			outfile<<fixed;
			outfile<<" "<<setprecision(1)<<percent[i]<<endl;
		}
		else
		{
			outfile<<fixed;
			outfile<<"  "<<setprecision(1)<<percent[i]<<endl;
		}
		
		//cout<<current_space<<endl;		
	}
	
	
	outfile.close();
	
}

