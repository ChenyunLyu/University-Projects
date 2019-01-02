#include <iostream>
#include <map>
#include <vector>
#include <set>
#include <string>
#include <time.h>      // time

using namespace std;

// feel free to use this function if you wish
// purpose: generate random coordinates
// input: none
// output: a vector with 2 coordinates between 1,1 and 4,4
vector<int> Get_A_Random_Square() {
	return vector<int>{rand()%4+1, rand()%4+1};
}

// feel free to use this function if you wish
// purpose: verifies if the coordinates of a square fall within the cave
// input: coordinates of a square
// output: true if the square is in the cave otherwise false
bool Is_Square_Valid(const vector<int> &square) {
	for (auto e : square)
		if (e < 1 || e > 4)
			return false;
	return true;
}

// don't change this function
// purpose: prints a map of the cave to the console
// input: the printable map a vector of strings
// output: none - console output of the cave diagram
void Print_Cave_Diagram(const vector<string> &cave_diagram){
	for (auto s : cave_diagram)
		cout<<s<<endl;
}

// Implement these functions below
// Do not change the function prototpyes
void Initialize_Cave(map<vector<int>, set<string> > &cave);
void Print_Square(map<vector<int>, set<string> > &cave, const vector<int> &rc);
void Get_Cave_Diagram(map<vector<int>, set<string> > &cave, vector<string> &cave_diagram);

// sample main function
int main() {
	srand(time(NULL)); // seed the random generator

	// Declare the cave data structure to 'map' coordinates to a 'set' of strings describing the cell contents
	map<vector<int>, set<string> > cave;

	// Check this out! The cave data structure has a lot going on
	// Uncomment the block below to see a demo of how to use the cave map
	// use the [] to access the set contained in the map
	// insert puts the word "ladder" into the set
	// cave[rc] is the set of words at coordinates rc
	// cave[rc].begin() returns an iterator to the first element of the set
	// note your program will likely crash if the set is empty
	// I recommend checking for this.
	// use the * to dereference the pointer to get the first word stored in the set
	/*
	vector<int> rc{1, 1}; // row column
	cave[rc].insert("ladder");
	if (cave[{1,2}].empty() == false)
		cout<<"cave square (1,1) contains a "<<*cave[rc].begin()<<endl;
	// */

	Initialize_Cave(cave);

	for (int r=1; r<=4; r++) {
		for (int c=1; c<=4; c++) {
			vector<int> rc{r, c};
			Print_Square(cave, rc);
		}
	}

	vector<string> cave_diagram;
	Get_Cave_Diagram(cave, cave_diagram);
	Print_Cave_Diagram(cave_diagram);

	return 0;
}

// add the player, ladder, wumpus, pits, gold, stench, breeze to the map
// input:
// output:
void Initialize_Cave(map<vector<int>, set<string> > &cave) {
	// place the "ladder" in row 1 column 1
	cave[{1,1}].insert("ladder");
	// place the "player" at the same location as the ladder
	cave[{1,1}].insert("player");
	// place the "wumpus" - can't be in the same square as the ladder
	vector<int> wumpus_pos{1,1};
	vector<int> ladder_pos{1,1};
	while( wumpus_pos == ladder_pos ) //validate wumpus position
	{
		wumpus_pos = Get_A_Random_Square();
	}
	cave[wumpus_pos].insert("wumpus");
	// place the 3 "pits" - can't be in the same square as the ladder, wumpus, or another pit
	vector<int> pit1_pos{1,1};
	while( pit1_pos == wumpus_pos || pit1_pos == ladder_pos ) //validate pit1 position
	{
		pit1_pos = Get_A_Random_Square();
	}
	vector<int> pit2_pos{1,1};
	while( pit2_pos == wumpus_pos || pit2_pos == ladder_pos || pit2_pos == pit1_pos ) //validate pit2 position
	{
		pit2_pos = Get_A_Random_Square();
	}
	vector<int> pit3_pos{1,1};
	while( pit3_pos == wumpus_pos || pit3_pos == ladder_pos || pit3_pos == pit1_pos || pit3_pos == pit2_pos ) //validate pit3 position
	{
		pit3_pos = Get_A_Random_Square();
	}
	cave[pit1_pos].insert("pit");
	cave[pit2_pos].insert("pit");
	cave[pit3_pos].insert("pit");
	
	// place the "gold" - can't be in the same square as a pit or the ladder
	vector<int> gold_pos{1,1};
	while( gold_pos == ladder_pos || gold_pos == pit1_pos || gold_pos == pit2_pos || gold_pos == pit3_pos ) //validate gold position
	{
		gold_pos = Get_A_Random_Square();
	}
	cave[gold_pos].insert("gold");
	// place the "stench" squares to the left, right, up, and down from the wumpus
	if(Is_Square_Valid({wumpus_pos[0]+1,wumpus_pos[1]}))
	{cave[{wumpus_pos[0]+1,wumpus_pos[1]}].insert("stench");}
	
	if(Is_Square_Valid({wumpus_pos[0]-1,wumpus_pos[1]}))
	{cave[{wumpus_pos[0]-1,wumpus_pos[1]}].insert("stench");}
	
	if(Is_Square_Valid({wumpus_pos[0],wumpus_pos[1]+1}))
	{cave[{wumpus_pos[0],wumpus_pos[1]+1}].insert("stench");}
	
	if(Is_Square_Valid({wumpus_pos[0],wumpus_pos[1]-1}))
	{cave[{wumpus_pos[0],wumpus_pos[1]-1}].insert("stench");}
	// place the "breeze" squares to the left, right, up, and down from the three pits
	if(Is_Square_Valid({pit1_pos[0]+1,pit1_pos[1]}))
	{cave[{pit1_pos[0]+1,pit1_pos[1]}].insert("breeze");}
	
	if(Is_Square_Valid({pit1_pos[0]-1,pit1_pos[1]}))
	{cave[{pit1_pos[0]-1,pit1_pos[1]}].insert("breeze");}
	
	if(Is_Square_Valid({pit1_pos[0],pit1_pos[1]+1}))
	{cave[{pit1_pos[0],pit1_pos[1]+1}].insert("breeze");}
	
	if(Is_Square_Valid({pit1_pos[0],pit1_pos[1]-1}))
	{cave[{pit1_pos[0],pit1_pos[1]-1}].insert("breeze");}
	
	
	
	if(Is_Square_Valid({pit2_pos[0]+1,pit2_pos[1]}))
	{
		if( cave[{pit2_pos[0]+1,pit2_pos[1]}].find("breeze") == cave[{pit2_pos[0]+1,pit2_pos[1]}].end() ) //insert if not containing "breeze"
		{
			cout<<"here"<<endl;
			cave[{pit2_pos[0]+1,pit2_pos[1]}].insert("breeze");
		}
	}
	
	if(Is_Square_Valid({pit2_pos[0]-1,pit2_pos[1]}))
	{
		if( cave[{pit2_pos[0]-1,pit2_pos[1]}].find("breeze") == cave[{pit2_pos[0]-1,pit2_pos[1]}].end() )
		{cout<<"here"<<endl;
			cave[{pit2_pos[0]-1,pit2_pos[1]}].insert("breeze");
		}
	}
	
	if(Is_Square_Valid({pit2_pos[0],pit2_pos[1]+1}))
	{
		if( cave[{pit2_pos[0],pit2_pos[1]+1}].find("breeze") == cave[{pit2_pos[0],pit2_pos[1]+1}].end() )
		{cout<<"here"<<endl;
			cave[{pit2_pos[0],pit2_pos[1]+1}].insert("breeze");
		}
	}
	
	if(Is_Square_Valid({pit2_pos[0],pit2_pos[1]-1}))
	{
		if( cave[{pit2_pos[0],pit2_pos[1]-1}].find("breeze") == cave[{pit2_pos[0],pit2_pos[1]-1}].end() )
		{cout<<"here"<<endl;
			cave[{pit2_pos[0],pit2_pos[1]-1}].insert("breeze");
		}
	}
	
	
	
	if(Is_Square_Valid({pit3_pos[0]+1,pit3_pos[1]}))
	{
		if( cave[{pit3_pos[0]+1,pit3_pos[1]}].find("breeze") == cave[{pit3_pos[0]+1,pit3_pos[1]}].end() )
		{
			cave[{pit3_pos[0]+1,pit3_pos[1]}].insert("breeze");
		}
	}
	
	if(Is_Square_Valid({pit3_pos[0]-1,pit3_pos[1]}))
	{
		if( cave[{pit3_pos[0]-1,pit3_pos[1]}].find("breeze") == cave[{pit3_pos[0]-1,pit3_pos[1]}].end() )
		{
			cave[{pit3_pos[0]-1,pit3_pos[1]}].insert("breeze");
		}
	}
	
	if(Is_Square_Valid({pit3_pos[0],pit3_pos[1]+1}))
	{
		if( cave[{pit3_pos[0],pit3_pos[1]+1}].find("breeze") == cave[{pit3_pos[0],pit3_pos[1]+1}].end() )
		{
			cave[{pit3_pos[0],pit3_pos[1]+1}].insert("breeze");
		}
	}
	
	if(Is_Square_Valid({pit3_pos[0],pit3_pos[1]-1}))
	{
		if( cave[{pit3_pos[0],pit3_pos[1]-1}].find("breeze") == cave[{pit3_pos[0],pit3_pos[1]-1}].end() )
		{
			cave[{pit3_pos[0],pit3_pos[1]-1}].insert("breeze");
		}
	}
}


// print the contents of the square
// input:
// output:
void Print_Square(map<vector<int>, set<string> > &cave, const vector<int> &rc) {
	cout<<"This part of the cave contains"<<endl;
	if (cave[rc].empty() == false)
	{

		for (auto n : cave[rc] ) 
		{
		cout << "    " << n <<endl;
		}
	}
	else
	{
		cout<<"    nothing"<<endl;
	}
}

// build a vector of strings where each string in the vector represents one row of the cave output
// input:
// output:
void Get_Cave_Diagram(map<vector<int>, set<string> > &cave, vector<string> &cave_diagram) {
	int cell_rows = 5;
	int cell_columns = 11;
	int total_rows = cell_rows*4 + 1;
	int total_columns = cell_columns*4 + 1;

	// fill in with vertical cell divisions
	for (int r=0; r<total_rows; r++) {
		string row(total_columns, ' ');
		for (int c=0; c<total_columns; c+=cell_columns) {
			row[c] = '|';
		}
		cave_diagram.push_back(row);
	}

	// udpate horizontal rows with '-'
	for (int i=0; i<total_rows; i+=cell_rows) {
		cave_diagram[i] = string(total_columns, '-');
	}

	// update cell corners with '+'
	for (int r=0; r<total_rows; r+=cell_rows) {
		for (int c=0; c<total_columns; c+=cell_columns) {
			cave_diagram[r][c]='+';
		}
	}

	// replace the part of the string with the cell contents
	for( int r = 1 ; r <= 4 ; r++ ) //iterate rows
	{
		for( int c = 1 ; c <= 4 ; c++ ) // iterate cols
		{
			if( cave[{r,c}].empty() == false ) //check if empty
			{
				for( int i = 0 ; i < cave[{r,c}].size() ; i++ ) //iterate through the items of each square
				{
					string str = *next(cave[{r,c}].begin(), i);
					//cout<<str.length()<<endl;
					for( int j = 0 ; j < str.length() ; j++ )
					{
						cave_diagram[1+(r-1)*5+i][3+(c-1)*11+j] = str[j];
					}
				}
			}
		}
	}
	/*
	 * backup
	if( cave[{1,1}].empty() == false )
	{
		for( int i = 0 ; i < cave[{1,1}].size() ; i++ )
		{
			string str = *next(cave[{1,1}].begin(), i);
			cout<<str.length()<<endl;
			for( int j = 0 ; j < str.length() ; j++ )
			{
				cave_diagram[1+i][3+j] = str[j];
			}
		}
	}
	* */

}


