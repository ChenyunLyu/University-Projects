#include "PLAYER.h"
#include "CAVE.h"

//constructor
//
PLAYER::PLAYER()
{
	position = {1,1};
	hasGold = TRUE;
	playerStatus = ALIVE;
	hasArrow = TRUE;
	direction = RIGHT;
	
}

PLAYER::~PLAYER()
{}

//returns the position of player
vector<int> PLAYER::Get_Position()
{
	return position;
}

//returns the facing of player
string PLAYER::Get_Facing()
{
	if( direction == RIGHT )
		return "right";
	else if( direction == LEFT )
		return "left";
	else if( direction == UP )
		return "up";
	else
		return "down";
}

//returns the next square if player moves
vector<int> PLAYER::Get_Next_Square()
{
	if( direction == RIGHT )
		return {position[0],position[1]+1};
	else if( direction == LEFT )
		return {position[0],position[1]-1};
	else if( direction == UP )
		return {position[0]-1,position[1]};
	else
		return {position[0]+1,position[1]};
}

//move player
void PLAYER::Move_Forward()
{
	if( direction == RIGHT )
		position = {position[0],position[1]+1};
	else if( direction == LEFT )
		position = {position[0],position[1]-1};
	else if( direction == UP )
		position = {position[0]-1,position[1]};
	else
		position = {position[0]+1,position[1]};
}

//change facing of the player to the left by 90 degrees
void PLAYER::Turn_Left()
{
	if( direction == RIGHT )
		direction = UP;
	else if( direction == LEFT )
		direction = DOWN;
	else if( direction == UP )
		direction = LEFT;
	else
		direction = RIGHT;
}

//change facing of the player to the right by 90 degrees
void PLAYER::Turn_Right()
{
	if( direction == RIGHT )
		direction = DOWN;
	else if( direction == LEFT )
		direction = UP;
	else if( direction == UP )
		direction = RIGHT;
	else
		direction = LEFT;
}

//pickup and removes gold from cave
//input: cave
//output: true if picked up gold else false
bool PLAYER::Pickup_Gold( CAVE &cave )
{
	if( cave.get_cave_map()[position].count("gold") ) // if contains gold
	{
		cave.Remove_Gold(position);
		return true;
	}
	else
		return false;
}

//shoot arrow
//input: cave
//output: true if kills wumpus else false
bool PLAYER::Shoot_Arrow( CAVE &cave )
{
	if( hasArrow == FALSE )
		return false;
	else 
	{
		int posrow = position[0];
		int poscol = position[1];
		
		if( direction == RIGHT )
			poscol = poscol+1;
		else if( direction == LEFT )
			poscol = poscol-1;
		else if( direction == UP )
			posrow = posrow-1;
		else
			posrow = posrow+1;
			
		while( posrow <= 4 && poscol <= 4 && posrow >= 1 && poscol >= 1 ) // check if the arrow is not out of bounds
		{
			if( cave.get_cave_map()[{posrow,poscol}].count("wumpus") ) // if fond wumpus
				return true;
			if( direction == RIGHT )
				poscol = poscol+1;
			else if( direction == LEFT )
				poscol = poscol-1;
			else if( direction == UP )
				posrow = posrow-1;
			else
				posrow = posrow+1;
		}
		return false;
	}
}

//set player status to escaped
void PLAYER::Climb_Ladder()
{
	playerStatus = ESCAPED;
}

//check if player has arrow
bool PLAYER::Has_Arrow()
{
	if( hasArrow == TRUE )
		return true;
	else
		return false;
}

//check if player has gold
bool PLAYER::Has_Gold()
{
	if( hasGold == TRUE )
		return true;
	else
		return false;
}

//check if player is eaten or fell or alive
//input: cave
//output: true if alive else false	
bool PLAYER::Update_Player_Status( CAVE &cave )
{
	int posrow = position[0];
	int poscol = position[1];
	
	if( cave.get_cave_map()[{posrow,poscol}].count( "wumpus" ) ) //check if wumpus is in the same square as player
	{
		playerStatus = EATEN;
		return false;
	}
	else if( cave.get_cave_map()[{posrow,poscol}].count( "pit" ) )//check if a pit is in the same square as player
	{
		playerStatus = FELL;
		return false;
	}
	else
	{
		return true;
	}
}

//check if player has escaped
bool PLAYER::Escaped()
{
	if( playerStatus == ESCAPED )
		return true;
	else
		return false;
}

//check if player got eaten
bool PLAYER::Eaten()
{
	if( playerStatus == EATEN )
		return true;
	else
		return false;
}

//check if player fell into a pit
bool PLAYER::Fell()
{
	if( playerStatus == FELL )
		return true;
	else
		return false;
}
